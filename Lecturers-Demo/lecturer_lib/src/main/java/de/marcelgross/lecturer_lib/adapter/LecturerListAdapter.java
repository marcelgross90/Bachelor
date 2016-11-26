package de.marcelgross.lecturer_lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.ProfileImageView;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.viewholder.LecturerListViewHolder;

public class LecturerListAdapter extends RessourceListAdapter<LecturerListViewHolder> {

	private final OnRessourceClickListener onRessourceClickListener;

	public LecturerListAdapter(OnRessourceClickListener onRessourceClickListener) {
		this.onRessourceClickListener = onRessourceClickListener;
	}


	@Override
	public LecturerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.card_lecturer, parent, false);
		return new LecturerListViewHolder(moduleCard, onRessourceClickListener);
	}
}