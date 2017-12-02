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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

import static com.example.key.beekeepernote.fragments.BeeColonyFragment.CHECKING_CONSTANT_NUMBER;
import static com.example.key.beekeepernote.fragments.BeeColonyFragment.NOTATION_CONSTANT_NUMBER;
import static com.example.key.beekeepernote.fragments.BeeColonyFragment.QUEEN_CONSTANT_NUMBER;

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
	public static final String QUEEN = "com.example.key.beekeepernote.action.QUEEN"; // 1
	public static final String CHECKING = "com.example.key.beekeepernote.action.CHECKING"; //2
	public static final String NOTATION = "com.example.key.beekeepernote.action.NOTATION"; //3
	public static final String HISTORY ="com.example.key.beekeepernote.action.HISTORY"; //4
	public static final int QUEEN_INT =  1;
	public static final int CHECKING_INT = 2;
	public static final int NOTATION_INT = 3;
	public static final int HISTORY_INT = 4;
	public static final int DEFAULT_HISTORY = 0;
	private PendingIntent mAlarmSender;
	private Calendar mCalender;
	FirebaseDatabase database;
	private int unId;


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
			 unId = new Integer(notifaction.getuId()) ;
			startAlarm(notifaction.getPathNotifaction(), notifaction.getTypeNotifaction(), notifaction.getSchowTime());
		}

		return START_NOT_STICKY;
	}

	private void refreshAlarm() {
		DatabaseReference myRef = database.getReference();
		Query myApiaries = myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("apiary");
		myApiaries.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if(dataSnapshot != null) {
					for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
						Apiary apiary = postSnapshot.getValue(Apiary.class);
						if (apiary != null) {
							for (int i = 0; i < apiary.getBeehives().size(); i++) {
								Beehive beehive = apiary.getBeehives().get(i);
								List<BeeColony> colonyList = apiary.getBeehives().get(i).getBeeColonies();
								if (colonyList.size() > 0) {
									for (int c = 0; c < colonyList.size(); c++) {

										if (colonyList.get(c).getCheckedTime() + (11 * 24 * 60 * 60 * 1000) > Calendar.getInstance().getTime().getTime()) {
											unId = (int) beehive.getFounded() / beehive.getNumberBeehive() / (c + 1) / CHECKING_CONSTANT_NUMBER;
											String path = new Uri.Builder().appendPath(apiary.getNameApiary()).appendPath(String.valueOf(beehive.getNumberBeehive())).appendPath(String.valueOf(i)).build().toString();
											startAlarm(path, CHECKING_INT, colonyList.get(c).getCheckedTime());
										}
										if (colonyList.get(c).isQueen() + (11 * 24 * 60 * 60 * 1000) >Calendar.getInstance().getTime().getTime()) {
											unId = (int) beehive.getFounded() / beehive.getNumberBeehive() / (c + 1) / QUEEN_CONSTANT_NUMBER;
											String path = new Uri.Builder().appendPath(apiary.getNameApiary()).appendPath(String.valueOf(beehive.getNumberBeehive())).appendPath(String.valueOf(i)).build().toString();
											startAlarm(path, QUEEN_INT, colonyList.get(c).isQueen());
										}
										if (colonyList.get(c).getTimeReminder() > Calendar.getInstance().getTime().getTime()) {
											unId = (int) beehive.getFounded() / beehive.getNumberBeehive() / (c + 1) / NOTATION_CONSTANT_NUMBER;
											String path = new Uri.Builder().appendPath(apiary.getNameApiary()).appendPath(String.valueOf(beehive.getNumberBeehive())).appendPath(String.valueOf(i)).build().toString();
											startAlarm(path, NOTATION_INT, colonyList.get(c).getCheckedTime());

										}
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



	public void startAlarm(String pathNotifaction, int action, long time){

		Intent intent = new Intent(this, TimeNotification.class);
		intent.putExtra(PATH_MESSAGE, pathNotifaction);



		mAlarmSender = PendingIntent.getBroadcast(this, unId, intent , PendingIntent.FLAG_CANCEL_CURRENT);
		switch (action) {
			case CHECKING_INT: {
				intent.setAction(CHECKING);
				AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, time + 20000 , mAlarmSender);
				break;
			}
			case QUEEN_INT: {
				intent.setAction(QUEEN);
				AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, time + 20000, mAlarmSender);
				break;
			}
			case NOTATION_INT: {
				intent.setAction(NOTATION);
				AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, time, mAlarmSender);
				break;
			}
		}
	}




	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
