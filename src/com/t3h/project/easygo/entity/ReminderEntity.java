/**
 * 
 */
package com.t3h.project.easygo.entity;

/**
 * @author My PC
 *
 */
public class ReminderEntity {
	private String _id, msg, date, time;

	/**
	 * @param _id
	 * @param msg
	 * @param date
	 * @param time
	 */
	public ReminderEntity(String _id, String msg, String date, String time) {
		super();
		this._id = _id;
		this.msg = msg;
		this.date = date;
		this.time = time;
	}

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
}
