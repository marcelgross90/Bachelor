package de.fiw.fhws.lecturers.spezific.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.fragment.EditRessourceFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.marcelgross.lecturer_lib.customView.LecturerInputView;
import de.marcelgross.lecturer_lib.model.Lecturer;
import de.marcelgross.lecturer_lib.model.Link;

public class EditLecturerFragment extends EditRessourceFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_edit_lecturer, container, false);

		inputView = (LecturerInputView) view.findViewById(R.id.lecturer_input);

		return view;
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
				ressourceEditLink = linkHeader.get(getActivity().getString(R.string.rel_type_update_lecturer));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						inputView.setRessource(lecturer);
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