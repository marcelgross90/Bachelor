package de.fiw.fhws.lecturers;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.owlike.genson.Genson;

import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.model.Link;
import de.fiw.fhws.lecturers.network.HttpHeroSingleton;
import de.marcelgross.httphero.HttpHero;
import de.marcelgross.httphero.HttpHeroResponse;
import de.marcelgross.httphero.HttpHeroResultListener;
import de.marcelgross.httphero.request.Request;

public class EditLecturerActivity extends AppCompatActivity {

	private final Genson genson = new Genson();

	private Toolbar toolbar;
	private Lecturer currentLecturer;
	private Link lectuererEditLink;
	private EditText title;
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private EditText phoneNumber;
	private EditText address;
	private EditText room;
	private EditText welearn;

	private TextInputLayout titleInputLayout;
	private TextInputLayout firstNameInputLayout;
	private TextInputLayout lastNameInputLayout;
	private TextInputLayout emailInputLayout;
	private TextInputLayout phoneNumberInputLayout;
	private TextInputLayout addressInputLayout;
	private TextInputLayout roomInputLayout;
	private TextInputLayout welearnInputLayout;

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
		title = (EditText) findViewById(R.id.title);
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		email = (EditText) findViewById(R.id.email);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		address = (EditText) findViewById(R.id.address);
		room = (EditText) findViewById(R.id.room);
		welearn = (EditText) findViewById(R.id.welearn);

		titleInputLayout = (TextInputLayout) findViewById(R.id.titleInputLayout);
		firstNameInputLayout = (TextInputLayout) findViewById(R.id.firstNameInputLayout);
		lastNameInputLayout = (TextInputLayout) findViewById(R.id.lastNameInputLayout);
		emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
		phoneNumberInputLayout = (TextInputLayout) findViewById(R.id.phoneNumberInputLayout);
		addressInputLayout = (TextInputLayout) findViewById(R.id.addressInputLayout);
		roomInputLayout = (TextInputLayout) findViewById(R.id.roomInputLayout);
		welearnInputLayout = (TextInputLayout) findViewById(R.id.welearnInputLayout);

		setUpToolbar();
		loadLecturer(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
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
				validateAndSave();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void loadLecturer(String selfUrl) {
		HttpHeroSingleton heroSingleton = HttpHeroSingleton.getInstance();

		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(selfUrl);
		builder.setMediaType(mediaType);

		heroSingleton.getHttpHero().performRequest(builder.get(), new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				Genson genson = new Genson();
				currentLecturer = genson.deserialize(httpHeroResponse.getData(), Lecturer.class);
				de.marcelgross.httphero.Link httpHerroLink = httpHeroResponse.getMapRelationTypeToLink().get("updateLecturer");
				lectuererEditLink = new Link(httpHerroLink.getUrl(), httpHerroLink.getRelationType(), httpHerroLink.getMediaType());
				setUpLecturer(currentLecturer);
			}

			@Override
			public void onFailure() {
				Toast.makeText(EditLecturerActivity.this, R.string.load_lecturer_error, Toast.LENGTH_LONG).show();
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

	private void setUpLecturer(Lecturer lecturer) {
		title.setText(lecturer.getTitle());
		firstName.setText(lecturer.getFirstName());
		lastName.setText(lecturer.getLastName());
		email.setText(lecturer.getEmail());
		phoneNumber.setText(lecturer.getPhone());
		address .setText(lecturer.getAddress());
		room.setText(lecturer.getRoomNumber());
		welearn.setText(lecturer.getUrlWelearn());
	}

	private void validateAndSave() {
		boolean error = false;
		String titleString = title.getText().toString().trim();
		String firstNameString = firstName.getText().toString().trim();
		String lastNameString = lastName.getText().toString().trim();
		String emailString = email.getText().toString().trim();
		String phoneNumberString = phoneNumber.getText().toString().trim();
		String addressString = address.getText().toString().trim();
		String roomString = room.getText().toString().trim();
		String welearnString = welearn.getText().toString().trim();

		if (titleString.isEmpty()) {
			titleInputLayout.setError(getString(R.string.title_missing));
			error = true;
		}
		if (firstNameString.isEmpty()) {
			firstNameInputLayout.setError(getString(R.string.first_name_missing));
			error = true;
		}
		if (lastNameString.isEmpty()) {
			lastNameInputLayout.setError(getString(R.string.last_name_missing));
			error = true;
		}
		if (emailString.isEmpty()) {
			emailInputLayout.setError(getString(R.string.email_missing));
			error = true;
		}
		if (phoneNumberString.isEmpty()) {
			phoneNumberInputLayout.setError(getString(R.string.phone_number_missing));
			error = true;
		}
		if (addressString.isEmpty()) {
			addressInputLayout.setError(getString(R.string.address_missing));
			error = true;
		}
		if (roomString.isEmpty()) {
			roomInputLayout.setError(getString(R.string.room_missing));
			error = true;
		}
		if (welearnString.isEmpty()) {
			welearnInputLayout.setError(getString(R.string.welearn_missing));
			error = true;
		}

		if (!error) {
			currentLecturer.setTitle(titleString);
			currentLecturer.setFirstName(firstNameString);
			currentLecturer.setLastName(lastNameString);
			currentLecturer.setEmail(emailString);
			currentLecturer.setPhone(phoneNumberString);
			currentLecturer.setAddress(addressString);
			currentLecturer.setRoomNumber(roomString);
			currentLecturer.setUrlWelearn(welearnString);
			saveLecturer();
		}
	}

	private void saveLecturer() {
		String lecturerJson = genson.serialize(currentLecturer);
		HttpHero httpHero = HttpHeroSingleton.getInstance().getHttpHero();

		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(lectuererEditLink.getHref());
		builder.setMediaType(lectuererEditLink.getType());

		Request request = builder.put(lecturerJson.getBytes());

		httpHero.performRequest(request, new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				Toast.makeText(EditLecturerActivity.this, "updated", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure() {
				Toast.makeText(EditLecturerActivity.this, "not updated", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
