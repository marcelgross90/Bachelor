package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.owlike.genson.Genson;

import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.fiw.fhws.lecturers.R;
import de.marcelgross.lecturer_lib.customView.LecturerInputView;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class NewLecturerFragment extends Fragment {

	private final Genson genson = new Genson();
	private String url;
	private String mediaType;
	private LecturerInputView lecturerInputView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		this.url = bundle.getString("url");
		this.mediaType = bundle.getString("mediaType");
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lecturer_input, container, false);

		lecturerInputView = (LecturerInputView) view.findViewById(R.id.lecturer_input);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.save_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.saveItem:
				postLecturer();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void postLecturer() {
		Lecturer lecturer = lecturerInputView.getLecturer();
		if (lecturer != null) {
			String lecturerJson = genson.serialize(lecturer);

			NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).post(lecturerJson, mediaType));
			client.sendRequest(new NetworkCallback() {
				@Override
				public void onFailure() {

				}

				@Override
				public void onSuccess(NetworkResponse response) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getActivity(), R.string.lecturer_saved, Toast.LENGTH_SHORT).show();
							FragmentHandler.replaceFragmentPopBackStack(getFragmentManager(), new LecturerListFragment());
						}
					});
				}
			});
		}
	}
}