package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import de.marcelgross.lecturer_lib.customView.LecturerDetailView;
import de.fiw.fhws.lecturers.fragment.DeleteDialogFragment;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;
import de.fiw.fhws.lecturers.network.util.HeaderParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LecturerDetailActivity extends AppCompatActivity implements View.OnClickListener, DeleteDialogFragment.DeleteDialogListener {
	private LecturerDetailView lecturerDetailView;
	private final Genson genson = new Genson();
	private Link deleteLink;
	private Link updateLink;
	private Toolbar toolbar;
	private Lecturer currentLecturer;

	@Override
	public void onDialogClosed(boolean successfullyDeleted) {
		if (successfullyDeleted) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, R.string.delete_error, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturer_detail);

		lecturerDetailView = (LecturerDetailView) findViewById(R.id.detail_view);
		toolbar = (Toolbar) findViewById(R.id.toolbar);

		setUpToolbar();

		loadLecturer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail_menu, menu);
		MenuItem deleteItem = menu.findItem(R.id.delete_item);
		MenuItem updateItem = menu.findItem(R.id.edit_item);
		deleteItem.setVisible(deleteLink != null);
		updateItem.setVisible(updateLink != null);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
				return true;
			case R.id.edit_item:
				Intent intent = new Intent(LecturerDetailActivity.this, EditLecturerActivity.class);
				intent.putExtra("url", updateLink.getHref());
				intent.putExtra("mediaType", updateLink.getType());
				startActivityForResult(intent, 1);
				return true;
			case R.id.delete_item:
				Bundle bundle = new Bundle();
				bundle.putString("url", deleteLink.getHref());
				bundle.putString("name", currentLecturer.getFirstName() + " " + currentLecturer.getLastName());
				DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
				deleteDialogFragment.setArguments(bundle);
				deleteDialogFragment.show(getSupportFragmentManager(), null);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void setUpToolbar() {
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		String fullName = getIntent().getExtras().getString("fullName");
		setTitle(fullName);
	}

	private void loadLecturer() {
		Intent intent = getIntent();
		String selfUrl = intent.getExtras().getString("selfUrl", "");
		String mediaType = intent.getExtras().getString("mediaType", "");


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
				currentLecturer = lecturer;
				Map<String, List<String>> headers = response.headers().toMultimap();
				Map<String, Link> linkHeader = HeaderParser.getLinks(headers.get("link"));
				deleteLink = linkHeader.get(LecturerDetailActivity.this.getString(R.string.rel_type_create_delete_lecturer));
				updateLink = linkHeader.get(LecturerDetailActivity.this.getString(R.string.rel_type_create_update_lecturer));

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setUp(lecturer);
					}
				});
			}
		});
	}

	private void setUp(Lecturer lecturer) {
		invalidateOptionsMenu();
		lecturerDetailView.setUpView(lecturer, this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tvEmailValue:
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + currentLecturer.getEmail()));
				startActivity(Intent.createChooser(intent, "Send Email"));
				break;

			case R.id.tvPhoneValue:
				Intent intent1 = new Intent(Intent.ACTION_DIAL);
				String p = "tel:" + currentLecturer.getPhone();
				intent1.setData(Uri.parse(p));
				startActivity(intent1);
				break;

			case R.id.tvWebsiteValue:
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.setData(Uri.parse(currentLecturer.getUrlWelearn()));
				startActivity(intent2);
				break;

			case R.id.tvAddressValue:
				Uri location = Uri.parse("geo:0,0?q=" + currentLecturer.getAddress());
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
				mapIntent.setPackage("com.google.android.apps.maps");
				startActivity(mapIntent);
				break;

			case R.id.charges_btn:
				Intent intent3 = new Intent(LecturerDetailActivity.this, ChargeActivity.class);
				intent3.putExtra("name", currentLecturer.getFirstName() + " " +currentLecturer.getLastName());
				intent3.putExtra("url", currentLecturer.getChargeUrl().getHref());
				intent3.putExtra("mediaType", currentLecturer.getChargeUrl().getType());
				startActivity(intent3);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			loadLecturer();
		}
	}
}