package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.owlike.genson.Genson;

import java.io.IOException;

import de.fiw.fhws.lecturers.FragmentHandler;
import de.fiw.fhws.lecturers.R;
import de.marcelgross.lecturer_lib.model.Lecturer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewLecturerFragment extends Fragment {

	private final Genson genson = new Genson();
	private String url;
	private String mediaType;
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

	public NewLecturerFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		this.url = bundle.getString("url");
		this.mediaType = bundle.getString("mediaType");
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lecturer_input, container, false);

		title = (EditText) view.findViewById(R.id.title);
		firstName = (EditText) view.findViewById(R.id.firstName);
		lastName = (EditText) view.findViewById(R.id.lastName);
		email = (EditText) view.findViewById(R.id.email);
		phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
		address = (EditText) view.findViewById(R.id.address);
		room = (EditText) view.findViewById(R.id.room);
		welearn = (EditText) view.findViewById(R.id.welearn);

		titleInputLayout = (TextInputLayout) view.findViewById(R.id.titleInputLayout);
		firstNameInputLayout = (TextInputLayout) view.findViewById(R.id.firstNameInputLayout);
		lastNameInputLayout = (TextInputLayout) view.findViewById(R.id.lastNameInputLayout);
		emailInputLayout = (TextInputLayout) view.findViewById(R.id.emailInputLayout);
		phoneNumberInputLayout = (TextInputLayout) view.findViewById(R.id.phoneNumberInputLayout);
		addressInputLayout = (TextInputLayout) view.findViewById(R.id.addressInputLayout);
		roomInputLayout = (TextInputLayout) view.findViewById(R.id.roomInputLayout);
		welearnInputLayout = (TextInputLayout) view.findViewById(R.id.welearnInputLayout);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.save_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.saveLecturer:
				save();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void save() {
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
			Lecturer lecturer = new Lecturer();
			lecturer.setTitle(titleString);
			lecturer.setFirstName(firstNameString);
			lecturer.setLastName(lastNameString);
			lecturer.setEmail(emailString);
			lecturer.setPhone(phoneNumberString);
			lecturer.setAddress(addressString);
			lecturer.setRoomNumber(roomString);
			lecturer.setUrlWelearn(welearnString);
			postLecturer(lecturer);
		}
	}

	private void postLecturer(Lecturer lecturer) {
		String lecturerJson = genson.serialize(lecturer);

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(MediaType.parse(mediaType), lecturerJson);
		final Request request = new Request.Builder()
				.url(url)
				.post(body)
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
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.lecturer_saved, Toast.LENGTH_SHORT).show();
						FragmentHandler.replaceFragmentPopBackStack(getFragmentManager(), new LecturerListFragment());
					}
				});

			}
		});
	}
}