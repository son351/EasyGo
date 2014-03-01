/**
 * 
 */
package com.t3h.project.easygo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author My PC
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// Database
	private static final String DATABASE_NAME = "easyGo.Db";
	private static final int DATABASE_VERSION = 1;
	public static final String DATE = "date";
	public static final String TIME = "time";

	// Table Reminders
	public static final String REMINDERS = "reminders";
	public static final String REMINDERS_ID = "_remindersId";
	public static final String REMINDER_MSG = "reminderMsg";

	private static final String REMINDERS_CREATE = "create table " + REMINDERS
			+ " (" + REMINDERS_ID + " integer primary key autoincrement,"
			+ REMINDER_MSG + " text not null," + DATE + " text not null,"
			+ TIME + " text not null" + ");";

	// Table Note
	public static final String NOTE = "note";
	public static final String NOTE_ID = "_noteId";
	public static final String NOTE_TITLE = "noteTitle";
	public static final String NOTE_CONTENT = "noteContent";
	public static final String NOTE_CHECKED = "noteChecked";

	private static final String NOTE_CREATE = "create table " + NOTE + " ("
			+ NOTE_ID + " integer primary key autoincrement," + NOTE_TITLE
			+ " text not null," + NOTE_CONTENT + " text not null," + DATE
			+ " text not null," + TIME + " text not null," + NOTE_CHECKED
			+ " integer not null" + ");";

	// Table Place
	public static final String PLACE = "place";
	public static final String PLACE_ID = "_placeId";
	public static final String PLACE_NAME = "placeName";

	private static final String PLACE_CREATE = "create table " + PLACE + " ("
			+ PLACE_ID + " integer primary key autoincrement," + PLACE_NAME
			+ " text not null," + DATE + " text not null," + TIME + " text not null" + ");";

	// Table Recording
	public static final String RECORDING = "recording";
	public static final String RECORDING_ID = "_recordingId";
	public static final String RECORDING_FILENAME = "recordingFileName";
	public static final String RECORDING_NOTE = "recordingNote";

	// private static final String RECORDING_CREATE = "";
	private static final String RECORDING_CREATE = "create table " + RECORDING
			+ " (" + RECORDING_ID + " integer primary key autoincrement,"
			+ RECORDING_FILENAME + " text not null," + RECORDING_NOTE
			+ " text not null," + DATE + " text not null," + TIME
			+ " text not null" + ");";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(REMINDERS_CREATE);
		db.execSQL(NOTE_CREATE);
		db.execSQL(RECORDING_CREATE);
		db.execSQL(PLACE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w("SON: Database ", "Change db from " + oldVersion + " to "
				+ newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + REMINDERS);
		db.execSQL("DROP TABLE IF EXISTS " + NOTE);
		db.execSQL("DROP TABLE IF EXISTS " + RECORDING);
		db.execSQL("DROP TABLE IF EXISTS " + PLACE);
		onCreate(db);
	}

}
