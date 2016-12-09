package de.marcelgross.lecturer_lib.specific.adapter;

import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.specific.viewholder.LecturerListViewHolder;

public class LecturerListAdapter extends ResourceListAdapter<LecturerListViewHolder> {


	public LecturerListAdapter(OnResourceClickListener onResourceClickListener) {
		super(onResourceClickListener);
	}

	@Override
	protected LecturerListViewHolder getViewHolder(View moduleCard, OnResourceClickListener onResourceClickListener) {
		return new LecturerListViewHolder(moduleCard, onResourceClickListener);
	}

	@Override
	protected int getLayout() {
		return R.layout.card_lecturer;
	}
}