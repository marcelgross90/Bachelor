package de.marcelgross.lecturer_lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.viewholder.LecturerDetailViewHolderContact;
import de.marcelgross.lecturer_lib.viewholder.LecturerDetailViewHolderOffice;


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
			int layout = R.layout.card_lecturer_detail_office;
			View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
			result = new LecturerDetailViewHolderOffice(v, listener);
		} else if (viewType == 1) {
			int layout = R.layout.card_lecturer_detail_contact;

			View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
			result = new LecturerDetailViewHolderContact(v, listener);
		}
		return result;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (position == 0) {
			LecturerDetailViewHolderOffice viewHolder = (LecturerDetailViewHolderOffice) holder;
			viewHolder.assignData(lecturer);
		} else if (position == 1) {
			LecturerDetailViewHolderContact viewHolder = (LecturerDetailViewHolderContact) holder;
			if (viewHolder != null)
				viewHolder.assignLecturer(lecturer);
		}
	}

	@Override
	public int getItemCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}
}