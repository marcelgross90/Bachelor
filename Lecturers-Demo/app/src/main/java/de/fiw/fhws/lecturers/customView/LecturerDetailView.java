package de.fiw.fhws.lecturers.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerDetailView extends CoordinatorLayout {

	private ProfileImageView profileImageView;

	public LecturerDetailView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LecturerDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LecturerDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.activity_lecturer_detail, null));

		/*TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailView, defStyle, 0);
		try {

			profileImageView = (ProfileImageView) findViewById(R.id.ivLecturerPicture);

		} finally {
			typedArray.recycle();
		}*/
	}

	public void setUpView(Lecturer lecturer) {
		profileImageView.loadImage(lecturer.getProfileImageUrl());
	}
}