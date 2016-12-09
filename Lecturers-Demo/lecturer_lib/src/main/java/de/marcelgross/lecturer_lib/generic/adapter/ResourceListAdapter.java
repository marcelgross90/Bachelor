package de.marcelgross.lecturer_lib.generic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.generic.viewholder.ResourceViewHolder;


public abstract class ResourceListAdapter<T extends ResourceViewHolder> extends RecyclerView.Adapter<T> {

	private final OnResourceClickListener onResourceClickListener;
	private final List<Resource> resourceList = new ArrayList<>();

	public ResourceListAdapter(OnResourceClickListener onResourceClickListener) {
		this.onResourceClickListener = onResourceClickListener;
	}

	public interface OnResourceClickListener {
		void onResourceClickWithView(Resource resource, View view);
		void onResourceClick(Resource resource);

	}

	public void addResource(List<Resource> resources) {
		for (Resource resource : resources) {
			if (!this.resourceList.contains(resource)) {
				this.resourceList.add(resource);
			}
		}

		notifyDataSetChanged();
	}

	@Override
	public T onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(getLayout(), parent, false);
		return getViewHolder(moduleCard, onResourceClickListener);
	}

	@Override
	public void onBindViewHolder(T holder, int position) {
		holder.assignData(resourceList.get(position));
	}

	@Override
	public int getItemCount() {
		return resourceList.size();
	}

	protected abstract T getViewHolder(View moduleCard, OnResourceClickListener onResourceClickListener);
	protected abstract int getLayout();
}
