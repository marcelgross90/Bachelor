package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.fragment.ChargeListFragment;
import de.marcelgross.lecturer_lib.generic.activity.ResourceActivity;

public class ChargeActivity extends ResourceActivity {


	@Override
	public void onBackPressed() {
		if (fragmentManager.getBackStackEntryCount() > 1)
			super.onBackPressed();
		else {
			finish();
			overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
		}
	}

	@Override
	protected Fragment handleIntentAndPrepareFragment(Intent intent) {
		String name = intent.getStringExtra("name");
		setTitle(name);

		Fragment fragment = new ChargeListFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", intent.getStringExtra("url"));
		bundle.putString("mediaType", intent.getStringExtra("mediaType"));

		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	protected void setUpToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		fragmentManager.addOnBackStackChangedListener(
				new FragmentManager.OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						canBack();
					}
				}
		);
		canBack();
	}

	private void canBack() {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(
					fragmentManager.getBackStackEntryCount() > 0
			);
		}
	}
}