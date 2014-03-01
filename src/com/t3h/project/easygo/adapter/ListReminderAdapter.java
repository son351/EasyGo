/**
 * 
 */
package com.t3h.project.easygo.adapter;

import java.util.List;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.entity.ReminderEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author My PC
 * 
 */
public class ListReminderAdapter extends BaseAdapter {
	private Context context;
	private List<ReminderEntity> list;

	/**
	 * @param context
	 * @param list
	 */
	public ListReminderAdapter(Context context, List<ReminderEntity> list) {
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
		TextView txtSetAt, txtReminderContent, txtDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = (View) inflater.inflate(
					R.layout.list_reminder_items_layout, null);

			holder.txtReminderContent = (TextView) convertView
					.findViewById(R.id.txtReminderContent);
			holder.txtSetAt = (TextView) convertView
					.findViewById(R.id.txtSetAt);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtReminderContent.setText(list.get(position).getMsg()
				.toString());
		holder.txtSetAt.setText("Remind me at " + list.get(position).getTime().toString());
		holder.txtDate.setText(list.get(position).getDate().toString());

		convertView.setTag(holder);

		return convertView;
	}

}
