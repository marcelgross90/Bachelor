package de.fiw.fhws.lecturers.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.FragmentHandler;
import de.fiw.fhws.lecturers.LecturerDetailActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.adapter.LecturerListAdapter;
import de.fiw.fhws.lecturers.customView.ProfileImageView;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.model.Link;
import de.fiw.fhws.lecturers.network.util.HeaderParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LecturerListFragment extends Fragment implements LecturerListAdapter.OnLecturerClickListener {

	private final Genson genson = new Genson();
	private final String baseUrl = "https://apistaging.fiw.fhws.de/mig/api/lecturers/";
	private LecturerListAdapter modulesAdapter;
	private LinearLayoutManager modulesLayoutMgr;
	private ProgressBar progressBar;
	private String nextUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lecturer_list, container, false);

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		RecyclerView modulesRecyclerView = (RecyclerView) view.findViewById(R.id.lecturer_recycler_view);
		modulesAdapter = new LecturerListAdapter(this);
		modulesLayoutMgr = new LinearLayoutManager(getContext());

		modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
		modulesRecyclerView.setAdapter(modulesAdapter);
		modulesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int visibleItems = recyclerView.getChildCount();
				int totalItems = modulesLayoutMgr.getItemCount();
				int firstVisible = modulesLayoutMgr.findFirstVisibleItemPosition();
				if (totalItems - visibleItems <= firstVisible + 1
						&& nextUrl != null && !nextUrl.isEmpty()) {
					loadLecturers(nextUrl);
				}
			}
		});

		loadLecturers(baseUrl);
		return view;
	}

	private void showProgressBar(final boolean show) {
		if (isAdded()) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
				}
			});
		}
	}

	@Override
	public void onLecturerClick(Lecturer lecturer, ProfileImageView view) {
		Intent intent = new Intent(getActivity(), LecturerDetailActivity.class);
		intent.putExtra("selfUrl", lecturer.getSelf().getHref());
		intent.putExtra("mediaType", lecturer.getSelf().getType());
		intent.putExtra("fullName", lecturer.getFirstName() + " " + lecturer.getLastName());

		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ActivityOptions options = ActivityOptions
					.makeSceneTransitionAnimation(getActivity(), view, "pic");
			getActivity().startActivity(intent, options.toBundle());
		} else {
			getActivity().startActivity(intent);
			getActivity().overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.lecturer_list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addLecturer:
				Bundle bundle = new Bundle();
				bundle.putString("url", baseUrl);
				bundle.putString("mediaType", "application/vnd.fhws-lecturer.default+json");
				Fragment fragment = new NewLecturerFragment();
				fragment.setArguments(bundle);
				FragmentHandler.replaceFragmentPopBackStack(getFragmentManager(), fragment);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void loadLecturers(String url) {
		showProgressBar(true);

		Request request = new Request.Builder()
				.header("Accept", "application/vnd.fhws-lecturer.default+json")
				.url(url)
				.build();

		OkHttpClient client = new OkHttpClient();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				}

				final List<Lecturer> lecturers = genson.deserialize(response.body().charStream(), new GenericType<List<Lecturer>>() {
				});
				Map<String, List<String>> headers = response.headers().toMultimap();
				Map<String, Link> linkHeader = HeaderParser.getLinks(headers.get("link"));
				Link nextLink = linkHeader.get("next");
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showProgressBar(false);
						modulesAdapter.addLecturer(lecturers);
					}
				});
			}
		});
	}
}
