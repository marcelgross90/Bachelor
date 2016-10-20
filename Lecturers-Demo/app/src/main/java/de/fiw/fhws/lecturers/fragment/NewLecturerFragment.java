package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.fiw.fhws.lecturers.R;

public class NewLecturerFragment extends Fragment {

	private View view;

	public NewLecturerFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_lecturer_input, container, false);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.save_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.saveLecturer:
//				MainActivity.replaceFragment(getFragmentManager(), new NewLecturerFragment());
				Snackbar.make(view, R.string.save,  Snackbar.LENGTH_LONG).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}