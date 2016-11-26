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
import de.marcelgross.lecturer_lib.customView.RessourceInputView;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Ressource;


public abstract class EditRessourceFragment extends Fragment {

	protected final Genson genson = new GensonBuilder().getDateFormater();
	protected RessourceInputView inputView;
	protected Link ressourceEditLink;
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

		loadRessource();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.save_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.saveItem:
				saveRessource();
				break;
		}

		return super.onOptionsItemSelected(item);
	}


	private void loadRessource() {
		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).acceptHeader(mediaType));
		client.sendRequest(getLoadCallBack());
	}

	private void saveRessource() {
		Ressource ressource = inputView.getRessource();

		if (ressource != null) {
			String ressourceString = genson.serialize(ressource);

			NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(ressourceEditLink.getHref()).put(ressourceString, ressourceEditLink.getType()));
			client.sendRequest(getSaveCallBack());
		}

	}

	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
									  Bundle savedInstanceState);

	protected abstract NetworkCallback getLoadCallBack();
	protected abstract NetworkCallback getSaveCallBack();

}
