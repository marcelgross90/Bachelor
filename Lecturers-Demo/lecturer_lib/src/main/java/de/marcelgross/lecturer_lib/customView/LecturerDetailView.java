package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.LecturerDetailAdapter;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerDetailView extends LinearLayout {

	private final Context context;
	private ProfileImageView profileImageView;
	private RecyclerView recyclerView;

	public LecturerDetailView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public LecturerDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public LecturerDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_lecturer_detail, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LecturerDetailView, defStyle, 0);
		try {

			profileImageView = (ProfileImageView) findViewById(R.id.ivLecturerPicture);
			recyclerView = (RecyclerView) findViewById(R.id.rvLecturerDetails);

		} finally {
			typedArray.recycle();
		}
	}

	public void setUpView(Lecturer lecturer, View.OnClickListener listener) {
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setHasFixedSize(true);

		LecturerDetailAdapter adapter = new LecturerDetailAdapter(listener);
		adapter.addLecturer(lecturer);
		if (lecturer.getProfileImageUrl() != null) {
			profileImageView.loadCuttedImage(lecturer.getProfileImageUrl());
		}
		recyclerView.setAdapter(adapter);
	}
}