package de.fiw.fhws.lecturers.specific.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.fragment.NewResourceFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.marcelgross.lecturer_lib.customView.ChargeInputView;
import de.marcelgross.lecturer_lib.customView.DateTimeView;
import de.marcelgross.lecturer_lib.fragment.DateTimePickerFragment;
import de.marcelgross.lecturer_lib.model.Link;

public class NewChargeFragment extends NewResourceFragment implements View.OnClickListener, DateTimePickerFragment.OnDateTimeSetListener {
	private DateTimeView startDateView;
	private DateTimeView endDateView;
	private int state;

	@Override
	protected int getLayout() {
		return R.layout.fragment_new_charge;
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
	protected NetworkCallback getCallback() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {

			}

			@Override
			public void onSuccess(NetworkResponse response) {
				Map<String, Link> linkHeader = response.getLinkHeader();
				final Link allChargesLink = linkHeader.get(getActivity().getString(R.string.rel_type_get_all_charges));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.charge_saved, Toast.LENGTH_SHORT).show();
						Bundle bundle = new Bundle();
						bundle.putString("url", allChargesLink.getHrefWithoutQueryParams());
						bundle.putString("mediaType", allChargesLink.getType());
						Fragment fragment = new ChargeListFragment();
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
		dateTimePickerFragment.setTargetFragment(NewChargeFragment.this, 0);

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