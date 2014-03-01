package com.t3h.project.easygo;

import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.fragment.NotesFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;

public class NotesActivity extends FragmentActivity {
	private Intent intent;
	Bundle data = new Bundle();
	NotesFragment notesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		notesFragment = new NotesFragment();

		intent = getIntent();
		int value = intent.getIntExtra(Config.ACTION, 0);
		if (value == 0) {
			// Add new note
			data.putString(DatabaseHelper.NOTE_ID, "add");
			notesFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, notesFragment)
					.commit();
		} else {
			// Edit note at specific position
			String _noteId = intent.getStringExtra(DatabaseHelper.NOTE_ID);			
			data.putString(DatabaseHelper.NOTE_ID, _noteId);			
			notesFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, notesFragment)
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				this.finish();
//				startActivity(new Intent(NotesActivity.this, MainActivity.class));				
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
