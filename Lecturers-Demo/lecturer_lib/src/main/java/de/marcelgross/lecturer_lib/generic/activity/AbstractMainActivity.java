package de.marcelgross.lecturer_lib.generic.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Map;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkClient;
import de.marcelgross.lecturer_lib.generic.network.NetworkRequest;
import de.marcelgross.lecturer_lib.generic.network.NetworkResponse;

import static de.marcelgross.lecturer_lib.generic.util.FragmentHandler.replaceFragment;


public abstract class AbstractMainActivity extends AppCompatActivity {

	protected abstract Fragment getStartFragment();

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
						Toast.makeText(AbstractMainActivity.this, R.string.load_lecturer_error, Toast.LENGTH_SHORT).show();
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
				Fragment fragment = getStartFragment();
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