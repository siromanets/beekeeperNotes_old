package com.example.key.beekeepernote.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.key.beekeepernote.models.Apiary;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
import com.example.key.beekeepernote.models.Notifaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by key on 24.09.17.
 */

public class AlarmService  extends Service{
	public static final String TO_SERVICE_COMMANDS = "service_command";
	public static final int REFRESH_ALARM = 1;
	public static final int CREATE_ALARM = 2;
	public static final int SEND_NOTIFACTION = 101;
	public static final int NO_SEND_NOTIFACTION = 102;
	public static final String TYPE_MESSAGE = "type_message";
	public static final String CREATE_ALAR = "create_alarm";
	public static final String PATH_MESSAGE = "path_message";
	public static final String NAME_MESSAGE = "name_message";
	public static final String QUEEN = "com.example.key.beekeepernote.action.QUEEN";
	public static final String CHECKING = "com.example.key.beekeepernote.action.CHECKING";
	public static final String NOTATION = "com.example.key.beekeepernote.action.NOTATION";
	public static final String HISTORY ="com.example.key.beekeepernote.action.HISTORY";
	private PendingIntent mAlarmSender;
	private Calendar mCalender;
	FirebaseDatabase database;
	private Integer i;


	@Override
	public void onCreate() {
		mCalender = Calendar.getInstance();
		database = FirebaseDatabase.getInstance();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getIntExtra(TO_SERVICE_COMMANDS, 0) == REFRESH_ALARM){
			refreshAlarm();
		}else if(intent.getIntExtra(TO_SERVICE_COMMANDS, 0) == CREATE_ALARM){

		}else if (intent.getSerializableExtra(TO_SERVICE_COMMANDS) != null){
			Notifaction notifaction = (Notifaction) intent.getSerializableExtra(TO_SERVICE_COMMANDS);
			 i = new Integer(notifaction.getuId()) ;
			startAlarm(notifaction.getPathNotifaction(), notifaction.getTypeNotifaction(), notifaction.getSchowTime());
		}

		return START_NOT_STICKY;
	}

	private void refreshAlarm() {
		DatabaseReference myRef = database.getReference();
		Query myApiaries = myRef.child("apiary");
		myApiaries.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if(dataSnapshot != null) {
					for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
						Apiary apiary = postSnapshot.getValue(Apiary.class);
						if (apiary != null) {
							for(int i = 0; i < apiary.getBeehives().size(); i++){
								Beehive beehive = apiary.getBeehives().get(i);
								List<BeeColony> colonyList =  apiary.getBeehives().get(i).getBeeColonies();
								if (colonyList.size() > 0) {
									for (int c = 0; c < colonyList.size(); c++) {
										if (colonyList.get(i).getCheckedTime() > mCalender.getTime().getTime()) {
											String path = Uri.fromParts(apiary.getNameApiary(), String.valueOf(beehive.getNumberBeehive()), String.valueOf(i)).toString();
											startAlarm( path, CHECKING, colonyList.get(i).getCheckedTime());
										}
										if (colonyList.get(i).isQueen() > mCalender.getTime().getTime()) {
											String path = Uri.fromParts(apiary.getNameApiary(), String.valueOf(beehive.getNumberBeehive()), String.valueOf(i)).toString();
											startAlarm( path, QUEEN, colonyList.get(i).isQueen());										}
									}
								}
							}
						}

					}
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}



	public void startAlarm(String pathNotifaction, String action, long time){

		Intent intent = new Intent(this, TimeNotification.class);
		intent.putExtra(PATH_MESSAGE, pathNotifaction);


		intent.setAction(action);
		mAlarmSender = PendingIntent.getBroadcast(this, i, intent , PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, time +20000, mAlarmSender);
	}




	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
