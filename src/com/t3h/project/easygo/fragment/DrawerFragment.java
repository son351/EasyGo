/**
 * 
 */
package com.t3h.project.easygo.fragment;

import com.t3h.project.easygo.Config;
import com.t3h.project.easygo.RemindersActivity;
import com.t3h.project.easygo.NotesActivity;
import com.t3h.project.easygo.PlacesActivity;
import com.t3h.project.easygo.R;
import com.t3h.project.easygo.RecordingActivity;
import com.t3h.project.easygo.adapter.ListNotesAdapter;
import com.t3h.project.easygo.adapter.ListPlaceAdapter;
import com.t3h.project.easygo.adapter.ListRecordAdapter;
import com.t3h.project.easygo.adapter.ListReminderAdapter;
import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.database.NoteDataSource;
import com.t3h.project.easygo.database.PlaceDataSource;
import com.t3h.project.easygo.database.RecordingDataSource;
import com.t3h.project.easygo.database.ReminderDataSource;
import com.t3h.project.easygo.service.ReminderService;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DrawerFragment extends Fragment implements OnItemClickListener {
	private ListView listItems;
	RelativeLayout layout;
	private TextView empty;
	private Button btnEmail;
	private Intent intent;

	private NoteDataSource noteDataSource;
	private RecordingDataSource recordingDataSource;
	private ReminderDataSource reminderDataSource;
	private PlaceDataSource placeDataSource;

	private ListNotesAdapter listNotesAdapter;
	private ListRecordAdapter listRecordAdapter;
	private ListReminderAdapter listReminderAdapter;
	private ListPlaceAdapter listPlaceAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		noteDataSource = new NoteDataSource(getActivity());
		noteDataSource.open();
		recordingDataSource = new RecordingDataSource(getActivity());
		recordingDataSource.open();
		reminderDataSource = new ReminderDataSource(getActivity());
		reminderDataSource.open();
		placeDataSource = new PlaceDataSource(getActivity());
		placeDataSource.open();

		Config.POSITION_ID = getArguments().getInt(Config.DRAWER_LIST_POSITION);

		View v = inflater.inflate(R.layout.items_fragment, container, false);

		layout = (RelativeLayout) v.findViewById(R.id.fragmentLayout);
		btnEmail = (Button) v.findViewById(R.id.btnSendEmail);
		listItems = (ListView) v.findViewById(R.id.listItems);
		empty = (TextView) v.findViewById(R.id.empty);
		listItems.setEmptyView(empty);

		String[] listItems = getResources().getStringArray(R.array.fragments);

		getActivity().getActionBar().setTitle(listItems[Config.POSITION_ID]);

		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);

		if (Config.POSITION_ID == 0 || Config.POSITION_ID == 2
				|| Config.POSITION_ID == 3 || Config.POSITION_ID == 4) {
			menu.add("Search")
					.setIcon(R.drawable.search)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_IF_ROOM
									| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			menu.add("Add").setIcon(R.drawable.add)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		} else if (Config.POSITION_ID == 1) {
			menu.add("Search")
					.setIcon(R.drawable.search)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_IF_ROOM
									| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// If hit Add button, it should transfer user to their desire activity
		// with extra info
		// because on each activity (reminders, notes, places, recording)
		// there will be one main .xml layout that contains frameLayout
		// and on each moment, it should return one fragment regarding to an
		// action

		if (item.getTitle().equals("Add")) {
			switch (Config.POSITION_ID) {
			case 0:
				intent = new Intent(getActivity().getBaseContext(),
						RemindersActivity.class);
				intent.putExtra(Config.ACTION, 0);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(getActivity().getBaseContext(),
						NotesActivity.class);
				intent.putExtra(Config.ACTION, 0);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(getActivity().getBaseContext(),
						PlacesActivity.class);
				intent.putExtra(Config.ACTION, 0);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(getActivity().getBaseContext(),
						RecordingActivity.class);
				intent.putExtra(Config.ACTION, 0);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Please choose an option!");
		menu.add("Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final int position = info.position;

		if (item.getTitle().equals("Delete")) {
			if (getCurrentFragment().equals("notes")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Delete: "
						+ noteDataSource.list.get(position).getNoteTitle());
				builder.setMessage("Are you sure you want to delete this?");
				builder.setIcon(R.drawable.delete);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								int result = noteDataSource.deleteNote(Integer
										.parseInt(noteDataSource.list.get(
												position).getNoteId()));
								if (result == 0) {
									Toast.makeText(getActivity(),
											"Error, can not delete ",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "Deleted",
											Toast.LENGTH_SHORT).show();
									noteDataSource.list.remove(position);
									listNotesAdapter.notifyDataSetChanged();

									// change number of items on drawer
									// navigation
									Config.numberOfNotes = noteDataSource
											.getTotalNumber();
									ListView list = (ListView) getActivity()
											.findViewById(R.id.drawer_list);
									((BaseAdapter) list.getAdapter())
											.notifyDataSetChanged();

								}
							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

				builder.create().show();
			} else if (getCurrentFragment().equals("recording")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Delete: "
						+ recordingDataSource.list.get(position)
								.getRecordNote());
				builder.setMessage("Are you sure you want to delete this?");
				builder.setIcon(R.drawable.delete);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								int result = recordingDataSource
										.deleteRecord(recordingDataSource.list
												.get(position).getRecordId());
								if (result == 0) {
									Toast.makeText(getActivity(),
											"Error, can not delete ",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "Deleted",
											Toast.LENGTH_SHORT).show();
									recordingDataSource.list.remove(position);
									listRecordAdapter.notifyDataSetChanged();

									// change number of items on drawer
									// navigation
									Config.numberOfRecording = recordingDataSource
											.getTotalNumber();
									ListView list = (ListView) getActivity()
											.findViewById(R.id.drawer_list);
									((BaseAdapter) list.getAdapter())
											.notifyDataSetChanged();

								}
							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

				builder.create().show();
			} else if (getCurrentFragment().equals("reminders")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Delete: "
						+ reminderDataSource.list.get(position).getMsg()
								.toString());
				builder.setMessage("Are you sure you want to delete this?");
				builder.setIcon(R.drawable.delete);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								int _id = Integer
										.parseInt(reminderDataSource.list.get(
												position).get_id());
								int result = reminderDataSource.delete(_id);
								if (result == 0) {
									Toast.makeText(getActivity(),
											"Error, can not delete ",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "Deleted",
											Toast.LENGTH_SHORT).show();
									reminderDataSource.list.remove(position);
									listReminderAdapter.notifyDataSetChanged();

									// change number of items on drawer
									// navigation
									Config.numberOfReminder = reminderDataSource
											.getTotalNumbers();
									ListView list = (ListView) getActivity()
											.findViewById(R.id.drawer_list);
									((BaseAdapter) list.getAdapter())
											.notifyDataSetChanged();

									// Cancel Service
									Intent intent = new Intent(getActivity(),
											ReminderService.class);
									intent.putExtra(
											DatabaseHelper.REMINDERS_ID, _id);
									intent.setAction(ReminderService.CANCEL);
									getActivity().startService(intent);
								}
							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

				builder.create().show();
			} else if (getCurrentFragment().equals("places")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Delete: "
						+ placeDataSource.list.get(position).getPlaceName()
								.toString());
				builder.setMessage("Are you sure you want to delete this?");
				builder.setIcon(R.drawable.delete);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								int _id = Integer.parseInt(placeDataSource.list
										.get(position).get_id());
								int result = placeDataSource.delete(_id);
								if (result == 0) {
									Toast.makeText(getActivity(),
											"Error, can not delete ",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "Deleted",
											Toast.LENGTH_SHORT).show();
									placeDataSource.list.remove(position);
									listPlaceAdapter.notifyDataSetChanged();

									// change number of items on drawer
									// navigation
									Config.numberOfPlaces = placeDataSource
											.getTotalNumber();
									ListView list = (ListView) getActivity()
											.findViewById(R.id.drawer_list);
									((BaseAdapter) list.getAdapter())
											.notifyDataSetChanged();
								}
							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

				builder.create().show();
			}
		} else if (item.getTitle().equals("Marks as important")) {
			Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
		}

		return super.onContextItemSelected(item);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		noteDataSource.open();
		recordingDataSource.open();
		reminderDataSource.open();
		placeDataSource.open();

		switch (Config.POSITION_ID) {
		/*
		 * Reminders
		 */
		case 0:
			listReminderAdapter = new ListReminderAdapter(getActivity(),
					reminderDataSource.getAllRecords());
			listItems.setAdapter(listReminderAdapter);
			listItems.setOnItemClickListener(this);
			registerForContextMenu(listItems);
			break;
		/*
		 * Important
		 */
		case 1:
			// listItems.setAdapter(adapter);
			// Toast.makeText(getActivity(), Config.numberOfImportant + "",
			// Toast.LENGTH_SHORT).show();
			empty.setText("Something very important with you will be stayed here!");
			registerForContextMenu(listItems);
			break;
		/*
		 * Notes
		 */
		case 2:
			listNotesAdapter = new ListNotesAdapter(getActivity(),
					noteDataSource.getAllNotes());
			listItems.setAdapter(listNotesAdapter);
			listItems.setOnItemClickListener(this);
			registerForContextMenu(listItems);
			break;
		/*
		 * Places
		 */
		case 3:
			listPlaceAdapter = new ListPlaceAdapter(getActivity(),
					placeDataSource.getAllRecords());
			listItems.setAdapter(listPlaceAdapter);
			registerForContextMenu(listItems);
			break;
		/*
		 * Recording
		 */
		case 4:
			listRecordAdapter = new ListRecordAdapter(getActivity(),
					recordingDataSource.getAllRecord());
			listItems.setAdapter(listRecordAdapter);
			listItems.setOnItemClickListener(this);
			registerForContextMenu(listItems);
			break;
		/*
		 * Feedback
		 */
		case 5:
			btnEmail.setVisibility(View.VISIBLE);
			btnEmail.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					String[] recipients = new String[] {
							"nghoangson68@gmail.com", "", };
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							recipients);
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"EasyGo feedback");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Feedback: ");
					emailIntent.setType("text/plain");
					startActivity(Intent.createChooser(emailIntent,
							"Send mail..."));
				}
			});
			empty.setText("Please send us some feedback\nto make this app better");
			break;
		/*
		 * About
		 */
		case 6:
			empty.setText("EasyGo\n\n\n1.0\n\n\n\nCopyright @ 2013 EasyGo. All rights Reserved."
					+ "\n\n--- David Nguyen ---");
			break;
		default:
			break;
		}

		Config.numberOfImportant = noteDataSource.getNumberOfNoteImportant();
		Config.numberOfNotes = noteDataSource.getTotalNumber();
		Config.numberOfRecording = recordingDataSource.getTotalNumber();
		Config.numberOfReminder = reminderDataSource.getTotalNumbers();
		Config.numberOfPlaces = placeDataSource.getTotalNumber();

		((BaseAdapter) Config.mDrawerList.getAdapter()).notifyDataSetChanged();
		if (listNotesAdapter != null && listRecordAdapter != null
				&& listReminderAdapter != null && listPlaceAdapter != null) {
			((BaseAdapter) listNotesAdapter).notifyDataSetChanged();
			((BaseAdapter) listRecordAdapter).notifyDataSetChanged();
			((BaseAdapter) listReminderAdapter).notifyDataSetChanged();
			((BaseAdapter) listPlaceAdapter).notifyDataSetChanged();
		}

		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		recordingDataSource.close();
		reminderDataSource.close();
		placeDataSource.close();
		super.onPause();
	}

	public String getCurrentFragment() {
		String where = "";
		if (Config.FRAGMENT_TAG.equals("Reminders")) {
			where = "reminders";
		} else if (Config.FRAGMENT_TAG.equals("Notes")) {
			where = "notes";
		} else if (Config.FRAGMENT_TAG.equals("Places")) {
			where = "places";
		} else if (Config.FRAGMENT_TAG.equals("Recording")) {
			where = "recording";
		}
		return where;
	}

	public Intent setIntent(String where, int position) {
		Intent intent = null;
		if (where.equals("reminders")) {
			intent = new Intent(getActivity().getBaseContext(),
					RemindersActivity.class);
			intent.putExtra(Config.ACTION, 1);
			intent.putExtra(DatabaseHelper.REMINDERS_ID,
					reminderDataSource.list.get(position).get_id());
		} else if (where.equals("notes")) {
			intent = new Intent(getActivity().getBaseContext(),
					NotesActivity.class);
			intent.putExtra(Config.ACTION, 1);
			intent.putExtra(DatabaseHelper.NOTE_ID,
					noteDataSource.list.get(position).getNoteId());
		} else if (where.equals("places")) {
			intent = new Intent(getActivity().getBaseContext(),
					PlacesActivity.class);
			intent.putExtra(Config.ACTION, position);
		} else if (where.equals("recording")) {
			intent = new Intent(getActivity().getBaseContext(),
					RecordingActivity.class);
			intent.putExtra(Config.ACTION, 1);
			intent.putExtra(DatabaseHelper.RECORDING_ID,
					recordingDataSource.list.get(position).getRecordId());
		}
		return intent;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub
		startActivity(setIntent(getCurrentFragment(), position));
	}
}
