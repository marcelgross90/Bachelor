package de.fiw.fhws.lecturers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.fiw.fhws.lecturers.fragment.LecturerListFragment;

public class MainActivity extends AppCompatActivity {

	private FragmentManager fragmentManager;

	public static void replaceFragment(FragmentManager fm, Fragment fragment) {
		fm.beginTransaction()
				.replace(
						R.id.content_container,
						fragment, fragment.getClass().getName())
				.addToBackStack(null)
				.commit();
	}

	public static void replaceFragmentPopBackStack(FragmentManager fm, Fragment fragment) {
		fm.popBackStack();
		replaceFragment(fm, fragment);
	}

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
			replaceFragment( fragmentManager, new LecturerListFragment() );
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

/*
	private void loadLecturers() {
		CollectionRequest collectionRequest = new CollectionRequest(MITARBEITER_URL, this, new CollectionRequest.CollectionRequestListener() {
			@Override
			public void onResponse(List<Lecturer> lecturer) {
				name.setText(lecturer.size() + "");
			}

			@Override
			public void onError(VolleyError error) {
				name.setText(error.getMessage());
			}
		});

		collectionRequest.sendRequest();
	}

	private void loadHeader() {
		LinkHeaderRequest headerRequest = new LinkHeaderRequest(MITARBEITER_URL + "/10", this, new LinkHeaderRequest.HeaderRequestListener() {
			@Override
			public void onResponse(HashMap<String, String> links) {
				StringBuilder builder = new StringBuilder();
				for (String s : links.keySet()) {
					builder.append(s);
					builder.append(" : ");
					builder.append(links.get(s));
					builder.append("\n");
				}
				name.setText(builder.toString());
			}

			@Override
			public void onResponse(String url) {
				name.setText(url);
			}

			@Override
			public void onError(VolleyError error) {
				name.setText(error.getMessage());
			}

		});

		headerRequest.sendRequest();
	}

	private void loadImage(String url) {
		ImageLoader imageLoader = VolleySingleton.getInstance(this).getImageLoader();
		imageView.setImageUrl(url, imageLoader);
	}

	private void assignLecturerToView(final Lecturer lecturer) {

		TextView title = (TextView) findViewById(R.id.title);
		TextView name = (TextView) findViewById(R.id.name);
		TextView email = (TextView) findViewById(R.id.email);
		TextView phone = (TextView) findViewById(R.id.phone);
		TextView address = (TextView) findViewById(R.id.address);
		TextView room = (TextView) findViewById(R.id.room);
		TextView welearn = (TextView) findViewById(R.id.welearn);

		NetworkImageView imageView = (NetworkImageView) findViewById(R.id.profileImg);

		title.setText(lecturer.getTitle());
		name.setText(lecturer.getFirstName() + " " + lecturer.getLastName());
		email.setText(lecturer.getEmail());
		email.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + lecturer.getEmail()));
				startActivity(Intent.createChooser(intent, "Send Email"));
				return false;
			}
		});
		phone.setText(lecturer.getPhone());
		phone.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Intent dialIntent = new Intent(Intent.ACTION_DIAL);
				dialIntent.setData(Uri.parse("tel:" + lecturer.getPhone()));
				startActivity(dialIntent);
				return false;
			}
		});
		address.setText(lecturer.getAddress());
		address.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Uri location = Uri.parse("geo:0,0?q=" + lecturer.getAddress());
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
				mapIntent.setPackage("com.google.android.apps.maps");
				startActivity(mapIntent);
				return false;
			}
		});
		room.setText(lecturer.getRoomNumber());
		welearn.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturer.getUrlWelearn()));
				startActivity(browserIntent);
				return false;
			}
		});
	}*/
}