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

import de.fiw.fhws.lecturers.LecturerDetailActivity;
import de.fiw.fhws.lecturers.MainActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.adapter.LecturerListAdapter;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerListFragment extends Fragment implements LecturerListAdapter.ActivateProgressBar, LecturerListAdapter.OnLecturerClickListener {

	private RecyclerView modulesRecyclerView;
	private RecyclerView.Adapter modulesAdapter;
	private RecyclerView.LayoutManager modulesLayoutMgr;
	private ProgressBar progressBar;


	public LecturerListFragment() {
		// Required empty public constructor
	}

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
		/*//colors progressBar
		progressBar.getIndeterminateDrawable().setColorFilter(
				ContextCompat.getColor(getActivity(), R.color.colorPrimary),
				android.graphics.PorterDuff.Mode.MULTIPLY
		);*/
		modulesRecyclerView = (RecyclerView) view.findViewById(R.id.lecturer_recycler_view);
		modulesAdapter = new LecturerListAdapter(this, this, getContext(), "https://apistaging.fiw.fhws.de/mi/api/mitarbeiter/");
		modulesLayoutMgr = new LinearLayoutManager(getContext());

		modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
		modulesRecyclerView.setAdapter(modulesAdapter);

		return view;
	}

	@Override
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
		inflater.inflate(R.menu.lecturer_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addLecturer:
				MainActivity.replaceFragment(getFragmentManager(), new NewLecturerFragment());
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}
