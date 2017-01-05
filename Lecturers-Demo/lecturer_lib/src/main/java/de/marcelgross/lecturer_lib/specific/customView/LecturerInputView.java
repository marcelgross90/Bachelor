package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.util.AttributeSet;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.AttributeInput;
import de.marcelgross.lecturer_lib.generic.customView.ResourceInputView;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;
import de.marcelgross.lecturer_lib.generic.model.Resource;

public class LecturerInputView extends ResourceInputView {

	private AttributeInput titleInput;
	private AttributeInput firstNameInput;
	private AttributeInput lastNameInput;
	private AttributeInput mailInput;
	private AttributeInput phoneInput;
	private AttributeInput roomInput;
	private AttributeInput addressInput;
	private AttributeInput welearnInput;

	public LecturerInputView(Context context) {
		super(context);
	}

	public LecturerInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LecturerInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void setResource(Resource resource) {
		Lecturer lecturer = (Lecturer) resource;
		titleInput.setText(lecturer.getTitle());
		firstNameInput.setText(lecturer.getFirstName());
		lastNameInput.setText(lecturer.getLastName());
		mailInput.setText(lecturer.getEmail());
		phoneInput.setText(lecturer.getPhone());
		roomInput.setText(lecturer.getRoomNumber());
		addressInput.setText(lecturer.getAddress());
		welearnInput.setText(lecturer.getHomepage().getHref());
	}

	@Override
	public Resource getResource() {
		boolean error = false;
		String titleString = titleInput.getText();
		String firstNameString = firstNameInput.getText();
		String lastNameString = lastNameInput.getText();
		String emailString = mailInput.getText();
		String phoneNumberString = phoneInput.getText();
		String addressString = addressInput.getText();
		String roomString = roomInput.getText();
		String homepageString = welearnInput.getText();

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
		if (homepageString.isEmpty()) {
			welearnInput.setError(context.getString(R.string.welearn_missing));
			error = true;
		}

		if (!error) {
			Lecturer currentLecturer = new Lecturer();
			currentLecturer.setTitle(titleString);
			currentLecturer.setFirstName(firstNameString);
			currentLecturer.setLastName(lastNameString);
			currentLecturer.setEmail(emailString);
			currentLecturer.setPhone(phoneNumberString);
			currentLecturer.setAddress(addressString);
			currentLecturer.setRoomNumber(roomString);
			Link homepage = new Link(homepageString, "homepage", "text/html");
			currentLecturer.setHomepage(homepage);
			return currentLecturer;
		}
		return null;
	}

	@Override
	protected void initializeViews() {
		titleInput = (AttributeInput) findViewById(R.id.title);
		firstNameInput = (AttributeInput) findViewById(R.id.firstName);
		lastNameInput = (AttributeInput) findViewById(R.id.lastName);
		mailInput = (AttributeInput) findViewById(R.id.mail);
		phoneInput = (AttributeInput) findViewById(R.id.phone);
		roomInput = (AttributeInput) findViewById(R.id.room);
		addressInput = (AttributeInput) findViewById(R.id.address);
		welearnInput = (AttributeInput) findViewById(R.id.welearn);
	}

	@Override
	protected int getLayout() {
		return R.layout.view_lecturer_input;
	}

	@Override
	protected int[] getStyleable() {
		return R.styleable.LecturerInputView;
	}
}