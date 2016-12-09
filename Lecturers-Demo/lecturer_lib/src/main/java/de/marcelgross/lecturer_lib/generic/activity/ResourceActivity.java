package de.marcelgross.lecturer_lib.generic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.util.FragmentHandler;

public abstract class ResourceActivity extends AppCompatActivity {

	protected FragmentManager fragmentManager;

	protected abstract Fragment handleIntentAndPrepareFragment(Intent intent);

	protected abstract void setUpToolbar();

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getSupportFragmentManager();

		setUpToolbar();

		Intent intent = getIntent();

		if (savedInstanceState == null && intent != null) {
			Fragment fragment = handleIntentAndPrepareFragment(intent);


			FragmentHandler.replaceFragment(fragmentManager, fragment);
		}
	}
}
