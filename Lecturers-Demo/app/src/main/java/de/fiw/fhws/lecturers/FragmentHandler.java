package de.fiw.fhws.lecturers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import de.fiw.fhws.lecturers.fragment.LecturerListFragment;
import de.marcelgross.lecturer_lib.model.Link;

public class FragmentHandler {

	public static void replaceFragment(FragmentManager fm, Fragment fragment) {
		fm.beginTransaction()
				.replace(
						R.id.content_container,
						fragment, fragment.getClass().getName())
				.addToBackStack(null)
				.commit();
	}

	public static void replaceFragmentPopBackStack(FragmentManager fm, Fragment fragment) {
		fm.popBackStack();
		replaceFragment(fm, fragment);
	}

	public static void startLecturerListFragment(FragmentManager fm, Link allLecturerLink) {
		Bundle bundle = new Bundle();
		bundle.putString("type", allLecturerLink.getType());
		bundle.putString("url", allLecturerLink.getHref());

		Fragment fragment = new LecturerListFragment();
		fragment.setArguments(bundle);

		replaceFragment(fm, fragment);
	}

}
