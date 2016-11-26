package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.owlike.genson.Genson;

import java.util.Map;

import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.customView.LecturerInputView;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;

public class EditLecturerActivity extends AppCompatActivity {

	private final Genson genson = new Genson();

	private Toolbar toolbar;
	private Link lecturerEditLink;
	private LecturerInputView lecturerInputView;

	private String url;
	private String mediaType;

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
		setContentView(R.layout.activity_edit_lecturer);

		Intent intent = getIntent();

		if (intent != null) {
			this.url = intent.getStringExtra("url");
			this.mediaType = intent.getStringExtra("mediaType");
		}

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		lecturerInputView = (LecturerInputView) findViewById(R.id.lecturer_input);

		setUpToolbar();
		loadLecturer(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.save_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			case R.id.saveItem:
				saveLecturer();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void loadLecturer(String selfUrl) {
		NetworkClient client = new NetworkClient(this, new NetworkRequest().url(selfUrl).acceptHeader(mediaType));
		client.sendRequest(new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final Lecturer lecturer = genson.deserialize(response.getResponseReader(), Lecturer.class);
				Map<String, Link> linkHeader = response.getLinkHeader();
				lecturerEditLink = linkHeader.get(EditLecturerActivity.this.getString(R.string.rel_type_update_lecturer));

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						lecturerInputView.setLecturer(lecturer);
					}
				});
			}
		});
	}

	private void setUpToolbar() {
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		setTitle(R.string.edit);
	}

	private void saveLecturer() {
		Lecturer lecturer = lecturerInputView.getLecturer();

		if (lecturer != null) {
			String lecturerJson = genson.serialize(lecturer);

			NetworkClient client = new NetworkClient(this, new NetworkRequest().url(lecturerEditLink.getHref()).put(lecturerJson, lecturerEditLink.getType()));
			client.sendRequest(new NetworkCallback() {
				@Override
				public void onFailure() {
				}

				@Override
				public void onSuccess(NetworkResponse response) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(EditLecturerActivity.this, R.string.lecturer_updated, Toast.LENGTH_SHORT).show();
							Intent intent = new Intent();
							setResult(1, intent);
							finish();
						}
					});
				}
			});
		}

	}
}
