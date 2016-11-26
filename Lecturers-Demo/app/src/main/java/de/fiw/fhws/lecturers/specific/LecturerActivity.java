package de.fiw.fhws.lecturers.specific;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.specific.fragment.EditLecturerFragment;
import de.fiw.fhws.lecturers.util.FragmentHandler;

public class LecturerActivity extends AppCompatActivity {

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturer);

		Intent intent = getIntent();

		if (intent != null) {
			Bundle bundle = new Bundle();
			bundle.putString("url", intent.getStringExtra("url"));
			bundle.putString("mediaType", intent.getStringExtra("mediaType"));

			Fragment fragment = new EditLecturerFragment();
			fragment.setArguments(bundle);
			FragmentHandler.replaceFragment(getSupportFragmentManager(), fragment);
		}

		setUpToolbar();
	}

	private void setUpToolbar() {
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
