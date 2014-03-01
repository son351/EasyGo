/**
 * 
 */
package com.t3h.project.easygo.service;

import java.util.Calendar;

import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.receiver.ReminderReceiver;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author My PC
 * 
 */
public class ReminderService extends IntentService {
	public static final String CREATE = "com.t3h.project.easygo.receiver.CREATE";
	public static final String CANCEL = "com.t3h.project.easygo.receiver.CANCEL";
	private IntentFilter filter;
	private int year, month, day, hourOfDay, minute;
	private String reminderNote;
	private int _id;

	public ReminderService() {
		super("SEVICE");
		// TODO Auto-generated constructor stub
		filter = new IntentFilter();
		filter.addAction(CREATE);
		filter.addAction(CANCEL);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		_id = intent.getIntExtra(DatabaseHelper.REMINDERS_ID, 1);
		reminderNote = intent.getStringExtra(DatabaseHelper.REMINDER_MSG);
		day = intent.getIntExtra("day", 0);
		month = intent.getIntExtra("month", 0);
		year = intent.getIntExtra("year", 0);
		hourOfDay = intent.getIntExtra("hourOfDay", 0);
		minute = intent.getIntExtra("minute", 0);

		if (filter.matchAction(action)) {
			execute(action);
		}
	}

	private void execute(String action) {
		// TODO Auto-generated method stub
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(this, ReminderReceiver.class);
		intent.putExtra(DatabaseHelper.REMINDERS_ID, _id);
		intent.putExtra(DatabaseHelper.REMINDER_MSG, reminderNote);		

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Calendar timeOff = Calendar.getInstance();
		timeOff.set(Calendar.MONTH, month);
		timeOff.set(Calendar.DAY_OF_MONTH, day);
		timeOff.set(Calendar.YEAR, year);
		timeOff.set(Calendar.HOUR_OF_DAY, hourOfDay);
		timeOff.set(Calendar.MINUTE, minute);
		timeOff.set(Calendar.SECOND, 0);
		
		if(CREATE.equals(action)){
			am.set(AlarmManager.RTC_WAKEUP, timeOff.getTimeInMillis(), pendingIntent);
		} else if (CANCEL.equals(action)) {
			am.cancel(pendingIntent);
		}
	}

}
