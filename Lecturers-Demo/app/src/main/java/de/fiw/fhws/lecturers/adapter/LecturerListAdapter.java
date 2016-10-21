package de.fiw.fhws.lecturers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.ArrayList;
import java.util.List;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.network.HttpHeroSingleton;
import de.fiw.fhws.lecturers.network.util.LinkParser;
import de.fiw.fhws.lecturers.viewholder.LecturerListViewHolder;
import de.marcelgross.httphero.HttpHeroResponse;
import de.marcelgross.httphero.HttpHeroResultListener;
import de.marcelgross.httphero.request.Request;

public class LecturerListAdapter extends RecyclerView.Adapter<LecturerListViewHolder> {

	private Genson genson;
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
		this.genson = new Genson();
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
		if (position == getItemCount() - 2) {
		//	loadControlHeader();
		}
		holder.assignData(lecturerList.get(position));
	}

	@Override
	public int getItemCount() {
		return lecturerList.size();
	}

	private void loadControlHeader() {
		HttpHeroSingleton heroSingleton = HttpHeroSingleton.getInstance();
		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(selfUrl).setMediaType("application/json");

		heroSingleton.getHttpHero().performRequest(builder.get(), new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				//loadLecturers();
			}

			@Override
			public void onFailure() {

			}
		});
		/*CollectionControlHeaderRequest controlHeaderRequest = new CollectionControlHeaderRequest(selfUrl, context, new CollectionControlHeaderRequest.CollectionControlHeaderRequestListener() {
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
		controlHeaderRequest.sendRequest();*/
	}

	private void loadLecturers(String url) {
		activateProgressBar.showProgressBar(true);

		HttpHeroSingleton heroSingleton = HttpHeroSingleton.getInstance();
		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(url).setMediaType("application/json");

		heroSingleton.getHttpHero().performRequest(builder.get(), new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				activateProgressBar.showProgressBar(false);
				android.util.Log.d("mgr", "was here");
				List<Lecturer> lecturerList = genson.deserialize(httpHeroResponse.getData(), new GenericType<List<Lecturer>>() {});
				lecturerList.addAll(lecturerList);
				notifyDataSetChanged();
			}

			@Override
			public void onFailure() {
				android.util.Log.d("mgr", "doof");
			}
		});
	}
}