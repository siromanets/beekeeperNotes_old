package com.example.key.beekeepernote.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.activities.StartActivity_;

/**
 * Created by key on 24.09.17.
 */

public class TimeNotification extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationManager mNM;

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, StartActivity_.class), 0);

		mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		// Set the icon, scrolling text and timestamp
		NotificationCompat.Builder notyBilder = new NotificationCompat.Builder(context);
		notyBilder.setSmallIcon(R.drawable.ic_beehive_one);
		notyBilder.setContentTitle(",kf ,kf");
		notyBilder.setContentText("skjdjdksjdjsdksdsjdjskkskjjd");
		notyBilder.setContentIntent(contentIntent);
		Notification notification = notyBilder.build();

		// The PendingIntent to launch our activity if the user selects this notification


		// Set the info for the views that show in the notification panel.
		// Send the notification.
		// We use a layout id because it is a unique number. We use it later to cancel.
		mNM.notify(1, notification);


	}
}
