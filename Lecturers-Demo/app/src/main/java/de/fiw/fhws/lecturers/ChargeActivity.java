package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import static de.fiw.fhws.lecturers.FragmentHandler.startChargesListFragment;

public class ChargeActivity extends AppCompatActivity {


	private FragmentManager fragmentManager;

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
	public boolean onSupportNavigateUp() {
		finish();

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge);

		fragmentManager = getSupportFragmentManager();
		setUpToolbar();

		Intent intent = getIntent();
		if (savedInstanceState == null && intent != null) {
			String name = intent.getStringExtra("name");
			setTitle(name);
			String url = intent.getStringExtra("url");
			String mediaType = intent.getStringExtra("mediaType");

			startChargesListFragment(fragmentManager, url, mediaType);

		}
	}

	private void setUpToolbar() {
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
		if( actionBar != null ) {
			actionBar.setDisplayHomeAsUpEnabled(
					fragmentManager.getBackStackEntryCount() > 0
			);
		}
	}
}
