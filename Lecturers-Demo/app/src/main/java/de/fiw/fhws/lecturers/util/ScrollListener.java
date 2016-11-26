package de.fiw.fhws.lecturers.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener {

	//offset when to trigger load
	private int offset = 1;

	public interface OnScrollListener {
		void load();
	}

	private LinearLayoutManager linearLayoutManager;
	private OnScrollListener listener;

	public ScrollListener(LinearLayoutManager linearLayoutManager, OnScrollListener listener) {
		this.linearLayoutManager = linearLayoutManager;
		this.listener = listener;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		int visibleItems = recyclerView.getChildCount();
		int totalItems = linearLayoutManager.getItemCount();
		int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
		if (totalItems - visibleItems <= firstVisible + offset) {
			listener.load();
		}
	}
}
