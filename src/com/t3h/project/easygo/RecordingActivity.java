package com.t3h.project.easygo;

import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.database.RecordingDataSource;
import com.t3h.project.easygo.fragment.RecordingFragment;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;

public class RecordingActivity extends FragmentActivity {
	private Intent intent;
	private RecordingDataSource recordingDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recording);
		
		recordingDataSource = new RecordingDataSource(this);
		recordingDataSource.open();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

		// Add or Edit
		intent = getIntent();
		int value = intent.getIntExtra(Config.ACTION, 0);
			
		if (value == 0) {
			//Add new Record
			Bundle data = new Bundle();
			data.putString(DatabaseHelper.RECORDING_ID, "add");
			RecordingFragment recordingFragment = new RecordingFragment();			
			recordingFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.record_frame_container, recordingFragment).commit();
		} else {
			//Edit specific record at record_id 
			String _id = intent.getStringExtra(DatabaseHelper.RECORDING_ID);
			Bundle data = new Bundle();
			data.putString(DatabaseHelper.RECORDING_ID, _id);
			RecordingFragment recordingFragment = new RecordingFragment();
			recordingFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.record_frame_container, recordingFragment)
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Config.numberOfRecording = recordingDataSource.getTotalNumber();
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				this.finish();
//				startActivity(new Intent(RecordingActivity.this, MainActivity.class));				
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		recordingDataSource.close();
	}

}
