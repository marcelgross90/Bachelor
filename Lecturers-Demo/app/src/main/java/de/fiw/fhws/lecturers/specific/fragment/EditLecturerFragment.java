package de.fiw.fhws.lecturers.specific.fragment;

import android.view.View;
import android.widget.Toast;

import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.fragment.EditResourceFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.specific.customView.LecturerInputView;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;

public class EditLecturerFragment extends EditResourceFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_edit_lecturer;
	}

	@Override
	protected void initializeView(View view) {
		inputView = (LecturerInputView) view.findViewById(R.id.lecturer_input);
	}

	@Override
	protected NetworkCallback getLoadCallBack() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final Lecturer lecturer = genson.deserialize(response.getResponseReader(), Lecturer.class);
				Map<String, Link> linkHeader = response.getLinkHeader();
				resourceEditLink = linkHeader.get(getActivity().getString(R.string.rel_type_update_lecturer));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						inputView.setResource(lecturer);
					}
				});
			}
		};
	}

	@Override
	protected NetworkCallback getSaveCallBack() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.lecturer_updated, Toast.LENGTH_SHORT).show();
						getActivity().onBackPressed();
					}
				});
			}
		};
	}
}