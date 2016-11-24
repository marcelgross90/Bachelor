package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerDetailContactCardView extends CardView {

	private AttributeView mailView;
	private AttributeView phoneView;
	private AttributeView welearnView;

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
		this.addView(inflater.inflate(R.layout.view_lecturer_detail_contact_card, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailContactCardView, defStyle, 0);
		try {

			mailView = (AttributeView) findViewById(R.id.tvEmailValue);
			phoneView = (AttributeView) findViewById(R.id.tvPhoneValue);
			welearnView = (AttributeView) findViewById(R.id.tvWebsiteValue);

		} finally {
			typedArray.recycle();
		}
	}

	public void setUpView(Lecturer lecturer) {
		hideUnnecessaryViews(lecturer);
		mailView.setText(lecturer.getEmail());
		phoneView.setText(lecturer.getPhone());
		welearnView.setText(getResources().getText(R.string.welearn));
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