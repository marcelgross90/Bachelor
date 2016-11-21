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
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import de.fiw.fhws.lecturers.FragmentHandler;
import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import de.marcelgross.lecturer_lib.customView.ChargeDetailView;
import de.marcelgross.lecturer_lib.model.Charge;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeDetailFragment extends Fragment {

	private final Genson genson = new GensonBuilder()
			.useDateAsTimestamp(false)
			.useDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMANY))
			.create();
	private String chargeUrl;
	private String mediaType;
	private ChargeDetailView chargeDetailView;


	public ChargeDetailFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			chargeUrl = bundle.getString("url", "");
			mediaType = bundle.getString("mediaType", "");
		}

		loadCharge();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_charge_detail, container, false);

		chargeDetailView = (ChargeDetailView) view.findViewById(R.id.charge_detail_view);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.detail_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.edit_item:
				FragmentHandler.replaceFragment(getFragmentManager(), new EditChargeFragment());
				break;
			case R.id.delete_item:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void loadCharge() {
		final Request request = new Request.Builder()
				.header("Accept", mediaType)
				.url(chargeUrl)
				.build();

		OkHttpClient client = OKHttpSingleton.getInstance(getContext()).getClient();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException();
				}
				final Charge charge = genson.deserialize(response.body().charStream(), Charge.class);

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						chargeDetailView.setUpView(charge);
					}
				});

			}
		});
	}

}
