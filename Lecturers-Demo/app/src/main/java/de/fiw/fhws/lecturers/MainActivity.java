package de.fiw.fhws.lecturers;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import de.fiw.fhws.lecturers.network.util.HeaderParser;
import de.marcelgross.lecturer_lib.model.Link;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static de.fiw.fhws.lecturers.FragmentHandler.startLecturerListFragment;

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
			initialNetworkRequest();
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

	private void initialNetworkRequest() {
		OkHttpClient client = OKHttpSingleton.getInstance(this).getClient();
		Request request = new Request.Builder()
				.url(getResources().getString(R.string.entry_url))
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Headers headers = response.headers();
				List<String> linkHeaderList = headers.toMultimap().get("link");
				Map<String, Link> linkHeader = new HashMap<>();
				if (linkHeaderList != null && linkHeaderList.size() > 0)
					linkHeader = HeaderParser.getLinks(linkHeaderList);

				final Link allLecturersLink;
				if (linkHeader.size() > 0) {
					allLecturersLink = linkHeader.get(MainActivity.this.getResources().getString(R.string.rel_type_get_all_lecturers));
				} else {
					allLecturersLink = null;
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (allLecturersLink == null) {
							Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_SHORT).show();
						} else {
							startLecturerListFragment(fragmentManager, allLecturersLink);
						}
					}
				});
			}
		});

	}

}