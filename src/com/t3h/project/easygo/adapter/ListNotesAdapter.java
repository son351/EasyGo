/**
 * 
 */
package com.t3h.project.easygo.adapter;

import java.util.List;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.NoteDataSource;
import com.t3h.project.easygo.entity.NoteEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class ListNotesAdapter extends BaseAdapter {
	private Context context;
	private List<NoteEntity> list;

	// private ImportantEntity entity;

	/**
	 * @param context
	 * @param list
	 */
	public ListNotesAdapter(Context context, List<NoteEntity> list) {
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
		TextView txtDate, txtTitle, txtTime;
		CheckBox cbImportant;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// Get entity to update CheckBox

		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = (View) inflater.inflate(R.layout.list_notes_items_layout,
					null);

			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtTitle);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.cbImportant = (CheckBox) convertView
					.findViewById(R.id.cbImportant);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtDate.setText(list.get(position).getNoteDate().toString());
		holder.txtTitle.setText(list.get(position).getNoteTitle().toString());
		holder.txtTime.setText(list.get(position).getNoteTime().toString());

		if (list.get(position).getChecked() == 1) {
			holder.cbImportant.setChecked(true);
		} else {
			holder.cbImportant.setChecked(false);
		}

		holder.cbImportant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			

				CheckBox cb = (CheckBox) v;
				if (cb.isChecked()) {
					list.get(position).setChecked(1);
					NoteDataSource noteDataSource = new NoteDataSource(context);
					noteDataSource.open();
					int result = noteDataSource.updateNote(list.get(position));
					noteDataSource.close();
					if (result > 0) {
						Toast.makeText(
								context,
								"update note succeed! at item id: "
										+ list.get(position).getNoteId(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Failed update note!",
								Toast.LENGTH_SHORT).show();
					}																			
					
				} else {
					list.get(position).setChecked(0);
					NoteDataSource noteDataSource = new NoteDataSource(context);
					noteDataSource.open();
					int result = noteDataSource.updateNote(list.get(position));
					noteDataSource.close();
					if (result > 0) {
						Toast.makeText(
								context,
								"unchecked at: "
										+ list.get(position).getNoteId(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								context,
								"Failed to uncheck at: "
										+ list.get(position).getNoteId(),
								Toast.LENGTH_SHORT).show();
					}		
				}
			}
		});

		convertView.setTag(holder);

		return convertView;
	}

}
