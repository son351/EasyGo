/**
 * 
 */
package com.t3h.project.easygo.database;

import java.util.ArrayList;
import java.util.List;

import com.t3h.project.easygo.entity.ReminderEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author My PC
 * 
 */
public class ReminderDataSource {
	private String[] columns = { DatabaseHelper.REMINDERS_ID,
			DatabaseHelper.REMINDER_MSG, DatabaseHelper.DATE,
			DatabaseHelper.TIME };
	private SQLiteDatabase db;
	private DatabaseHelper databaseHelper;
	public List<ReminderEntity> list;
	private ReminderEntity reminderEntity;

	/**
	 * 
	 */
	public ReminderDataSource(Context context) {
		super();
		databaseHelper = new DatabaseHelper(context);
	}

	public void open() {
		db = databaseHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public long insert(String msg, String date, String time) {
		synchronized (this) {
			long _id = 0;
			try {
				db.beginTransaction();
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.REMINDER_MSG, msg);
				values.put(DatabaseHelper.DATE, date);
				values.put(DatabaseHelper.TIME, time);
				_id = db.insert(DatabaseHelper.REMINDERS, null, values);
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return _id;
		}
	}

	public int update(ReminderEntity reminderEntity) {
		synchronized (this) {
			int rows = 0;
			try {
				db.beginTransaction();
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.REMINDER_MSG, reminderEntity.getMsg());
				values.put(DatabaseHelper.DATE, reminderEntity.getDate());
				values.put(DatabaseHelper.TIME, reminderEntity.getTime());
				rows = db.update(
						DatabaseHelper.REMINDERS,
						values,
						DatabaseHelper.REMINDERS_ID + "="
								+ reminderEntity.get_id(), null);
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return rows;
		}
	}

	public int delete(int _id) {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();

				result = db.delete(DatabaseHelper.REMINDERS,
						DatabaseHelper.REMINDERS_ID + "=" + _id, null);
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return result;
		}
	}

	public int getTotalNumbers() {
		synchronized (this) {
			int total = 0;
			try {
				db.beginTransaction();

				Cursor c = db.rawQuery("select count(*) from "
						+ DatabaseHelper.REMINDERS, null);
				c.moveToFirst();
				total = c.getInt(0);
				c.close();

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return total;
		}
	}

	public List<ReminderEntity> getAllRecords() {
		synchronized (this) {
			list = new ArrayList<ReminderEntity>();
			try {
				db.beginTransaction();

				Cursor c = db.query(DatabaseHelper.REMINDERS, columns, null,
						null, null, null, null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					reminderEntity = new ReminderEntity(
							Integer.toString(c.getInt(c
									.getColumnIndex(DatabaseHelper.REMINDERS_ID))),
							c.getString(c
									.getColumnIndex(DatabaseHelper.REMINDER_MSG)),
							c.getString(c.getColumnIndex(DatabaseHelper.DATE)),
							c.getString(c.getColumnIndex(DatabaseHelper.TIME)));

					list.add(reminderEntity);
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

	public ReminderEntity getOneReminder(int _id) {
		synchronized (this) {
			try {
				db.beginTransaction();

				Cursor c = db.query(DatabaseHelper.REMINDERS, columns,
						DatabaseHelper.REMINDERS_ID + "=" + _id, null, null,
						null, null);
				c.moveToFirst();
				reminderEntity = new ReminderEntity(Integer.toString(c.getInt(c
						.getColumnIndex(DatabaseHelper.REMINDERS_ID))),
						c.getString(c
								.getColumnIndex(DatabaseHelper.REMINDER_MSG)),
						c.getString(c.getColumnIndex(DatabaseHelper.DATE)),
						c.getString(c.getColumnIndex(DatabaseHelper.TIME)));
				c.close();

				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			return reminderEntity;
		}
	}

}
