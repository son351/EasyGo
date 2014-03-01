package com.t3h.project.easygo;

import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.fragment.RemindersFragment;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class RemindersActivity extends FragmentActivity {
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

		// Add or Edit
		intent = getIntent();
		int value = intent.getIntExtra(Config.ACTION, 0);
		if (value == 0) {
			Bundle data = new Bundle();
			RemindersFragment remindersFragment = new RemindersFragment();
			data.putString(DatabaseHelper.REMINDERS_ID, "add");
			remindersFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, remindersFragment)
					.commit();
		} else {
			Bundle data = new Bundle();
			String _reminderId = intent.getStringExtra(DatabaseHelper.REMINDERS_ID);			
			data.putString(DatabaseHelper.REMINDERS_ID, _reminderId);	
			RemindersFragment remindersFragment = new RemindersFragment();
			remindersFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, remindersFragment)
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
