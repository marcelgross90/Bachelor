package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.AttributeView;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;

public class LecturerDetailCardView extends CardView {

	private AttributeView roomView;
	private AttributeView addressView;
	private AttributeView mailView;
	private AttributeView phoneView;
	private AttributeView welearnView;

	public LecturerDetailCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LecturerDetailCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LecturerDetailCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_detail_card, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailCardView, defStyle, 0);
		try {

			roomView = (AttributeView) findViewById(R.id.tvRoomValue);
			addressView = (AttributeView) findViewById(R.id.tvAddressValue);
			mailView = (AttributeView) findViewById(R.id.tvEmailValue);
			phoneView = (AttributeView) findViewById(R.id.tvPhoneValue);
			welearnView = (AttributeView) findViewById(R.id.tvWebsiteValue);

		} finally {
			typedArray.recycle();
		}
	}

	public void setUpView(Lecturer lecturer) {
		hideUnnecessaryViews(lecturer);
		roomView.setText(lecturer.getRoomNumber());
		addressView.setText(lecturer.getAddress());
		mailView.setText(lecturer.getEmail());
		phoneView.setText(lecturer.getPhone());
		welearnView.setText(getResources().getText(R.string.welearn));
	}

	private void hideUnnecessaryViews(Lecturer lecturer) {
		if (lecturer.getRoomNumber().trim().isEmpty()) {
			roomView.setVisibility(View.GONE);
		} else {
			roomView.setVisibility(View.VISIBLE);
		}
		if (lecturer.getPhone().trim().isEmpty()) {
			phoneView.setVisibility(View.GONE);
		} else {
			phoneView.setVisibility(View.VISIBLE);
		}
		if (lecturer.getHomepage() == null) {
			welearnView.setVisibility(View.GONE);
		} else {
			welearnView.setVisibility(View.VISIBLE);
		}
	}
}
