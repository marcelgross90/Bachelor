package de.fiw.fhws.lecturers.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.customView.ProfileImageView;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.viewholder.LecturerListViewHolder;

public class LecturerListAdapter extends RecyclerView.Adapter<LecturerListViewHolder> {

	private final List<Lecturer> lecturerList = new ArrayList<>();
	private final OnLecturerClickListener onLecturerClickListener;


	public interface OnLecturerClickListener {
		void onLecturerClick(Lecturer lecturer, ProfileImageView imageView);
	}

	public LecturerListAdapter(OnLecturerClickListener lecturerClickListener) {
		this.onLecturerClickListener = lecturerClickListener;
	}

	public void addLecturer(List<Lecturer> newLecturers) {
		for (Lecturer newLecturer : newLecturers) {
			if (!this.lecturerList.contains(newLecturer)) {
				this.lecturerList.add(newLecturer);
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public LecturerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.card_lecturer, parent, false);
		return new LecturerListViewHolder(moduleCard, onLecturerClickListener);
	}

	@Override
	public void onBindViewHolder(LecturerListViewHolder holder, int position) {
		holder.assignData(lecturerList.get(position));
	}

	@Override
	public int getItemCount() {
		return lecturerList.size();
	}
}