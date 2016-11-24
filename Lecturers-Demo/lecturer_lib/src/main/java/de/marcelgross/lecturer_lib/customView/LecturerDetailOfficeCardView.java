package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerDetailOfficeCardView extends CardView {

	private AttributeView roomView;
	private AttributeView addressView;

	public LecturerDetailOfficeCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LecturerDetailOfficeCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LecturerDetailOfficeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_detail_office_card, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailOfficeCardView, defStyle, 0);
		try {

			roomView = (AttributeView) findViewById(R.id.tvRoomValue);
			addressView = (AttributeView) findViewById(R.id.tvAddressValue);

		} finally {
			typedArray.recycle();
		}
	}

	public void setUpView(Lecturer lecturer) {
		hideUnnecessaryViews(lecturer);
		roomView.setText(lecturer.getRoomNumber());
		addressView.setText(lecturer.getAddress());
	}

	private void hideUnnecessaryViews(Lecturer lecturer) {
		if (lecturer.getRoomNumber().trim().isEmpty()) {
			roomView.setVisibility(View.GONE);
		} else {
			roomView.setVisibility(View.VISIBLE);
		}
	}
}
