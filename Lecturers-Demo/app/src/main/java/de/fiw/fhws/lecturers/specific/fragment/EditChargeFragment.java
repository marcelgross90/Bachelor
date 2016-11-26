package de.fiw.fhws.lecturers.specific.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.fragment.EditResourceFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.marcelgross.lecturer_lib.customView.ChargeInputView;
import de.marcelgross.lecturer_lib.customView.DateTimeView;
import de.marcelgross.lecturer_lib.fragment.DateTimePickerFragment;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Link;

public class EditChargeFragment extends EditResourceFragment implements View.OnClickListener, DateTimePickerFragment.OnDateTimeSetListener {

	private DateTimeView startDateView;
	private DateTimeView endDateView;
	private int state;


	@Override
	protected int getLayout() {
		return R.layout.fragment_edit_charge;
	}

	@Override
	protected void initializeView(View view) {
		inputView = (ChargeInputView) view.findViewById(R.id.input_view);
		startDateView = (DateTimeView) view.findViewById(R.id.startDate);
		endDateView = (DateTimeView) view.findViewById(R.id.endDate);

		startDateView.setOnClickListener(this);
		endDateView.setOnClickListener(this);
	}

	@Override
	protected NetworkCallback getLoadCallBack() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final Charge charge = genson.deserialize(response.getResponseReader(), Charge.class);
				Map<String, Link> linkHeader = response.getLinkHeader();

				resourceEditLink = linkHeader.get(getActivity().getString(R.string.rel_type_update_charge));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						inputView.setResource(charge);
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
		};
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
}