package de.fiw.fhws.lecturers.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import de.fiw.fhws.lecturers.R;
import de.marcelgross.lecturer_lib.customView.textView.EndDateView;
import de.marcelgross.lecturer_lib.customView.textView.StartDateView;
import de.marcelgross.lecturer_lib.fragment.DateTimePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditChargeFragment extends Fragment implements View.OnClickListener, DateTimePickerFragment.OnDateTimeSetListener {

	private StartDateView startDateView;
	private EndDateView endDateView;
	private int state;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_charge, container, false);

		startDateView = (StartDateView) view.findViewById(R.id.startDate);
		endDateView = (EndDateView) view.findViewById(R.id.endDate);

		startDateView.setOnClickListener(this);
		endDateView.setOnClickListener(this);

		return view;
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
