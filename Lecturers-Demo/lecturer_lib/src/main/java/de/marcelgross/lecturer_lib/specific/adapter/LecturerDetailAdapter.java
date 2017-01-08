package de.marcelgross.lecturer_lib.specific.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;
import de.marcelgross.lecturer_lib.specific.viewholder.LecturerDetailViewHolder;


public class LecturerDetailAdapter extends RecyclerView.Adapter {
	private Lecturer lecturer;
	private final View.OnClickListener listener;

	public LecturerDetailAdapter(View.OnClickListener listener) {
		this.listener = listener;
	}

	public void addLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder result = null;

		if (viewType == 0) {
			int layout = R.layout.card_lecturer_detail;
			View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
			result = new LecturerDetailViewHolder(v, listener);
		}
		return result;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (position == 0) {
			LecturerDetailViewHolder viewHolder = (LecturerDetailViewHolder) holder;
			viewHolder.assignData(lecturer);
		}
	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}
}