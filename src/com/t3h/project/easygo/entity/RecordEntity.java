/**
 * 
 */
package com.t3h.project.easygo.entity;

/**
 * @author My PC
 *
 */
public class RecordEntity {
	private String recordId, recordFileName, recordNote, recordDate, recordTime;

	/**
	 * @param recordId
	 * @param recordFileName
	 * @param recordNote
	 * @param recordData
	 * @param recordTime
	 */
	public RecordEntity(String recordId, String recordFileName,
			String recordNote, String recordDate, String recordTime) {
		super();
		this.recordId = recordId;
		this.recordFileName = recordFileName;
		this.recordNote = recordNote;
		this.recordDate = recordDate;
		this.recordTime = recordTime;
	}

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the recordFileName
	 */
	public String getRecordFileName() {
		return recordFileName;
	}

	/**
	 * @param recordFileName the recordFileName to set
	 */
	public void setRecordFileName(String recordFileName) {
		this.recordFileName = recordFileName;
	}

	/**
	 * @return the recordNote
	 */
	public String getRecordNote() {
		return recordNote;
	}

	/**
	 * @param recordNote the recordNote to set
	 */
	public void setRecordNote(String recordNote) {
		this.recordNote = recordNote;
	}

	/**
	 * @return the recordData
	 */
	public String getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordData the recordData to set
	 */
	public void setRecordData(String recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * @return the recordTime
	 */
	public String getRecordTime() {
		return recordTime;
	}

	/**
	 * @param recordTime the recordTime to set
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
}
