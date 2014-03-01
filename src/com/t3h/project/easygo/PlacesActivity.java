package com.t3h.project.easygo;

import com.t3h.project.easygo.fragment.PlacesFragment;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class PlacesActivity extends FragmentActivity {
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

		// Add or Edit
		intent = getIntent();
		int value = intent.getIntExtra(Config.ACTION, 0);
		if (value == 0) {
			PlacesFragment placesAddFragment = new PlacesFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, placesAddFragment)
					.commit();
		} else {
			Bundle data = new Bundle();
			data.putInt(Config.ITEM_ID, value);
			PlacesFragment placesFragment = new PlacesFragment();
			placesFragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.inbox_frame_container, placesFragment)
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
