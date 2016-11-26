package de.fiw.fhws.lecturers.spezific.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

import com.owlike.genson.GenericType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.fragment.RessourceListFragment;
import de.fiw.fhws.lecturers.spezific.LecturerDetailActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.adapter.LecturerListAdapter;
import de.marcelgross.lecturer_lib.adapter.RessourceListAdapter;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Ressource;

public class LecturerListFragment extends RessourceListFragment {

	private LecturerListAdapter lecturerListAdapter;

	@Override
	public void onResourceClickWithView(Ressource ressource, View view) {
		Lecturer lecturer = (Lecturer) ressource;
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
	public void onResourceClick(Ressource ressource) {
		//not needed here
	}

	@Override
	protected NetworkCallback getCallBack() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final List<Lecturer> lecturers = genson.deserialize(response.getResponseReader(), new GenericType<List<Lecturer>>() {
				});
				final List<Ressource> ressources = new ArrayList<>();
				for (Lecturer lecturer : lecturers) {
					ressources.add(lecturer);
				}
				Map<String, Link> linkHeader = response.getLinkHeader();

				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				createNewRessourceLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_new_lecturer));
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setHasOptionsMenu(createNewRessourceLink != null);
						showProgressBar(false);
						getAdapter().addRessource(ressources);
					}
				});
			}
		};
	}

	@Override
	protected RessourceListAdapter getAdapter() {
		if (lecturerListAdapter == null) {
			lecturerListAdapter = new LecturerListAdapter(LecturerListFragment.this);
		}
		return lecturerListAdapter;
	}

	@Override
	protected Fragment getFragment() {
		return new NewLecturerFragment();
	}
}