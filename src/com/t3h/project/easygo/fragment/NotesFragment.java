/**
 * 
 */
package com.t3h.project.easygo.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.t3h.project.easygo.R;
import com.t3h.project.easygo.database.DatabaseHelper;
import com.t3h.project.easygo.database.NoteDataSource;
import com.t3h.project.easygo.entity.NoteEntity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author My PC
 * 
 */
public class NotesFragment extends Fragment implements OnClickListener {
	View v;
	EditText editTitleNotes, editContentNotes;
	Button btnSaveNotes;
	private NoteDataSource noteDataSource;
	String _noteId;
	NoteEntity entity;
	Date cDate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().hide();
		
		v = (View) inflater.inflate(R.layout.notes_layout, null);
		editTitleNotes = (EditText) v.findViewById(R.id.editTitleNotes);
		editContentNotes = (EditText) v.findViewById(R.id.editContentNotes);

		btnSaveNotes = (Button) v.findViewById(R.id.btnSaveNotes);

		noteDataSource = new NoteDataSource(getActivity());
		noteDataSource.open();

		_noteId = getArguments().getString(DatabaseHelper.NOTE_ID);
		if (!_noteId.equals("add")) {
			// Edit
			entity = noteDataSource.getOneNote(_noteId);
			editTitleNotes.setText(entity.getNoteTitle());
			editContentNotes.setText(entity.getNoteContent());
		}

		btnSaveNotes.setOnClickListener(this);

		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String title = editTitleNotes.getText().toString();
		String content = editContentNotes.getText().toString();
		if (title.equals("") && content.equals("")) {
			Toast.makeText(getActivity(), "Can not save an empty note!",
					Toast.LENGTH_SHORT).show();
		} else {
			if (title.equals("") && !content.equals("")) {
				title = content;
			}
			int result;
			switch (v.getId()) {
			case R.id.btnSaveNotes:
				if (!_noteId.equals("add")) {
					// Update
					entity.setNoteTitle(title);
					entity.setNoteContent(content);

					cDate = new Date();
					String date = new SimpleDateFormat("MM/dd/yyyy",
							Locale.getDefault()).format(cDate);
					String time = new SimpleDateFormat("HH:mm:ss",
							Locale.getDefault()).format(cDate);

					entity.setNoteDate(date);
					entity.setNoteTime(time);
					
					result = noteDataSource.updateNote(entity);
					
					if (result > 0) {
						Toast.makeText(getActivity(), "Saved",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Error, can not save!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					// Add
					
					result = noteDataSource.insertNote(title, content);
					
					if (result == 1) {
						Toast.makeText(getActivity(), "Saved",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Error, can not save!",
								Toast.LENGTH_SHORT).show();
					}
				}

				editTitleNotes.setEnabled(false);
				editContentNotes.setEnabled(false);
				btnSaveNotes.setEnabled(false);
				break;

			default:
				break;
			}			
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		noteDataSource.open();
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		super.onPause();
	}
	
}
