package de.fiw.fhws.lecturers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.network.CollectionControlHeaderRequest;
import de.fiw.fhws.lecturers.network.CollectionRequest;
import de.fiw.fhws.lecturers.network.LinkParser;
import de.fiw.fhws.lecturers.viewholder.LecturerListViewHolder;


public class LecturerListAdapter extends RecyclerView.Adapter<LecturerListViewHolder> {

	private List<Lecturer> lecturerList;
	private Context context;
	private final ActivateProgressBar activateProgressBar;
	private final OnLecturerClickListener onLecturerClickListener;
	private String selfUrl;

	public interface ActivateProgressBar {
		void showProgressBar(boolean show);
	}

	public interface OnLecturerClickListener {
		void onLecturerClick(Lecturer lecturer, ImageView imageView);
	}

	public LecturerListAdapter(ActivateProgressBar activateProgressBar, OnLecturerClickListener lecturerClickListener, Context context, String url) {
		this.activateProgressBar = activateProgressBar;
		this.onLecturerClickListener = lecturerClickListener;
		this.context = context;
		this.lecturerList = new ArrayList<>();
		this.selfUrl = url;
		loadLecturers(url);
	}

	@Override
	public LecturerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.card_lecturer, parent, false);
		return new LecturerListViewHolder(moduleCard, context, onLecturerClickListener);
	}

	@Override
	public void onBindViewHolder(LecturerListViewHolder holder, int position) {
		// -2 that the data is loaded before you reach end of list
		if (position == getItemCount()-2) {
			loadControlHeader();
		}
		holder.assignData(lecturerList.get(position));
	}

	@Override
	public int getItemCount() {
		return lecturerList.size();
	}

	private void loadControlHeader() {
		CollectionControlHeaderRequest controlHeaderRequest = new CollectionControlHeaderRequest(selfUrl, context, new CollectionControlHeaderRequest.CollectionControlHeaderRequestListener() {
			@Override
			public void onResponse(int numberOfResults, int totalNumberOfResults, String selfUrl) {
				if (getItemCount() < totalNumberOfResults) {
					int newSize;
					if (totalNumberOfResults - getItemCount() >= 10) {
						newSize = 10;
					} else {
						newSize = totalNumberOfResults - getItemCount();
					}
					String newUrl = LinkParser.gerateNewUrl(selfUrl, newSize, getItemCount());
					loadLecturers(newUrl);
				}

			}

			@Override
			public void onError(VolleyError error) {
				//todo display error
			}
		});
		controlHeaderRequest.sendRequest();
	}

	private void loadLecturers(String url) {
		activateProgressBar.showProgressBar(true);
		CollectionRequest collectionRequest = new CollectionRequest(url, context, new CollectionRequest.CollectionRequestListener() {
			@Override
			public void onResponse(List<Lecturer> lecturer) {
				activateProgressBar.showProgressBar(false);
				lecturerList.addAll(lecturer);
				notifyDataSetChanged();
			}

			@Override
			public void onError(VolleyError error) {
				//todo display error
				android.util.Log.d("mgr", error.getMessage());
			}
		});

		collectionRequest.sendRequest();
	}
}
