package de.fiw.fhws.lecturers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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

}
