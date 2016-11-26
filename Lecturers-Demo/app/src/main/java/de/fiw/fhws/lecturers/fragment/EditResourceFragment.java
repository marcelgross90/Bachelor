package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.owlike.genson.Genson;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.util.GensonBuilder;
import de.marcelgross.lecturer_lib.customView.ResourceInputView;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Resource;


public abstract class EditResourceFragment extends Fragment {

	protected final Genson genson = new GensonBuilder().getDateFormatter();
	protected ResourceInputView inputView;
	protected Link resourceEditLink;
	private String url;
	private String mediaType;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			url = bundle.getString("url", "");
			mediaType = bundle.getString("mediaType", "");
		}

		loadResource();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(getLayout(), container, false);

		initializeView(view);

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
				saveResource();
				break;
		}

		return super.onOptionsItemSelected(item);
	}


	private void loadResource() {
		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).acceptHeader(mediaType));
		client.sendRequest(getLoadCallBack());
	}

	private void saveResource() {
		Resource resource = inputView.getResource();

		if (resource != null) {
			String resourceString = genson.serialize(resource);

			NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(resourceEditLink.getHref()).put(resourceString, resourceEditLink.getType()));
			client.sendRequest(getSaveCallBack());
		}

	}

	protected abstract int getLayout();

	protected abstract void initializeView(View view);

	protected abstract NetworkCallback getLoadCallBack();

	protected abstract NetworkCallback getSaveCallBack();

}
