package de.marcelgross.lecturer_lib.generic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.owlike.genson.Genson;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.ResourceDetailView;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkClient;
import de.marcelgross.lecturer_lib.generic.network.NetworkRequest;
import de.marcelgross.lecturer_lib.generic.util.FragmentHandler;
import de.marcelgross.lecturer_lib.generic.util.GensonBuilder;


public abstract class DetailResourceFragment extends Fragment implements DeleteDialogFragment.DeleteDialogListener {

	protected abstract int getResourceDeleteError();
	protected abstract int getLayout();
	protected abstract void initializeView(View view);
	protected abstract Fragment getEditFragment();
	protected abstract Bundle prepareDeleteBundle();
	protected abstract NetworkCallback getCallback();

	private String resourceUrl;
	private String mediaType;
	protected final Genson genson = new GensonBuilder().getDateFormatter();
	protected Resource currentResource;
	protected ResourceDetailView resourceDetailView;
	protected Link deleteLink;
	protected Link updateLink;

	@Override
	public void onDialogClosed(final boolean successfullyDeleted) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (successfullyDeleted) {
					getFragmentManager().popBackStackImmediate();
				} else {
					Toast.makeText(getActivity(), getResourceDeleteError(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			resourceUrl = bundle.getString("url", "");
			mediaType = bundle.getString("mediaType", "");
		} else {
			resourceUrl = savedInstanceState.getString("url", "");
			mediaType = savedInstanceState.getString("mediaType", "");
		}

		loadResource();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("url", resourceUrl);
		outState.putString("mediaType", mediaType);
		super.onSaveInstanceState(outState);

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
		inflater.inflate(R.menu.detail_menu, menu);
		MenuItem deleteItem = menu.findItem(R.id.delete_item);
		MenuItem editItem = menu.findItem(R.id.edit_item);

		deleteItem.setVisible(deleteLink != null);
		editItem.setVisible(updateLink != null);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int i = item.getItemId();
		if (i == R.id.edit_item) {
			Bundle editBundle = new Bundle();
			editBundle.putString("url", updateLink.getHref());
			editBundle.putString("mediaType", updateLink.getType());
			Fragment fragment = getEditFragment();
			fragment.setArguments(editBundle);
			FragmentHandler.replaceFragmentPopBackStack(getFragmentManager(), fragment);

		} else if (i == R.id.delete_item) {
			Bundle bundle = prepareDeleteBundle();
			bundle.putString("url", deleteLink.getHref());
			DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
			deleteDialogFragment.setArguments(bundle);
			deleteDialogFragment.setTargetFragment(DetailResourceFragment.this, 0);
			deleteDialogFragment.show(getFragmentManager(), null);

		}

		return super.onOptionsItemSelected(item);
	}

	private void loadResource() {
		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().acceptHeader(mediaType).url(resourceUrl));
		client.sendRequest(getCallback());
	}
}
