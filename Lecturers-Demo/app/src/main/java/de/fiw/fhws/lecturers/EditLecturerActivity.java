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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import de.fiw.fhws.lecturers.network.util.HeaderParser;
import de.marcelgross.lecturer_lib.customView.LecturerInputView;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
		finish();

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
			case R.id.saveLecturer:
				saveLecturer();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void loadLecturer(String selfUrl) {
		final Request request = new Request.Builder()
				.header("Accept", mediaType)
				.url(selfUrl)
				.build();

		OkHttpClient client = OKHttpSingleton.getInstance(this).getClient();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				}
				final Lecturer lecturer = genson.deserialize(response.body().charStream(), Lecturer.class);

				Map<String, List<String>> headers = response.headers().toMultimap();
				Map<String, Link> linkHeader = HeaderParser.getLinks(headers.get("link"));
				lecturerEditLink = linkHeader.get("updateLecturer");

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

			OkHttpClient client = OKHttpSingleton.getInstance(this).getClient();
			RequestBody body = RequestBody.create(MediaType.parse(lecturerEditLink.getType()), lecturerJson);
			final Request request = new Request.Builder()
					.url(lecturerEditLink.getHref())
					.put(body)
					.build();

			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected code " + response);
					}
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
