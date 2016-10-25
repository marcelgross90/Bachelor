package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.owlike.genson.Genson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.fiw.fhws.lecturers.adapter.LecturerDetailAdapter;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.network.HttpHeroSingleton;
import de.marcelgross.httphero.HttpHeroResponse;
import de.marcelgross.httphero.HttpHeroResultListener;
import de.marcelgross.httphero.request.Request;

public class LecturerDetailActivity extends AppCompatActivity implements View.OnClickListener {
	private Toolbar toolbar;
	private ImageView imageView;
	private RecyclerView recyclerView;
	private Lecturer currentLecturer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecturer_detail);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		imageView = (ImageView) findViewById(R.id.ivLecturerPicture);
		recyclerView = (RecyclerView) findViewById(R.id.rvLecturerDetails);

		setUpToolbar();

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);

		loadLecturer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.lecturer_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
				return true;
			case R.id.edit_lecturer:
				return true;
			case R.id.delete_lecturer:
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
		String selfUrl = getIntent().getExtras().getString("selfUrl");
		HttpHeroSingleton heroSingleton = HttpHeroSingleton.getInstance();

		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(selfUrl);
		builder.setMediaType("application/json");

		heroSingleton.getHttpHero().performRequest(builder.get(), new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				Genson genson = new Genson();
				currentLecturer = genson.deserialize(httpHeroResponse.getData(), Lecturer.class);
				setUp(currentLecturer);
			}

			@Override
			public void onFailure() {
				Toast.makeText(LecturerDetailActivity.this, R.string.load_lecturer_error, Toast.LENGTH_LONG).show();
			}
		});
	}

	private void setUp(Lecturer lecturer) {
		displayLecturerPicture(lecturer.getUrlProfileImage());
		recyclerView.setAdapter(new LecturerDetailAdapter(lecturer, LecturerDetailActivity.this));
	}

	private void displayLecturerPicture(String pictureUrl) {
		Target target = new Target() {
			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {
			}

			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
				Bitmap editedBitmap = Bitmap.createBitmap(bitmap, 0, 50, bitmap.getWidth(), 300);
				BitmapDrawable drawable = new BitmapDrawable(getResources(), editedBitmap);
				imageView.setImageDrawable(drawable);
			}

			@Override
			public void onBitmapFailed(Drawable errorDrawable) {
			}
		};
		Picasso.with(getApplicationContext()).load(pictureUrl).into(target);
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

		}
	}
}