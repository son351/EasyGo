package com.t3h.project.easygo;

import com.t3h.project.easygo.adapter.DrawerAdapter;
import com.t3h.project.easygo.database.NoteDataSource;
import com.t3h.project.easygo.database.PlaceDataSource;
import com.t3h.project.easygo.database.RecordingDataSource;
import com.t3h.project.easygo.database.ReminderDataSource;
import com.t3h.project.easygo.fragment.DrawerFragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener, DrawerListener {
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	private DrawerLayout mDrawerLayout;

	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle = "";
	private String[] mDrawerItems;

	private NoteDataSource noteDataSource;
	private RecordingDataSource recordingDataSource;
	private ReminderDataSource reminderDataSource;
	private PlaceDataSource placeDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();

		noteDataSource = new NoteDataSource(this);
		recordingDataSource = new RecordingDataSource(this);
		reminderDataSource = new ReminderDataSource(this);
		placeDataSource = new PlaceDataSource(this);

		noteDataSource.open();
		recordingDataSource.open();
		reminderDataSource.open();
		placeDataSource.open();

		Config.numberOfImportant = noteDataSource.getNumberOfNoteImportant();
		Config.numberOfNotes = noteDataSource.getTotalNumber();
		Config.numberOfRecording = recordingDataSource.getTotalNumber();
		Config.numberOfReminder = reminderDataSource.getTotalNumbers();
		Config.numberOfPlaces = placeDataSource.getTotalNumber();

		mDrawerItems = getResources().getStringArray(R.array.fragments);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		Config.mDrawerList = (ListView) findViewById(R.id.drawer_list);

		mTitle = (String) getTitle();

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		Config.mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems));
		Config.mDrawerList.setOnItemClickListener(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#000000")));

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	private void selectItem(int position) {
		// TODO Auto-generated method stub
		DrawerFragment drawerFragment = new DrawerFragment();
		Bundle args = new Bundle();
		args.putInt(Config.DRAWER_LIST_POSITION, position);
		drawerFragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();

		// Set tag for fragment so that in DrawerFragment class, It can be check
		// which fragment user are choosing
		Config.FRAGMENT_TAG = mDrawerItems[position];
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, drawerFragment).commit();

		// update selected item and title, then close the drawer
		getActionBar().setTitle(mTitle);
		mDrawerLayout.closeDrawer(Config.mDrawerList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub
		Config.mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		Config.mDrawerList.setItemChecked(position, true);
		Config.mDrawerList.getFocusables(position);
		Config.mDrawerList.setSelected(true);
		selectItem(position);
	}

	@Override
	public void onDrawerClosed(View v) {
		// TODO Auto-generated method stub
		getActionBar().setTitle(mTitle);		
		invalidateOptionsMenu();		
	}

	@Override
	public void onDrawerOpened(View v) {
		// TODO Auto-generated method stub
		getActionBar().setTitle("Select an option");		
		invalidateOptionsMenu();		
	}

	@Override
	public void onDrawerSlide(View v, float arg1) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** Handling the touch event of app icon */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(Config.mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				if (event.getDownTime() - lastPressedTime < PERIOD) {
					this.finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Press again to exit.", Toast.LENGTH_SHORT).show();
					lastPressedTime = event.getEventTime();
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		noteDataSource.open();
		recordingDataSource.open();
		reminderDataSource.open();
		placeDataSource.open();
		((BaseAdapter) Config.mDrawerList.getAdapter()).notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		recordingDataSource.close();
		reminderDataSource.close();
		placeDataSource.close();
		super.onPause();
	}
}
