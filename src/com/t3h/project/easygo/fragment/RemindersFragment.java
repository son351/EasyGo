/**
 * 
 */
package com.t3h.project.easygo.fragment;

import java.util.Calendar;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.database.ReminderDataSource;
import com.t3h.project.easygo.entity.ReminderEntity;
import com.t3h.project.easygo.service.ReminderService;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class RemindersFragment extends Fragment implements OnClickListener {
	Button btnDate, btnTime, btnSet;
	EditText editTextReminderNote;
	static TextView txtDate, txtTime;
	private static int y, m, d, h, min;
	private ReminderDataSource reminderDataSource;
	View v;
	private String _id;
	private ReminderEntity reminderEntity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		reminderDataSource = new ReminderDataSource(getActivity());
		reminderDataSource.open();

		v = (View) inflater.inflate(R.layout.reminders_layout, null);
		btnDate = (Button) v.findViewById(R.id.btnDate);
		btnTime = (Button) v.findViewById(R.id.btnTime);
		btnSet = (Button) v.findViewById(R.id.btnSet);
		editTextReminderNote = (EditText) v
				.findViewById(R.id.editTextReminderNote);

		txtDate = (TextView) v.findViewById(R.id.txtDate);
		txtTime = (TextView) v.findViewById(R.id.txtTime);

		_id = getArguments().getString(DatabaseHelper.REMINDERS_ID);
		if (!_id.equals("add")) {
			reminderEntity = reminderDataSource.getOneReminder(Integer
					.parseInt(_id));
			editTextReminderNote.setText(reminderEntity.getMsg());
			txtDate.setText(reminderEntity.getDate());
			txtTime.setText(reminderEntity.getTime());
			
			//set again m, d, y, h, minute
			String[] date = reminderEntity.getDate().split("/");
			m = Integer.parseInt(date[0]);
			m--;
			d = Integer.parseInt(date[1]);
			y = Integer.parseInt(date[2]);			
			
			String[] time = reminderEntity.getTime().split(":");
			h = Integer.parseInt(time[0]);
			min = Integer.parseInt(time[1]);			
		}

		btnDate.setOnClickListener(this);

		btnTime.setOnClickListener(this);

		btnSet.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		Calendar c1 = Calendar.getInstance();			
		c1.set(y, m, d, h, min);
		long then = c1.getTimeInMillis();	
		switch (v.getId()) {
		case R.id.btnDate:
			DialogFragment dateFragment = new DatePickerFragment();
			dateFragment.show(getActivity().getFragmentManager(), "datePicker");			
			break;
		case R.id.btnTime:
			DialogFragment timeFragment = new TimePickerFragment();
			timeFragment.show(getActivity().getFragmentManager(), "timePicker");
			break;
		case R.id.btnSet:
					
			if (editTextReminderNote.getText().toString().equals("")) {
				Toast.makeText(getActivity(),
						"Please make some note",
						Toast.LENGTH_SHORT).show();
			} if (now > then) {
				Toast.makeText(getActivity(), "Please choose different datetime",
						Toast.LENGTH_SHORT).show();
			} else {
				String date = txtDate.getText().toString();
				String time = txtTime.getText().toString();
				if (_id.equals("add")) {

					long _id = reminderDataSource.insert(editTextReminderNote
							.getText().toString(), date, time);

					if (_id > 0) {
						Intent intent = new Intent(getActivity(),
								ReminderService.class);
						intent.setAction(ReminderService.CREATE);
						intent.putExtra(DatabaseHelper.REMINDERS_ID, (int) _id);
						intent.putExtra(DatabaseHelper.REMINDER_MSG,
								editTextReminderNote.getText().toString());
						intent.putExtra("day", d);
						intent.putExtra("month", m);
						intent.putExtra("year", y);
						intent.putExtra("hourOfDay", h);
						intent.putExtra("minute", min);
						getActivity().startService(intent);
					} else {
						Toast.makeText(getActivity(), "Error insert",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					reminderEntity = reminderDataSource.getOneReminder(Integer
							.parseInt(_id));
					reminderEntity.setDate(date);
					reminderEntity.setTime(time);

					int result = reminderDataSource.update(reminderEntity);

					if (result > 0) {
						Intent intent = new Intent(getActivity(),
								ReminderService.class);
						intent.setAction(ReminderService.CREATE);
						intent.putExtra(DatabaseHelper.REMINDERS_ID,
								reminderEntity.get_id());
						intent.putExtra(DatabaseHelper.REMINDER_MSG,
								reminderEntity.getMsg());
						intent.putExtra("day", d);
						intent.putExtra("month", m);
						intent.putExtra("year", y);
						intent.putExtra("hourOfDay", h);
						intent.putExtra("minute", min);
						getActivity().startService(intent);
					} else {
						Toast.makeText(getActivity(), "Error update",
								Toast.LENGTH_SHORT).show();
					}

				}
				Toast.makeText(getActivity(), "Your reminder has been set!",
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user	
			
			RemindersFragment.y = year;
			RemindersFragment.m = month;
			RemindersFragment.d = day;
			month++;
			RemindersFragment.txtDate.setText(month + "/" + day + "/" + year);
		}
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			RemindersFragment.h = hourOfDay;
			RemindersFragment.min = minute;
			if (hourOfDay < 10 && minute < 10) {
				RemindersFragment.txtTime.setText("0" + hourOfDay + ":" + "0" + minute);
			} else if (hourOfDay < 10 && minute >= 10) {
				RemindersFragment.txtTime.setText("0" + hourOfDay + ":" + minute);
			} else if (hourOfDay >= 10 && minute < 10) {
				RemindersFragment.txtTime.setText(hourOfDay + ":" + "0" + minute);
			} else {
				RemindersFragment.txtTime.setText(hourOfDay + ":" + minute);
			}			
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reminderDataSource.open();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		reminderDataSource.close();
	}
}
