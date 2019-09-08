package com.carpathianapiary.niko.beekeepernotes.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.carpathianapiary.niko.beekeepernotes.R;

import com.carpathianapiary.niko.beekeepernotes.models.Notifaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.CHECKING;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.CHECKING_INT;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.NOTATION;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.NOTATION_INT;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.PATH_MESSAGE;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.QUEEN;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.QUEEN_INT;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.REFRESH_ALARM;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.TO_SERVICE_COMMANDS;

/**
 * Created by key on 24.09.17.
 */

public class TimeNotification extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Toast.makeText(context.getApplicationContext(), "sssssssss", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(context.getApplicationContext(), AlarmService.class);
			i.putExtra(TO_SERVICE_COMMANDS, REFRESH_ALARM);
			context.getApplicationContext().startService(i);

		}else if (intent.getAction().equals(QUEEN)) {
			if (intent.getStringExtra(PATH_MESSAGE) != null) {
				String path =  intent.getStringExtra(PATH_MESSAGE);
				Uri uri = Uri.parse(path);
				List<String> pathes = uri.getPathSegments();
					NotificationManager mNM;

		//			PendingIntent contentIntent = PendingIntent.getActivity(context, 1, new Intent(context, StartActivity_.class), 0);

					mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
					// Set the icon, scrolling text and timestamp
					NotificationCompat.Builder notyBilder = new NotificationCompat.Builder(context);
					notyBilder.setSmallIcon(R.drawable.ic_beehive_one);
					notyBilder.setContentTitle("QUEEN");
					notyBilder.setContentText("за останні кілкість днів ви не помічали королеви в "
							+ "Пасіка " + pathes.get(0) + "| " + "Вулик № " +  pathes.get(1) + "| " + "Сімя № " + pathes.get(2) + "| ");
			//		notyBilder.setContentIntent(contentIntent);
					Notification notification = notyBilder.build();

					// The PendingIntent to launch our activity if the user selects this notification


					// Set the info for the views that show in the notification panel.
					// Send the notification.
					// We use a layout id because it is a unique number. We use it later to cancel.

					int i = (int)Math.round(Math.random() * 1000);
					mNM.notify(i, notification);
				Notifaction notifaction = new Notifaction();
				notifaction.setTypeNotifaction(QUEEN_INT);
				notifaction.setTextNotifaction("за останні кілкість днів ви не помічали королеви");
				notifaction.setNameNotifaction(QUEEN);
				notifaction.setPathNotifaction(path);
				notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
					FirebaseAuth mAuth = FirebaseAuth.getInstance();
					DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
					myRef.child(mAuth.getCurrentUser().getUid()).child("Notifaction")
							.child(String.valueOf(notifaction.getSchowTime()))
							.setValue(notifaction);
				}

		}else if (intent.getAction().equals(CHECKING)){
			if (intent.getStringExtra(PATH_MESSAGE) != null) {
				String path =  intent.getStringExtra(PATH_MESSAGE);
				Uri uri = Uri.parse(path);
				List<String> pathes = uri.getPathSegments();
				NotificationManager mNM;

			//	PendingIntent contentIntent = PendingIntent.getActivity(context, 1, new Intent(context, StartActivity_.class), 0);

				mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
				// Set the icon, scrolling text and timestamp
				NotificationCompat.Builder notyBilder = new NotificationCompat.Builder(context);
				notyBilder.setSmallIcon(R.drawable.ic_beehive_one);
				notyBilder.setContentTitle("CHECKING");
				notyBilder.setContentText("Вам потрібно оглянути сімю  "
						+ pathes.get(0) + "| " +  pathes.get(1) + "| " + pathes.get(2) + "| ");
			//	notyBilder.setContentIntent(contentIntent);
				Notification notification = notyBilder.build();

				// The PendingIntent to launch our activity if the user selects this notification


				// Set the info for the views that show in the notification panel.
				// Send the notification.
				// We use a layout id because it is a unique number. We use it later to cancel.
				int r = (int)Math.round(Math.random()*1000);
				mNM.notify(r, notification);


				Notifaction notifaction = new Notifaction();
				notifaction.setTypeNotifaction(CHECKING_INT);
				notifaction.setTextNotifaction("Потрібно оглянути сімю");
				notifaction.setNameNotifaction(CHECKING);
				notifaction.setPathNotifaction(path);
				notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
				FirebaseAuth mAuth = FirebaseAuth.getInstance();
				DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
				myRef.child(mAuth.getCurrentUser().getUid()).child("history")
						.child(String.valueOf(notifaction.getSchowTime()))
						.setValue(notifaction);

			}
		}else if (intent.getAction().equals(NOTATION)){
			if (intent.getStringExtra(PATH_MESSAGE) != null) {
				String path =  intent.getStringExtra(PATH_MESSAGE);
				Uri uri = Uri.parse(path);
				List<String> pathes = uri.getPathSegments();
				NotificationManager mNM;

//				PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
//						new Intent(context, StartActivity_.class), 0);

				mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
				// Set the icon, scrolling text and timestamp
				NotificationCompat.Builder notyBilder = new NotificationCompat.Builder(context);
				notyBilder.setSmallIcon(R.drawable.ic_beehive_one);
				notyBilder.setContentTitle("NOTATION");
				notyBilder.setContentText("Ви просили нагадати"
						+ pathes.get(0) + "| " +  pathes.get(1) + "| " + pathes.get(2) + "| ");
//				notyBilder.setContentIntent(contentIntent);
				Notification notification = notyBilder.build();

				// The PendingIntent to launch our activity if the user selects this notification


				// Set the info for the views that show in the notification panel.
				// Send the notification.
				// We use a layout id because it is a unique number. We use it later to cancel.
				int r = (int)Math.round(Math.random() * 1000);
				mNM.notify((int)Math.round(Math.random()), notification);


				Notifaction notifaction = new Notifaction();
				notifaction.setTypeNotifaction(NOTATION_INT);
				notifaction.setTextNotifaction("Потрібно оглянути сімю");
				notifaction.setNameNotifaction(NOTATION);
				notifaction.setPathNotifaction(path);
				notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
				FirebaseAuth mAuth = FirebaseAuth.getInstance();
				DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
				myRef.child(mAuth.getCurrentUser().getUid()).child("Notifaction")
						.child(String.valueOf(Calendar.getInstance().getTime().getTime()))
						.setValue(notifaction);

			}
		}
	}
}
