package de.fiw.fhws.lecturers;

import android.support.v4.app.Fragment;

import de.fiw.fhws.lecturers.fragment.LecturerListFragment;
import de.marcelgross.lecturer_lib.generic.activity.AbstractMainActivity;

public class MainActivity extends AbstractMainActivity {

	@Override
	protected Fragment getStartFragment() {
		return new LecturerListFragment();
	}


	@Override
	protected int getLoadErrorMessage() {
		return R.string.load_lecturer_error;
	}
}