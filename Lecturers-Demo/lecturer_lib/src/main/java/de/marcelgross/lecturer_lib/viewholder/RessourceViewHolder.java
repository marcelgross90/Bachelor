package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.model.Ressource;


public abstract class RessourceViewHolder extends RecyclerView.ViewHolder {

	public RessourceViewHolder(View itemView) {
		super(itemView);
	}

	public abstract void assignData(final Ressource ressource);
}
