/**
 * 
 */
package com.t3h.project.easygo.adapter;

import java.io.IOException;
import java.util.List;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.RecordingDataSource;
import com.t3h.project.easygo.entity.RecordEntity;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class ListRecordAdapter extends BaseAdapter {
	private Context context;
	private List<RecordEntity> list;
	private MediaPlayer mPlayer;
	private String mFileName;
	private RecordingDataSource recordingDataSource;
	private RecordEntity recordEntity;

	/**
	 * @param context
	 * @param list
	 */
	public ListRecordAdapter(Context context, List<RecordEntity> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView txtRecordNote, txtRecordDate;
		ImageButton btnRecordPlay, btnStopPlaying;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = (View) inflater.inflate(
					R.layout.list_record_items_layout, null);

			holder = new ViewHolder();
			holder.txtRecordNote = (TextView) convertView
					.findViewById(R.id.txtRecordNote);
			holder.txtRecordDate = (TextView) convertView
					.findViewById(R.id.txtRecordDate);
			holder.btnRecordPlay = (ImageButton) convertView
					.findViewById(R.id.btnRecordPlay);
			holder.btnRecordPlay.setFocusable(false);
			holder.btnStopPlaying = (ImageButton) convertView
					.findViewById(R.id.btnStopPlaying);
			holder.btnStopPlaying.setFocusable(false);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RecordingDataSource recordingDataSource = new RecordingDataSource(
				context);
		recordingDataSource.open();
		RecordEntity recordEntity = recordingDataSource.getOneRecord(list
				.get(position).getRecordId().toString());
		recordingDataSource.close();

		if (recordEntity.getRecordNote().equals("")) {
			holder.txtRecordNote.setText("No Title");
		} else {
			holder.txtRecordNote.setText(recordEntity.getRecordNote());
			holder.txtRecordDate.setText(recordEntity.getRecordDate());
			holder.btnRecordPlay.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "Playing", Toast.LENGTH_SHORT)
							.show();
					onPlay(position);
					holder.btnRecordPlay.setVisibility(View.GONE);
					holder.btnStopPlaying.setVisibility(View.VISIBLE);
					if (!mPlayer.isPlaying()) {
						holder.btnRecordPlay.setVisibility(View.VISIBLE);
						holder.btnStopPlaying.setVisibility(View.GONE);
					}
				}
			});
			holder.btnStopPlaying
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(context, "Stop", Toast.LENGTH_SHORT)
									.show();
							onPause(position);
							holder.btnRecordPlay.setVisibility(View.VISIBLE);
							holder.btnStopPlaying.setVisibility(View.GONE);
						}
					});
		}

		convertView.setTag(holder);
		return convertView;
	}

	private void onPlay(int position) {
		// TODO Auto-generated method stub
		mPlayer = new MediaPlayer();
		try {
			recordingDataSource = new RecordingDataSource(context);
			recordingDataSource.open();
			recordEntity = recordingDataSource.getOneRecord(list.get(position)
					.getRecordId().toString());
			mFileName = recordEntity.getRecordFileName();
			recordingDataSource.close();
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mPlayer.start();
	}

	private void onPause(int position) {
		// TODO Auto-generated method stub
		mPlayer.release();
		mPlayer = null;
	}

}
