package de.marcelgross.lecturer_lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.marcelgross.lecturer_lib.model.Ressource;
import de.marcelgross.lecturer_lib.viewholder.RessourceViewHolder;


public abstract class RessourceListAdapter<T extends RessourceViewHolder> extends RecyclerView.Adapter<T> {

	private final List<Ressource> ressourceList = new ArrayList<>();

	public interface OnRessourceClickListener {
		void onResourceClickWithView(Ressource ressource, View view);
		void onResourceClick(Ressource ressource);

	}

	public void addRessource(List<Ressource> ressources) {
		for (Ressource ressource : ressources) {
			if (!this.ressourceList.contains(ressource)) {
				this.ressourceList.add(ressource);
			}
		}

		notifyDataSetChanged();
	}

	@Override
	public void onBindViewHolder(T holder, int position) {
		holder.assignData(ressourceList.get(position));
	}

	@Override
	public int getItemCount() {
		return ressourceList.size();
	}

}
