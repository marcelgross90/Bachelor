package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Lecturer;

public class NewLecturerFragment extends Fragment {

	private View view;
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
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_lecturer_input, container, false);

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
//				MainActivity.replaceFragment(getFragmentManager(), new NewLecturerFragment());
				save();
				Snackbar.make(view, R.string.save,  Snackbar.LENGTH_LONG).show();
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
		}
	}
}