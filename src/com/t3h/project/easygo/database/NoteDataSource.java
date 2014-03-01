/**
 * 
 */
package com.t3h.project.easygo.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.t3h.project.easygo.entity.NoteEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author My PC
 * 
 */
public class NoteDataSource {
	private String[] columns = { DatabaseHelper.NOTE_ID,
			DatabaseHelper.NOTE_TITLE, DatabaseHelper.NOTE_CONTENT,
			DatabaseHelper.DATE, DatabaseHelper.TIME,
			DatabaseHelper.NOTE_CHECKED };
	NoteEntity entity;
	public List<NoteEntity> list;	

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<NoteEntity> list) {
		this.list = list;
	}

	SQLiteDatabase db;
	DatabaseHelper databaseHelper;
	Date cDate;

	/**
	 * 
	 */
	public NoteDataSource(Context context) {
		super();
		databaseHelper = new DatabaseHelper(context);
	}

	public void open() {
		db = databaseHelper.getWritableDatabase();
	}

	public void close() {
		databaseHelper.close();
	}

	public int insertNote(String title, String content) {
		synchronized (this) {
			long noteId = 0;
			
			try {				
				
				db.beginTransaction();
				cDate = new Date();
				String date = new SimpleDateFormat("MM/dd/yyyy",
						Locale.getDefault()).format(cDate);
				String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
						.format(cDate);
				ContentValues value = new ContentValues();
				value.put(DatabaseHelper.NOTE_TITLE, title);
				value.put(DatabaseHelper.NOTE_CONTENT, content);
				value.put(DatabaseHelper.DATE, date);
				value.put(DatabaseHelper.TIME, time);
				value.put(DatabaseHelper.NOTE_CHECKED, 0);
				noteId = db.insert(DatabaseHelper.NOTE, null, value);

				db.setTransactionSuccessful();				
				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
			
			if (noteId > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public int updateNote(NoteEntity entity) {
		synchronized (this) {
			int result = 0;
			
			try {
				db.beginTransaction();
				
				ContentValues values = new ContentValues();
				values.put(DatabaseHelper.NOTE_TITLE, entity.getNoteTitle());
				values.put(DatabaseHelper.NOTE_CONTENT, entity.getNoteContent());
				values.put(DatabaseHelper.DATE, entity.getNoteDate());
				values.put(DatabaseHelper.TIME, entity.getNoteTime());
				values.put(DatabaseHelper.NOTE_CHECKED, entity.getChecked());
				result = db.update(DatabaseHelper.NOTE, values,
						DatabaseHelper.NOTE_ID + "=" + entity.getNoteId(), null);
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}			

			return result;
		}
	}

	public int deleteNote(int _id) {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();
				
				result = db.delete(DatabaseHelper.NOTE, DatabaseHelper.NOTE_ID
						+ "=" + _id, null);
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}			

			return result;
		}

	}

	public List<NoteEntity> getAllNotes() {
		synchronized (this) {
			try {
				db.beginTransaction();
				
				list = new ArrayList<NoteEntity>();
				Cursor c = db.query(DatabaseHelper.NOTE, columns, null, null, null,
						null, null);
				int datePosition = c.getColumnIndex(DatabaseHelper.DATE);
				int timePosition = c.getColumnIndex(DatabaseHelper.TIME);
				int titlePosition = c.getColumnIndex(DatabaseHelper.NOTE_TITLE);
				int contentPosition = c.getColumnIndex(DatabaseHelper.NOTE_CONTENT);
				int id = c.getColumnIndex(DatabaseHelper.NOTE_ID);
				int checked = c.getColumnIndex(DatabaseHelper.NOTE_CHECKED);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					columns[0] = Integer.toString(c.getInt(id));
					columns[1] = c.getString(titlePosition);
					columns[2] = c.getString(contentPosition);
					columns[3] = c.getString(datePosition);
					columns[4] = c.getString(timePosition);

					entity = new NoteEntity(columns[0], columns[1], columns[2],
							columns[3], columns[4], c.getInt(checked));
					list.add(entity);
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

	public NoteEntity getOneNote(String _id) {
		synchronized (this) {
			NoteEntity entity = null;
			try {
				db.beginTransaction();
				
				Cursor c = db.rawQuery("select * from " + DatabaseHelper.NOTE
						+ " where " + DatabaseHelper.NOTE_ID + "=" + _id, null);
				c.moveToFirst();
				entity = new NoteEntity(_id, c.getString(c
						.getColumnIndex(DatabaseHelper.NOTE_TITLE)), c.getString(c
						.getColumnIndex(DatabaseHelper.NOTE_CONTENT)),
						c.getString(c.getColumnIndex(DatabaseHelper.DATE)),
						c.getString(c.getColumnIndex(DatabaseHelper.TIME)),
						c.getInt(c.getColumnIndex(DatabaseHelper.NOTE_CHECKED)));
				c.close();
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}			

			return entity;
		}

	}

	public int getTotalNumber() {
		synchronized (this) {			
			int result = 0;
			
			try {
				db.beginTransaction();
				
				Cursor c = db.rawQuery("select count(*) from "
						+ DatabaseHelper.NOTE, null);
				c.moveToFirst();
				result = c.getInt(0);
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}			

			return result;
		}
	}

	public int getNumberOfNoteImportant() {
		synchronized (this) {
			int result = 0;
			try {
				db.beginTransaction();
				
				Cursor c = db.rawQuery("select count(*) from "
						+ DatabaseHelper.NOTE + " where "
						+ DatabaseHelper.NOTE_CHECKED + "=" + 1, null);
				c.moveToFirst();
				result = c.getInt(0); 
				
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
