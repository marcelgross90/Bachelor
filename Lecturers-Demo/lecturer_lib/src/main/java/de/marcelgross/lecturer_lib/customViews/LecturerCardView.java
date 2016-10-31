package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerCardView extends CardView {

	private ImageView profileImageView;
	private TitleView titleView;
	private NameView nameView;
	private MailView mailView;
	private PhoneView phoneView;
	private AddressView addressView;
	private RoomView roomView;
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
		this.addView(inflater.inflate(R.layout.lecturer_card_view, null));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerCardView, defStyle, 0);
		try {
			profileImageView = (ImageView) findViewById(R.id.profileImg);
			titleView = (TitleView) findViewById(R.id.title);
			nameView = (NameView) findViewById(R.id.name);
			mailView = (MailView) findViewById(R.id.email);
			phoneView = (PhoneView) findViewById(R.id.phone);
			addressView = (AddressView) findViewById(R.id.address);
			roomView = (RoomView) findViewById(R.id.room);
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
		addressView.setText(lecturer.getAddress());
		roomView.setText(lecturer.getRoomNumber());
		welearnView.setAddress(lecturer.getUrlWelearn());
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
