package de.fiw.fhws.lecturers.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerDetailContactCardView extends CardView {

	private MailView mailView;
	private PhoneView phoneView;
	private WelearnView welearnView;

	public LecturerDetailContactCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LecturerDetailContactCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LecturerDetailContactCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_detail_contact_card, null));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailContactCardView, defStyle, 0);
		try {

			mailView = (MailView) findViewById(R.id.tvEmailValue);
			phoneView = (PhoneView) findViewById(R.id.tvPhoneValue);
			welearnView = (WelearnView) findViewById(R.id.tvWebsiteValue);

		} finally {
			typedArray.recycle();
		}
	}

	public void setUpView(Lecturer lecturer) {
		hideUnnecessaryViews(lecturer);
		mailView.setText(lecturer.getEmail());
		phoneView.setText(lecturer.getPhone());
		welearnView.setAddress(lecturer.getUrlWelearn());
	}

	private void hideUnnecessaryViews(Lecturer lecturer) {
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
	}
}