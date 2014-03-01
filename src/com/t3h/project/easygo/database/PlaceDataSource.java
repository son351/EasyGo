/**
 * 
 */
package com.t3h.project.easygo.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.t3h.project.easygo.entity.PlaceEntity;

/**
 * @author My PC
 * 
 */
public class PlaceDataSource {
//	private String[] columns = { DatabaseHelper.PLACE_ID,
//			DatabaseHelper.PLACE_NAME, DatabaseHelper.DATE, DatabaseHelper.TIME };
	public List<PlaceEntity> list;
	private PlaceEntity placeEntity;
	private SQLiteDatabase db;
	private DatabaseHelper databaseHelper;
	private Date cDate;

	/**
	 * 
	 */
	public PlaceDataSource(Context context) {
		super();
		databaseHelper = new DatabaseHelper(context);
	}

	public void open() {
		db = databaseHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public long insert(String placeName) {
		synchronized (this) {
			long _id = 0;
			try {
				db.beginTransaction();
				 
				cDate = new Date();
				String date = new SimpleDateFormat("MM/dd/yyyy",
						Locale.getDefault()).format(cDate);
				String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
						.format(cDate);
				
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.PLACE_NAME, placeName);
				values.put(DatabaseHelper.DATE, date);
				values.put(DatabaseHelper.TIME, time);
				
				_id = db.insert(DatabaseHelper.PLACE, null, values);

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return _id;
		}
	}

	public int update(PlaceEntity entity) {
		synchronized (this) {
			try {
				db.beginTransaction();
				
				

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
		}
		return 0;
	}

	public int delete(int _id) {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();
				
				result = db.delete(DatabaseHelper.PLACE, DatabaseHelper.PLACE_ID + "=" + _id, null);

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return result;
		}		
	}

	public int getTotalNumber() {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();

				Cursor c = db.rawQuery("select count(*) from "
						+ DatabaseHelper.PLACE, null);
				c.moveToFirst();
				result = c.getInt(0);
				c.close();

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return result;
		}
	}

	public List<PlaceEntity> getAllRecords() {
		synchronized (this) {
			list = new ArrayList<PlaceEntity>();
			try {
				db.beginTransaction();

				Cursor c = db.rawQuery("select * from " + DatabaseHelper.PLACE,
						null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					placeEntity = new PlaceEntity(
							Integer.toString(c.getInt(c
									.getColumnIndex(DatabaseHelper.PLACE_ID))),
							c.getString(c
									.getColumnIndex(DatabaseHelper.PLACE_NAME)),
							c.getString(c.getColumnIndex(DatabaseHelper.DATE)),
							c.getString(c.getColumnIndex(DatabaseHelper.TIME)));
					list.add(placeEntity);
					c.moveToNext();
				}
				c.close();

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return list;
		}
	}
}
