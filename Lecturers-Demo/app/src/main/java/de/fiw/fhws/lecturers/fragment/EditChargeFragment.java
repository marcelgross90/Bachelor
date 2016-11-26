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
import android.widget.Toast;

import com.owlike.genson.Genson;

import java.util.Date;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.fiw.fhws.lecturers.util.GensonBuilder;
import de.marcelgross.lecturer_lib.customView.ChargeInputView;
import de.marcelgross.lecturer_lib.customView.DateTimeView;
import de.marcelgross.lecturer_lib.fragment.DateTimePickerFragment;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Link;

public class EditChargeFragment extends Fragment implements View.OnClickListener, DateTimePickerFragment.OnDateTimeSetListener {

	private final Genson genson = new GensonBuilder().getDateFormater();
	private ChargeInputView chargeInputView;
	private DateTimeView startDateView;
	private DateTimeView endDateView;
	private int state;

	private String url;
	private String mediaType;
	private Link chargeEditLink;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			url = bundle.getString("url", "");
			mediaType = bundle.getString("mediaType", "");
		}

		loadCharge();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_charge, container, false);

		chargeInputView = (ChargeInputView) view.findViewById(R.id.input_view);
		startDateView = (DateTimeView) view.findViewById(R.id.startDate);
		endDateView = (DateTimeView) view.findViewById(R.id.endDate);

		startDateView.setOnClickListener(this);
		endDateView.setOnClickListener(this);

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
				saveCharge();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		this.state = view.getId();

		DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment();
		dateTimePickerFragment.setTargetFragment(EditChargeFragment.this, 0);

		dateTimePickerFragment.show(getFragmentManager(), "dateTime");

	}

	@Override
	public void onDateTimeSet(Date date) {
		switch (state) {
			case R.id.startDate:
				startDateView.setDate(date);
				break;
			case R.id.endDate:
				endDateView.setDate(date);
		}
	}

	private void loadCharge() {
		NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().acceptHeader(mediaType).url(url));
		client.sendRequest(new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final Charge charge = genson.deserialize(response.getResponseReader(), Charge.class);
				Map<String, Link> linkHeader = response.getLinkHeader();

				chargeEditLink = linkHeader.get(getActivity().getString(R.string.rel_type_update_charge));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						chargeInputView.setCharge(charge);
					}
				});
			}
		});
	}

	private void saveCharge() {
		Charge charge = chargeInputView.getCharge();

		if (charge != null) {
			String chargeString = genson.serialize(charge);

			NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(chargeEditLink.getHref()).put(chargeString, chargeEditLink.getType()));
			client.sendRequest(new NetworkCallback() {
				@Override
				public void onFailure() {

				}

				@Override
				public void onSuccess(NetworkResponse response) {

					Map<String, Link> linkHeader = response.getLinkHeader();
					final Link selfLink = linkHeader.get(getActivity().getString(R.string.rel_type_self));

					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getActivity(), R.string.charge_updated, Toast.LENGTH_SHORT).show();
							Bundle bundle = new Bundle();
							bundle.putString("url", selfLink.getHref());
							bundle.putString("mediaType", selfLink.getType());
							Fragment fragment = new ChargeDetailFragment();
							fragment.setArguments(bundle);

							FragmentHandler.replaceFragmentPopBackStack(getFragmentManager(), fragment);
						}
					});
				}
			});
		}
	}
}
