package de.marcelgross.lecturer_lib.generic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.owlike.genson.Genson;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.ResourceInputView;
import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkClient;
import de.marcelgross.lecturer_lib.generic.network.NetworkRequest;
import de.marcelgross.lecturer_lib.generic.util.GensonBuilder;

public abstract class NewResourceFragment extends Fragment {

	protected ResourceInputView inputView;
	private final Genson genson = new GensonBuilder().getDateFormatter();
	private String url;
	private String mediaType;

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
		if (item.getItemId() == R.id.saveItem) {
			saveResource();
		}

		return super.onOptionsItemSelected(item);
	}

	private void saveResource() {
		Resource resource = inputView.getResource();
		if (resource != null) {
			String lecturerJson = genson.serialize(resource);

			NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).post(lecturerJson, mediaType));
			client.sendRequest(getCallback());
		}
	}

	protected abstract int getLayout();

	protected abstract void initializeView(View view);

	protected abstract NetworkCallback getCallback();
}
