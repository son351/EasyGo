/**
 * 
 */
package com.t3h.project.easygo.entity;

/**
 * @author My PC
 *
 */
public class PlaceEntity {
	String _id, placeName, date, time;

	/**
	 * @param _id
	 * @param placeName
	 * @param date
	 * @param time
	 */
	public PlaceEntity(String _id, String placeName, String date, String time) {
		super();
		this._id = _id;
		this.placeName = placeName;
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
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}

	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
