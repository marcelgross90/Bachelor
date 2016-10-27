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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.List;

import de.fiw.fhws.lecturers.FragmentHandler;
import de.fiw.fhws.lecturers.LecturerDetailActivity;
import de.fiw.fhws.lecturers.MainActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.adapter.LecturerListAdapter;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.network.HttpHeroSingleton;
import de.marcelgross.httphero.HttpHeroResponse;
import de.marcelgross.httphero.HttpHeroResultListener;
import de.marcelgross.httphero.Link;
import de.marcelgross.httphero.request.Request;

public class LecturerListFragment extends Fragment implements LecturerListAdapter.OnLecturerClickListener {

	private final Genson genson = new Genson();
	private RecyclerView modulesRecyclerView;
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

		modulesRecyclerView = (RecyclerView) view.findViewById(R.id.lecturer_recycler_view);
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
				if (totalItems - visibleItems <= firstVisible + 4
						&& nextUrl != null && !nextUrl.isEmpty()) {
					loadLecturers(nextUrl);
				}
			}
		});

		loadLecturers("https://apistaging.fiw.fhws.de/mig/api/lecturers/");
		return view;
	}

	public void showProgressBar(final boolean show) {
		if (isAdded()) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
				}
			});
		}
	}

	@Override
	public void onLecturerClick(Lecturer lecturer, ImageView view) {
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
				FragmentHandler.replaceFragment(getFragmentManager(), new NewLecturerFragment());
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void loadLecturers(String url) {
		showProgressBar(true);
		HttpHeroSingleton heroSingleton = HttpHeroSingleton.getInstance();
		Request.Builder builder = new Request.Builder();
		builder.setUriTemplate(url).setMediaType("application/vnd.fhws-lecturer.default+json");

		heroSingleton.getHttpHero().performRequest(builder.get(), new HttpHeroResultListener() {
			@Override
			public void onSuccess(HttpHeroResponse httpHeroResponse) {
				showProgressBar(false);
				Link nextLink = httpHeroResponse.getMapRelationTypeToLink().get("next");
				if (nextLink != null) {
					nextUrl = nextLink.getUrl();
				} else {
					nextUrl = "";
				}
				modulesAdapter.addLecturer(
						genson.deserialize(
								httpHeroResponse.getData(), new GenericType<List<Lecturer>>() {})
				);
			}

			@Override
			public void onFailure() {
				android.util.Log.d("mgr", "doof");
			}
		});
	}
}
