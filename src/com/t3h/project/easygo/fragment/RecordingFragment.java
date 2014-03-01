/**
 * 
 */
package com.t3h.project.easygo.fragment;

import java.io.File;
import java.io.IOException;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.database.RecordingDataSource;
import com.t3h.project.easygo.entity.RecordEntity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class RecordingFragment extends Fragment implements OnClickListener {
	EditText editTextRecordNote;
	Button btnStartRecord, btnStopRecord;
	TextView txtTimer;
	MediaPlayer mPlayer;
	MediaRecorder mRecorder;
	String mFileName = "";
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	String _id;

	private final String pathToExternalStorage = Environment
			.getExternalStorageDirectory().toString();
	private final String appDirectory = pathToExternalStorage + "/" + "EasyGo";
	private final String recordDirectory = appDirectory + "/" + "Record";
	private RecordingDataSource recordingDataSource;
	private RecordEntity recordEntity;

	private Handler mHandler = new Handler();
	private String hours, minutes, seconds, milliseconds;
	private long secs, mins, hrs;
	private final int REFRESH_RATE = 100;
	private long elapsedTime;
	private long startTime;
	private boolean stopped = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		recordingDataSource = new RecordingDataSource(getActivity());
		recordingDataSource.open();

		View v = inflater.inflate(R.layout.record_layout, null);
		editTextRecordNote = (EditText) v.findViewById(R.id.editTextRecordNote);
		txtTimer = (TextView) v.findViewById(R.id.txtTimer);
		btnStartRecord = (Button) v.findViewById(R.id.startButton);
		btnStopRecord = (Button) v.findViewById(R.id.stopButton);

		_id = getArguments().getString(DatabaseHelper.RECORDING_ID);
		if (!_id.equals("add")) {
			recordEntity = recordingDataSource.getOneRecord(_id);
			editTextRecordNote.setText(recordEntity.getRecordNote());
			mFileName = recordEntity.getRecordFileName();
		} else {
			int number = recordingDataSource.getTotalNumberOfRecord();
			number++;
			mFileName = recordDirectory + "/Record" + number + ".3gp";
		}

		btnStartRecord.setOnClickListener(this);
		btnStopRecord.setOnClickListener(this);

		btnStopRecord.setEnabled(false);

		// check media availability
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// Being able to read and write data
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// Only read data
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something went wrong
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		// Create directory
		File folder = new File(appDirectory);
		folder.mkdirs();
		folder = new File(recordDirectory);
		folder.mkdirs();

		setHasOptionsMenu(true);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startButton:
			if (editTextRecordNote.getText().toString().matches("")) {
				Toast.makeText(getActivity(),
						"Please type some words\nbefore you can save!",
						Toast.LENGTH_SHORT).show();
			} else {
				onRecord();
				btnStopRecord.setEnabled(true);
				btnStartRecord.setEnabled(false);
			}

			break;
		case R.id.stopButton:
			onStopRecord();
			btnStopRecord.setEnabled(false);
			btnStartRecord.setEnabled(true);

			break;
		default:
			break;
		}
	}

	private void onRecord() {
		// TODO Auto-generated method stub
		if (stopped) {
			startTime = System.currentTimeMillis() - elapsedTime;
		} else {
			startTime = System.currentTimeMillis();
		}
		mHandler.removeCallbacks(startTimer);
		mHandler.postDelayed(startTimer, 0);

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("SON", "prepare failed!");
		}

		mRecorder.start();

		Toast.makeText(getActivity(), "Recording", Toast.LENGTH_SHORT).show();

	}

	private void onStopRecord() {
		// TODO Auto-generated method stub

		if (_id.equals("add")) {
			long _id = recordingDataSource.insertRecord(editTextRecordNote
					.getText().toString(), mFileName);
			if (_id > 0) {
				Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			recordEntity = recordingDataSource.getOneRecord(_id);
			recordEntity.setRecordNote(editTextRecordNote.getText().toString());
			int result = recordingDataSource.updateRecord(recordEntity);
			if (result > 0) {
				Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "Error Update",
						Toast.LENGTH_SHORT).show();
			}
		}

		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;

		mHandler.removeCallbacks(startTimer);
		stopped = true;

		Toast.makeText(getActivity(), "Record successful!", Toast.LENGTH_SHORT)
				.show();
		txtTimer.setText("00:00:00");
	}

	private Runnable startTimer = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			elapsedTime = System.currentTimeMillis() - startTime;
			updateTimer(elapsedTime);
			mHandler.postDelayed(this, REFRESH_RATE);
		}
	};

	private void updateTimer(float time) {
		secs = (long) (time / 1000);
		mins = (long) ((time / 1000) / 60);
		hrs = (long) (((time / 1000) / 60) / 60);
		secs = secs % 60;
		seconds = String.valueOf(secs);
		if (secs == 0) {
			seconds = "00";
		}
		if (secs < 10 && secs > 0) {
			seconds = "0" + seconds;
		} /* Convert the minutes to String and format the String */
		mins = mins % 60;
		minutes = String.valueOf(mins);
		if (mins == 0) {
			minutes = "00";
		}
		if (mins < 10 && mins > 0) {
			minutes = "0" + minutes;
		} /* Convert the hours to String and format the String */
		hours = String.valueOf(hrs);
		if (hrs == 0) {
			hours = "00";
		}
		if (hrs < 10 && hrs > 0) {
			hours = "0" + hours;
		} /*
		 * Extra milliseconds when needed
		 */
		milliseconds = String.valueOf((long) time);
		if (milliseconds.length() == 2) {
			milliseconds = "0" + milliseconds;
		}
		if (milliseconds.length() <= 1) {
			milliseconds = "00";
		}
		milliseconds = milliseconds.substring(milliseconds.length() - 3,
				milliseconds.length() - 2);
		txtTimer.setText(hours + ":" + minutes + ":" + seconds);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		recordingDataSource.open();
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		recordingDataSource.close();

		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
}
