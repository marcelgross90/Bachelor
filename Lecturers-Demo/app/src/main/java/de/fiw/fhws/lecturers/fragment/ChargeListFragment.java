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
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.fiw.fhws.lecturers.FragmentHandler;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import de.fiw.fhws.lecturers.network.util.HeaderParser;
import de.marcelgross.lecturer_lib.adapter.ChargeListAdapter;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Link;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChargeListFragment extends Fragment implements ChargeListAdapter.OnChargeClickListener {

	private final Genson genson = new GensonBuilder()
			.useDateAsTimestamp(false)
			.useDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMANY))
			.create();
	private ChargeListAdapter chargeListAdapter;
	private ProgressBar progressBar;
	private String url;
	private String detailChargeTemplateUrl;
	private String mediaType;
	private String nextUrl;
	private Link createNewChargeLink;

	@Override
	public void onChargeClick(Charge charge) {
		Fragment fragment = new ChargeDetailFragment();
		Bundle bundle = new Bundle();

		bundle.putString("url", detailChargeTemplateUrl.replace("{id}", String.valueOf(charge.getId())));
		bundle.putString("mediaType", charge.getSelf().getType());

		fragment.setArguments(bundle);

		FragmentHandler.replaceFragment(getFragmentManager(), fragment);
	}

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
		View view =  inflater.inflate(R.layout.fragment_charge_list, container, false);

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		chargeListAdapter = new ChargeListAdapter(this);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charge_recycler_view);
		final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setAdapter(chargeListAdapter);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int visibleItems = recyclerView.getChildCount();
				int totalItems = linearLayoutManager.getItemCount();
				int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
				if (totalItems - visibleItems <= firstVisible + 1
						&& nextUrl != null && !nextUrl.isEmpty()) {
					loadCharges(nextUrl);
				}
			}
		});
		loadCharges(url);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.lecturer_list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addLecturer:
				Bundle bundle = new Bundle();
				if (createNewChargeLink != null) {
					bundle.putString("url", createNewChargeLink.getHref());
					bundle.putString("mediaType", createNewChargeLink.getType());
				}
				Fragment fragment = new NewChargeFragment();
				fragment.setArguments(bundle);
				FragmentHandler.replaceFragment(getFragmentManager(), fragment);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void showProgressBar(final boolean show) {
		if (isAdded()) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
				}
			});
		}
	}

	private void loadCharges(String url) {
		showProgressBar(true);

		Request request = new Request.Builder()
				.header("Accept", mediaType)
				.url(url)
				.build();

		OkHttpClient client =  OKHttpSingleton.getInstance(getActivity()).getClient();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.load__charges_error, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				}
				final List<Charge> charges = genson.deserialize(response.body().charStream(), new GenericType<List<Charge>>() {});

				Map<String, List<String>> headers = response.headers().toMultimap();
				Map<String, Link> linkHeader = HeaderParser.getLinks(headers.get("link"));
				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				Link oneChargeOfLecturerLink = linkHeader.get(getActivity().getString(R.string.rel_type_get_one_charge_of_lecturer));
				createNewChargeLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_charge_of_lecturer));
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}

				if (oneChargeOfLecturerLink != null) {
					detailChargeTemplateUrl = oneChargeOfLecturerLink.getHref();
				} else {
					detailChargeTemplateUrl = "";
				}

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setHasOptionsMenu(createNewChargeLink != null);
						showProgressBar(false);
						chargeListAdapter.addCharge(charges);
					}
				});
			}
		});
	}

}
