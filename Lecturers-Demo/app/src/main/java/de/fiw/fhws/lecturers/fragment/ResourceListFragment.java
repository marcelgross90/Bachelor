package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.owlike.genson.Genson;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.fiw.fhws.lecturers.util.GensonBuilder;
import de.fiw.fhws.lecturers.util.ScrollListener;
import de.marcelgross.lecturer_lib.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Resource;

public abstract class ResourceListFragment extends Fragment implements ResourceListAdapter.OnResourceClickListener {

	protected final Genson genson = new GensonBuilder().getDateFormatter();
	private ProgressBar progressBar;
	private String url;
	private String mediaType;
	protected String nextUrl;
	protected Link createNewResourceLink;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		url = bundle.getString("url", "");
		mediaType = bundle.getString("mediaType", "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_resource_list, container, false);

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


		RecyclerView modulesRecyclerView = (RecyclerView) view.findViewById(R.id.resource_recycler_view);
		LinearLayoutManager modulesLayoutMgr = new LinearLayoutManager(getContext());

		modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
		modulesRecyclerView.setAdapter(getAdapter());
		modulesRecyclerView.addOnScrollListener(new ScrollListener(modulesLayoutMgr, new ScrollListener.OnScrollListener() {
			@Override
			public void load() {
				if (nextUrl != null && !nextUrl.isEmpty()) {
					loadResources(nextUrl);
				}
			}
		}));

		loadResources(url);
		return view;
	}

	@Override
	public abstract void onResourceClickWithView(Resource resource, View view);

	@Override
	public abstract void onResourceClick(Resource resource);

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add:
				Bundle bundle = new Bundle();
				if (createNewResourceLink != null) {
					bundle.putString("url", createNewResourceLink.getHref());
					bundle.putString("mediaType", createNewResourceLink.getType());
				}

				Fragment fragment = getFragment();
				fragment.setArguments(bundle);
				FragmentHandler.replaceFragment(getFragmentManager(), fragment);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void showProgressBar(final boolean show) {
		if (isAdded()) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
				}
			});
		}
	}

	private void loadResources(String url) {
		showProgressBar(true);

		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).acceptHeader(mediaType));
		client.sendRequest(getCallBack());
	}

	protected abstract NetworkCallback getCallBack();
	protected abstract ResourceListAdapter getAdapter();
	protected abstract Fragment getFragment();
}
