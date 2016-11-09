package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.textView.AddressView;
import de.marcelgross.lecturer_lib.customView.textView.MailView;
import de.marcelgross.lecturer_lib.customView.textView.NameView;
import de.marcelgross.lecturer_lib.customView.textView.PhoneView;
import de.marcelgross.lecturer_lib.customView.textView.RoomView;
import de.marcelgross.lecturer_lib.customView.textView.TitleView;
import de.marcelgross.lecturer_lib.customView.textView.WelearnView;
import de.marcelgross.lecturer_lib.model.Lecturer;


public class LecturerCardView extends CardView {

	private ProfileImageView imageView;
	private TitleView titleView;
	private NameView nameView;
	private MailView mailView;
	private PhoneView phoneView;
	private RoomView roomView;
	private AddressView addressView;
	private WelearnView welearnView;

	public LecturerCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LecturerCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LecturerCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_card, null));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerCardView, defStyle, 0);
		try {

			imageView = (ProfileImageView) findViewById(R.id.profileImg);
			titleView = (TitleView) findViewById(R.id.title);
			nameView = (NameView) findViewById(R.id.name);
			mailView = (MailView) findViewById(R.id.email);
			phoneView = (PhoneView) findViewById(R.id.phone);
			roomView = (RoomView) findViewById(R.id.room);
			addressView = (AddressView) findViewById(R.id.address);
			welearnView = (WelearnView) findViewById(R.id.welearn);

		} finally {
			typedArray.recycle();
		}

	}

	public void setUpView(Lecturer lecturer) {
		hideUnnecessaryViews(lecturer);
		titleView.setText(lecturer.getTitle());
		nameView.setText(lecturer.getFirstName() + " " + lecturer.getLastName());
		mailView.setText(lecturer.getEmail());
		phoneView.setText(lecturer.getPhone());
		roomView.setText(lecturer.getRoomNumber());
		addressView.setText(lecturer.getAddress());
		welearnView.setAddress(lecturer.getUrlWelearn());
		imageView.loadImage(lecturer.getProfileImageUrl(), R.dimen.picture_width, R.dimen.picture_height);
	}

	private void hideUnnecessaryViews(Lecturer lecturer) {
		if (lecturer.getTitle().trim().isEmpty()) {
			titleView.setVisibility(View.GONE);
		} else {
			titleView.setVisibility(View.VISIBLE);
		}
		if (lecturer.getPhone().trim().isEmpty()) {
			phoneView.setVisibility(View.GONE);
		} else {
			phoneView.setVisibility(View.VISIBLE);
		}
		if (lecturer.getUrlWelearn().trim().isEmpty()) {
			welearnView.setVisibility(View.GONE);
		} else {
			welearnView.setVisibility(View.VISIBLE);
		}
		if (lecturer.getRoomNumber().trim().isEmpty()) {
			roomView.setVisibility(View.GONE);
		} else {
			roomView.setVisibility(View.VISIBLE);
		}
	}
}
