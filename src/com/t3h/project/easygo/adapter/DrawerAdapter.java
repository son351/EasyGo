/**
 * 
 */
package com.t3h.project.easygo.adapter;

import com.t3h.project.easygo.Config;
import com.t3h.project.easygo.R;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author My PC
 * 
 */
public class DrawerAdapter extends BaseAdapter {
	private Context context;
	private String[] items;	

	/**
	 * @param context
	 * @param items
	 */
	public DrawerAdapter(Context context, String[] items) {
		super();
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		ImageView img;
		TextView txtTitle, txtNumberItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = (View) inflater.inflate(R.layout.drawer_list_layout,
					null);

			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.imgItems);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtItems);
			holder.txtNumberItems = (TextView) convertView
					.findViewById(R.id.txtNoItems);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Resources res = context.getResources();
		int imgID = res.getIdentifier(items[position].toLowerCase(),
				"drawable", context.getPackageName());
		holder.img.setImageResource(imgID);
		holder.txtTitle.setText(items[position]);

		// update number of items in drawer navigation
		switch (position) {		
		case 0:
			if (Config.numberOfReminder == 0) {				
				holder.txtNumberItems.setVisibility(View.GONE);
			} else {
				holder.txtNumberItems.setText(Config.numberOfReminder + "");
			}
			break;
		case 1:						
			//update number of item in drawer navigation
			if (Config.numberOfImportant == 0) {				
				holder.txtNumberItems.setVisibility(View.GONE);
			} else {
				holder.txtNumberItems.setText(Config.numberOfImportant + "");
			}
			break;
		case 2:
			if (Config.numberOfNotes == 0) {
				holder.txtNumberItems.setVisibility(View.GONE);
			} else {
				holder.txtNumberItems.setText(Config.numberOfNotes + "");
			}

			break;
		case 3:
			if (Config.numberOfPlaces == 0) {
				holder.txtNumberItems.setVisibility(View.GONE);
			} else {
				holder.txtNumberItems.setText(Config.numberOfPlaces + "");
			}
			break;
		case 4:
			if (Config.numberOfRecording == 0) {
				holder.txtNumberItems.setVisibility(View.GONE);
			} else {
				holder.txtNumberItems.setText(Config.numberOfRecording + "");
			}
			break;
		default:
			holder.txtNumberItems.setVisibility(View.GONE);
			break;
		}

		convertView.setTag(holder);
		return convertView;
	}

}
