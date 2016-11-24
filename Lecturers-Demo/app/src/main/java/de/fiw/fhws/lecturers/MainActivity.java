package de.fiw.fhws.lecturers;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.fragment.LecturerListFragment;

import static de.fiw.fhws.lecturers.util.FragmentHandler.replaceFragment;

public class MainActivity extends AppCompatActivity {

	private FragmentManager fragmentManager;

	@Override
	public void onBackPressed() {
		if (fragmentManager.getBackStackEntryCount() > 1)
			super.onBackPressed();
		else
			finish();
	}

	@Override
	public boolean onSupportNavigateUp() {
		fragmentManager.popBackStack();

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getSupportFragmentManager();

		initToolbar();


		if( savedInstanceState == null ) {
			replaceFragment(fragmentManager, new LecturerListFragment());
		}
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );

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
					fragmentManager.getBackStackEntryCount() > 1
			);
		}
	}

}