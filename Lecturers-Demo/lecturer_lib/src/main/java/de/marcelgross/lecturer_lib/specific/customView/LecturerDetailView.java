package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.ProfileImageView;
import de.marcelgross.lecturer_lib.generic.customView.ResourceDetailView;
import de.marcelgross.lecturer_lib.specific.adapter.LecturerDetailAdapter;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;

public class LecturerDetailView extends ResourceDetailView {

	private ProfileImageView profileImageView;
	private RecyclerView recyclerView;

	public LecturerDetailView(Context context) {
		super(context);
	}

	public LecturerDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LecturerDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected int getLayout() {
		return R.layout.view_lecturer_detail;
	}

	@Override
	protected int[] getStyleable() {
		return R.styleable.LecturerDetailView;
	}

	@Override
	protected void initializeView() {
		profileImageView = (ProfileImageView) findViewById(R.id.ivLecturerPicture);
		recyclerView = (RecyclerView) findViewById(R.id.rvLecturerDetails);
	}

	public void setUpView(Lecturer lecturer, View.OnClickListener listener) {
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setHasFixedSize(true);

		LecturerDetailAdapter adapter = new LecturerDetailAdapter(listener);
		adapter.addLecturer(lecturer);
		if (lecturer.getProfileImageUrl() != null) {
			profileImageView.loadCutImage(lecturer.getProfileImageUrl());
		}
		recyclerView.setAdapter(adapter);
	}
}