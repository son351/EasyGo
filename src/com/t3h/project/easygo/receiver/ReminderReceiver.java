/**
 * 
 */
package com.t3h.project.easygo.receiver;

import com.t3h.project.easygo.MainActivity;
import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.DatabaseHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * @author My PC
 * 
 */
public class ReminderReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int _id = intent.getIntExtra(DatabaseHelper.REMINDERS_ID, 1);
		String msg = intent.getStringExtra(DatabaseHelper.REMINDER_MSG);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.gowalla_hdpi)
				.setContentTitle("EasyGo Reminder!")
				.setContentText(msg)
				.setWhen(System.currentTimeMillis()).setDefaults(Notification.DEFAULT_ALL);		

		Intent resultIntent = new Intent(context, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(_id, mBuilder.build());
	}

}
