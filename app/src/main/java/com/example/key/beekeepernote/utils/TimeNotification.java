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

import static com.example.key.beekeepernote.utils.AlarmService.NO_SEND_NOTIFACTION;
import static com.example.key.beekeepernote.utils.AlarmService.REFRESH_ALARM;
import static com.example.key.beekeepernote.utils.AlarmService.SEND_NOTIFACTION;
import static com.example.key.beekeepernote.utils.AlarmService.TO_SERVICE_COMMANDS;
import static com.example.key.beekeepernote.utils.AlarmService.TYPE_MESSAGE;

/**
 * Created by key on 24.09.17.
 */

public class TimeNotification extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent i = new Intent();
			i.setAction("com.example.key.beekeepernote.utils.AlarmService");
			i.putExtra(TO_SERVICE_COMMANDS, REFRESH_ALARM);
			context.startService(i);
		}else  {
			if (intent.getIntExtra(TYPE_MESSAGE, 0) == SEND_NOTIFACTION) {
				NotificationManager mNM;

				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, StartActivity_.class), 0);

				mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
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

			}else if (intent.getIntExtra(TYPE_MESSAGE, 0) == NO_SEND_NOTIFACTION){
				//TODO write to history on Database
			}

		}

	}
}
