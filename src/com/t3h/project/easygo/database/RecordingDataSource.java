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

import com.t3h.project.easygo.entity.RecordEntity;

/**
 * @author My PC
 * 
 */
public class RecordingDataSource {
	private String[] columns = { DatabaseHelper.RECORDING_ID,
			DatabaseHelper.RECORDING_FILENAME, DatabaseHelper.RECORDING_NOTE,
			DatabaseHelper.DATE, DatabaseHelper.TIME };
	private RecordEntity recordEntity;
	public List<RecordEntity> list;
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	private Date mDate;

	/**
	 * 
	 */
	public RecordingDataSource(Context context) {
		super();
		databaseHelper = new DatabaseHelper(context);

	}

	public void open() {
		db = databaseHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public long insertRecord(String recordNote, String fileName) {
		synchronized (this) {
			try {
				db.beginTransaction();

				mDate = new Date();
				String date = new SimpleDateFormat("MM/dd/yyyy",
						Locale.getDefault()).format(mDate);
				String time = new SimpleDateFormat("HH:mm:ss",
						Locale.getDefault()).format(mDate);
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.RECORDING_FILENAME, fileName);
				values.put(DatabaseHelper.RECORDING_NOTE, recordNote);
				values.put(DatabaseHelper.DATE, date);
				values.put(DatabaseHelper.TIME, time);

				long _id = db.insert(DatabaseHelper.RECORDING, null, values);

				db.setTransactionSuccessful();

				return _id;
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
		}
		return 0;
	}

	public int updateRecord(RecordEntity entity) {
		synchronized (this) {
			int rows = 0;
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.RECORDING_FILENAME, entity.getRecordFileName());
				values.put(DatabaseHelper.RECORDING_NOTE, entity.getRecordNote());
				values.put(DatabaseHelper.DATE, entity.getRecordDate());
				values.put(DatabaseHelper.TIME, entity.getRecordTime());
				rows = db.update(DatabaseHelper.RECORDING, values,
						DatabaseHelper.RECORDING_ID + "=" + entity.getRecordId(), null);

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return rows;
		}
	}

	public int deleteRecord(String _id) {
		synchronized (this) {
			int rows = 0;
			try {
				db.beginTransaction();

				rows = db.delete(DatabaseHelper.RECORDING,
						DatabaseHelper.RECORDING_ID + "=" + _id, null);

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return rows;
		}
	}

	public RecordEntity getOneRecord(String _id) {
		synchronized (this) {

			try {
				db.beginTransaction();

				Cursor c = db.query(DatabaseHelper.RECORDING, columns,
						DatabaseHelper.RECORDING_ID + "=" + _id, null, null,
						null, null);
				c.moveToFirst();

				recordEntity = new RecordEntity(
						_id,
						c.getString(c
								.getColumnIndex(DatabaseHelper.RECORDING_FILENAME)),
						c.getString(c
								.getColumnIndex(DatabaseHelper.RECORDING_NOTE)),
						c.getString(c.getColumnIndex(DatabaseHelper.DATE)), c
								.getString(c
										.getColumnIndex(DatabaseHelper.TIME)));
				c.close();

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return recordEntity;
		}

	}

	public List<RecordEntity> getAllRecord() {
		synchronized (this) {
			list = new ArrayList<RecordEntity>();
			try {
				db.beginTransaction();

				Cursor c = db.rawQuery("select * from "
						+ DatabaseHelper.RECORDING, null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					recordEntity = new RecordEntity(
							Integer.toString(c.getInt(c
									.getColumnIndex(DatabaseHelper.RECORDING_ID))),
							c.getString(c
									.getColumnIndex(DatabaseHelper.RECORDING_FILENAME)),
							c.getString(c
									.getColumnIndex(DatabaseHelper.RECORDING_NOTE)),
							c.getString(c.getColumnIndex(DatabaseHelper.DATE)),
							c.getString(c.getColumnIndex(DatabaseHelper.TIME)));
					list.add(recordEntity);
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

	public int getTotalNumberOfRecord() {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();

				Cursor c = db.rawQuery("select count(*) from "
						+ DatabaseHelper.RECORDING, null);
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
	
	public int getTotalNumber(){
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();
				
				Cursor c = db.rawQuery("select count(*) from " + DatabaseHelper.RECORDING, null);
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

}
