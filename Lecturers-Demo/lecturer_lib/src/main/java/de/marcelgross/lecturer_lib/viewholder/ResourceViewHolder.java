package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.model.Resource;


public abstract class ResourceViewHolder extends RecyclerView.ViewHolder {

	public ResourceViewHolder(View itemView) {
		super(itemView);
	}

	public abstract void assignData(final Resource resource);
}
