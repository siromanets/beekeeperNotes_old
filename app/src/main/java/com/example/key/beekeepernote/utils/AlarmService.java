package com.example.key.beekeepernote.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.key.beekeepernote.models.Apiary;
import com.example.key.beekeepernote.models.BeeColony;
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

	private PendingIntent mAlarmSender;
	private Calendar mCalender;
	FirebaseDatabase database;



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
								List<BeeColony> colonyList =  apiary.getBeehives().get(i).getBeeColonies();
								if (colonyList.size() > 0) {
									for (int c = 0; c < colonyList.size(); c++) {
										if (colonyList.get(i).getCheckedTime() > mCalender.getTime().getTime()) {
											startAlarm(colonyList.get(i).getCheckedTime(), SEND_NOTIFACTION);
										}
										if (colonyList.get(i).isQueen() > mCalender.getTime().getTime()) {
											startAlarm(colonyList.get(i).getCheckedTime(), NO_SEND_NOTIFACTION);
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



	public void startAlarm(long time, int requestCode){
		Intent intent = new Intent(this, TimeNotification.class);
		intent.putExtra(TYPE_MESSAGE, requestCode);
		mAlarmSender = PendingIntent.getBroadcast(this, requestCode, intent , 0);
		mCalender.add(Calendar.SECOND, (int)time);
		long firstTime = mCalender.getTimeInMillis();
		// Schedule the alarm!

		AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
	}


	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
