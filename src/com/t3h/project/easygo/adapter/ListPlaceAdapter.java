/**
 * 
 */
package com.t3h.project.easygo.adapter;

import java.util.List;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.entity.PlaceEntity;

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
public class ListPlaceAdapter extends BaseAdapter {
	private Context context;
	private List<PlaceEntity> list;

	/**
	 * @param context
	 * @param list
	 */
	public ListPlaceAdapter(Context context, List<PlaceEntity> list) {
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
	
	static class ViewHolder{
		TextView txtPlace, txtDate, txtTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = (View) inflater.inflate(R.layout.list_place_items_layout, null);
			holder.txtPlace = (TextView) convertView.findViewById(R.id.txtPlace);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtPlace.setText(list.get(position).getPlaceName());
		holder.txtDate.setText(list.get(position).getDate());
		holder.txtTime.setText(list.get(position).getTime());
		
		convertView.setTag(holder);
		return convertView;
	}

}
