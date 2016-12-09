package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.fragment.EditLecturerFragment;
import de.marcelgross.lecturer_lib.generic.activity.ResourceActivity;

public class LecturerActivity extends ResourceActivity {

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
	}

	@Override
	protected Fragment handleIntentAndPrepareFragment(Intent intent) {
		Bundle bundle = new Bundle();
		bundle.putString("url", intent.getStringExtra("url"));
		bundle.putString("mediaType", intent.getStringExtra("mediaType"));

		Fragment fragment = new EditLecturerFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	protected void setUpToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		setTitle(R.string.edit);
	}
}
