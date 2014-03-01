/**
 * 
 */
package com.t3h.project.easygo.entity;

/**
 * @author My PC
 *
 */
public class NoteEntity {
	private String noteId, noteTitle, noteContent, noteDate, noteTime;
	private int noteChecked;

	/**
	 * @param noteId
	 * @param noteTitle
	 * @param noteContent
	 * @param noteDate
	 * @param noteTime
	 */
	public NoteEntity(String noteId, String noteTitle, String noteContent,
			String noteDate, String noteTime, int checked) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteDate = noteDate;
		this.noteTime = noteTime;
		this.noteChecked = checked;
	}

	/**
	 * @return the checked
	 */
	public int getChecked() {
		return noteChecked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(int checked) {
		this.noteChecked = checked;
	}

	/**
	 * @return the noteId
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return the noteTitle
	 */
	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param noteTitle the noteTitle to set
	 */
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	/**
	 * @return the noteContent
	 */
	public String getNoteContent() {
		return noteContent;
	}

	/**
	 * @param noteContent the noteContent to set
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	/**
	 * @return the noteDate
	 */
	public String getNoteDate() {
		return noteDate;
	}

	/**
	 * @param noteDate the noteDate to set
	 */
	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}

	/**
	 * @return the noteTime
	 */
	public String getNoteTime() {
		return noteTime;
	}

	/**
	 * @param noteTime the noteTime to set
	 */
	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}
}
