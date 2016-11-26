package de.fiw.fhws.lecturers.spezific;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.spezific.fragment.ChargeListFragment;
import de.fiw.fhws.lecturers.util.FragmentHandler;

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
		onBackPressed();

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

			Fragment fragment = new ChargeListFragment();
			Bundle bundle = new Bundle();
			bundle.putString("url", url);
			bundle.putString("mediaType", mediaType);

			fragment.setArguments(bundle);

			FragmentHandler.replaceFragment(fragmentManager, fragment);
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
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(
					fragmentManager.getBackStackEntryCount() > 0
			);
		}
	}
}