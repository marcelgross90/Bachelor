package de.fiw.fhws.lecturers.specific.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

import com.owlike.genson.GenericType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.fragment.ResourceListFragment;
import de.fiw.fhws.lecturers.specific.LecturerDetailActivity;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.specific.adapter.LecturerListAdapter;
import de.marcelgross.lecturer_lib.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Resource;

public class LecturerListFragment extends ResourceListFragment {

	private LecturerListAdapter lecturerListAdapter;

	@Override
	public void onResourceClickWithView(Resource resource, View view) {
		Lecturer lecturer = (Lecturer) resource;
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
	public void onResourceClick(Resource resource) {
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
				final List<Resource> resources = new ArrayList<>();
				for (Lecturer lecturer : lecturers) {
					resources.add(lecturer);
				}
				Map<String, Link> linkHeader = response.getLinkHeader();

				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				createNewResourceLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_new_lecturer));
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setHasOptionsMenu(createNewResourceLink != null);
						showProgressBar(false);
						getAdapter().addResource(resources);
					}
				});
			}
		};
	}

	@Override
	protected ResourceListAdapter getAdapter() {
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