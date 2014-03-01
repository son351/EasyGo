/**
 * 
 */
package com.t3h.project.easygo;

import android.widget.ListView;

import com.t3h.project.easygo.adapter.DrawerAdapter;

/**
 * @author My PC
 * 
 */
public class Config {
	public static final String DRAWER_LIST_POSITION = "position";
	public static int POSITION_ID = 0;
	public static final String ACTION = "action";
	public static final String ITEM_ID = "id";
	public static String FRAGMENT_TAG = "";	
	
	//Number of items on each fragments
	public static int numberOfNotes = 0;	
	public static int numberOfImportant = 0;
	public static int numberOfPlaces = 0;
	public static int numberOfRecording = 0;
	public static int numberOfReminder = 0;
	
	public static DrawerAdapter drawerAdapter;
	public static ListView mDrawerList;
}
