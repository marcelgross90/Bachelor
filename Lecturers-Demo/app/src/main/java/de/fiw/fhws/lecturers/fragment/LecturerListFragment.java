package de.fiw.fhws.lecturers.fragment;


import android.app.Activity;
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
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.fiw.fhws.lecturers.LecturerDetailActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.util.ScrollListener;
import de.marcelgross.lecturer_lib.adapter.LecturerListAdapter;
import de.marcelgross.lecturer_lib.customView.ProfileImageView;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;

public class LecturerListFragment extends Fragment implements LecturerListAdapter.OnLecturerClickListener {

	private final Genson genson = new Genson();
	private Link allLecturersLink;
	private LecturerListAdapter modulesAdapter;
	private ProgressBar progressBar;
	private String nextUrl;
	private Link createNewLecturerLink;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		modulesAdapter = new LecturerListAdapter(this);
		initialNetworkRequest();
	}

	private void initialNetworkRequest() {
		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(getResources().getString(R.string.entry_url)));
		client.sendRequest(new NetworkCallback() {
			@Override
			public void onFailure() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.load_lecturer_error, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				Activity activity = getActivity();

				Map<String, Link> linkHeader = response.getLinkHeader();

				if (linkHeader.size() > 0) {
					allLecturersLink = linkHeader.get(activity.getResources().getString(R.string.rel_type_get_all_lecturers));
					loadLecturers(allLecturersLink.getHrefWithoutQueryParams());
				} else {
					allLecturersLink = null;
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lecturer_list, container, false);

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		RecyclerView modulesRecyclerView = (RecyclerView) view.findViewById(R.id.lecturer_recycler_view);
		LinearLayoutManager modulesLayoutMgr = new LinearLayoutManager(getContext());

		modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
		modulesRecyclerView.setAdapter(modulesAdapter);
		modulesRecyclerView.addOnScrollListener(new ScrollListener(modulesLayoutMgr, new ScrollListener.OnScrollListener() {
			@Override
			public void load() {
				if (nextUrl != null && !nextUrl.isEmpty()) {
					loadLecturers(nextUrl);
				}
			}
		}));
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
				if (createNewLecturerLink != null) {
					bundle.putString("url", createNewLecturerLink.getHref());
					bundle.putString("mediaType", createNewLecturerLink.getType());
				}
				Fragment fragment = new NewLecturerFragment();
				fragment.setArguments(bundle);
				FragmentHandler.replaceFragment(getFragmentManager(), fragment);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void loadLecturers(String url) {
		showProgressBar(true);

		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).acceptHeader(allLecturersLink.getType()));
		client.sendRequest(new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final List<Lecturer> lecturers = genson.deserialize(response.getResponseReader(), new GenericType<List<Lecturer>>() {});
				Map<String, Link> linkHeader = response.getLinkHeader();

				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				createNewLecturerLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_new_lecturer));
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setHasOptionsMenu(createNewLecturerLink != null);
						showProgressBar(false);
						modulesAdapter.addLecturer(lecturers);
					}
				});
			}
		});
	}
}
