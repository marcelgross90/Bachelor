package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.textInputLayout.AddressInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.FirstNameInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.LastNameInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.MailInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.PhoneInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.RoomInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.TitleInput;
import de.marcelgross.lecturer_lib.customView.textInputLayout.WelearnInput;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerInputView extends ScrollView {

	private final Context context;
	private final Lecturer currentLecturer;
	private TitleInput titleInput;
	private FirstNameInput firstNameInput;
	private LastNameInput lastNameInput;
	private MailInput mailInput;
	private PhoneInput phoneInput;
	private RoomInput roomInput;
	private AddressInput addressInput;
	private WelearnInput welearnInput;

	public LecturerInputView(Context context) {
		super(context);
		this.context = context;
		this.currentLecturer = new Lecturer();
		init(context, null, 0);
	}

	public LecturerInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.currentLecturer = new Lecturer();

		init(context, attrs, 0);
	}

	public LecturerInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		this.currentLecturer = new Lecturer();
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_input, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerInputView, defStyle, 0);
		try {
			titleInput = (TitleInput) findViewById(R.id.title);
			firstNameInput = (FirstNameInput) findViewById(R.id.firstName);
			lastNameInput = (LastNameInput) findViewById(R.id.lastName);
			mailInput = (MailInput) findViewById(R.id.mail);
			phoneInput = (PhoneInput) findViewById(R.id.phone);
			roomInput = (RoomInput) findViewById(R.id.room);
			addressInput = (AddressInput) findViewById(R.id.address);
			welearnInput = (WelearnInput) findViewById(R.id.welearn);
 		} finally {
			typedArray.recycle();
		}
	}

	public void setLecturer(Lecturer lecturer) {
		titleInput.setText(lecturer.getTitle());
		firstNameInput.setText(lecturer.getFirstName());
		lastNameInput.setText(lecturer.getLastName());
		mailInput.setText(lecturer.getEmail());
		phoneInput.setText(lecturer.getPhone());
		roomInput.setText(lecturer.getRoomNumber());
		addressInput.setText(lecturer.getAddress());
		welearnInput.setText(lecturer.getUrlWelearn());
	}

	public Lecturer getLecturer() {
		boolean error = false;
		String titleString = titleInput.getText();
		String firstNameString = firstNameInput.getText();
		String lastNameString = lastNameInput.getText();
		String emailString = mailInput.getText();
		String phoneNumberString = phoneInput.getText();
		String addressString = addressInput.getText();
		String roomString = roomInput.getText();
		String welearnString = welearnInput.getText();

		if (titleString.isEmpty()) {
			titleInput.setError(context.getString(R.string.title_missing));
			error = true;
		}
		if (firstNameString.isEmpty()) {
			firstNameInput.setError(context.getString(R.string.first_name_missing));
			error = true;
		}
		if (lastNameString.isEmpty()) {
			lastNameInput.setError(context.getString(R.string.last_name_missing));
			error = true;
		}
		if (emailString.isEmpty()) {
			mailInput.setError(context.getString(R.string.email_missing));
			error = true;
		}
		if (phoneNumberString.isEmpty()) {
			phoneInput.setError(context.getString(R.string.phone_number_missing));
			error = true;
		}
		if (addressString.isEmpty()) {
			addressInput.setError(context.getString(R.string.address_missing));
			error = true;
		}
		if (roomString.isEmpty()) {
			roomInput.setError(context.getString(R.string.room_missing));
			error = true;
		}
		if (welearnString.isEmpty()) {
			welearnInput.setError(context.getString(R.string.welearn_missing));
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
			return  currentLecturer;
		}
		return null;
	}
}
