package de.fiw.fhws.lecturers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Map;

import de.fiw.fhws.lecturers.fragment.LecturerListFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.model.Link;

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


		if (savedInstanceState == null) {
			initialNetworkRequest();
		}
	}

	private void initialNetworkRequest() {
		NetworkClient client = new NetworkClient(this, new NetworkRequest().url(getResources().getString(R.string.entry_url)));
		client.sendRequest(new NetworkCallback() {
			@Override
			public void onFailure() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, R.string.load_lecturer_error, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onSuccess(NetworkResponse response) {

				Map<String, Link> linkHeader = response.getLinkHeader();

				Link allLecturersLink = linkHeader.get(getResources().getString(R.string.rel_type_get_all_lecturers));
				Bundle bundle = new Bundle();
				bundle.putString("url", allLecturersLink.getHrefWithoutQueryParams());
				bundle.putString("mediaType", allLecturersLink.getType());
				Fragment fragment = new LecturerListFragment();
				fragment.setArguments(bundle);
				replaceFragment(fragmentManager, fragment);

			}
		});
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

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
					fragmentManager.getBackStackEntryCount() > 1
			);
		}
	}

}