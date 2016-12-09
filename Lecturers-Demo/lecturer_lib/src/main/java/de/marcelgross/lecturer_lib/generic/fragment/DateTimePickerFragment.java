package de.marcelgross.lecturer_lib.generic.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateTimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	final private Calendar calendar = Calendar.getInstance();
	private OnDateTimeSetListener listener;

	private int year;
	private int month;
	private int day;

	public interface OnDateTimeSetListener {
		void  onDateTimeSet(Date date);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listener = (OnDateTimeSetListener) getTargetFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onDateSet(DatePicker datePicker, final int year, final int month, final int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		TimePickerDialog timePickerdialog = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

		timePickerdialog.show();
	}

	@Override
	public void onTimeSet(TimePicker timePicker, int hour, int minute) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.GERMANY);
		StringBuilder dateTimeString = new StringBuilder();
		dateTimeString.append(year);
		dateTimeString.append("-");
		dateTimeString.append(month + 1);
		dateTimeString.append("-");
		dateTimeString.append(day);
		dateTimeString.append("T");
		dateTimeString.append(hour);
		dateTimeString.append(":");
		dateTimeString.append(minute);

		Date date;
		try {
			date = simpleDateFormat.parse(dateTimeString.toString());
		}catch (ParseException e) {
			date = new Date();
		}
		listener.onDateTimeSet(date);
	}
}
