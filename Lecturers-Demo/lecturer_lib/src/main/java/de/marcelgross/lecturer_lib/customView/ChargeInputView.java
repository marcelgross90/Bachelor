package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.textView.EndDateView;
import de.marcelgross.lecturer_lib.customView.textView.StartDateView;
import de.marcelgross.lecturer_lib.model.Charge;

public class ChargeInputView extends LinearLayout {

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
	private final Context context;
	private final Charge currentCharge;
	private AttributeInput titleInput;
	private StartDateView startDateView;
	private EndDateView endDateView;
	private Charge charge;

	public ChargeInputView(Context context) {
		super(context);
		this.context = context;
		this.currentCharge = new Charge();
		init(context, null, 0);
	}

	public ChargeInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.currentCharge = new Charge();

		init(context, attrs, 0);
	}

	public ChargeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		this.currentCharge = new Charge();
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_charge_input, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ChargeInputView, defStyle, 0);
		try {
			titleInput = (AttributeInput) findViewById(R.id.title);
			startDateView = (StartDateView) findViewById(R.id.startDate);
			endDateView = (EndDateView) findViewById(R.id.endDate);
 		} finally {
			typedArray.recycle();
		}
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
		titleInput.setText(charge.getTitle());
		startDateView.setText(dateToString(charge.getFromDate()));
		endDateView.setText(dateToString(charge.getToDate()));
	}

	public Charge getCharge() {
		boolean error = false;
		String titleString = titleInput.getText();
		String startDateString = startDateView.getText().toString();
		String endDateString = endDateView.getText().toString();

		if (titleString.isEmpty()) {
			titleInput.setError(context.getString(R.string.title_missing));
			error = true;
		}

		if (!error) {
			if (charge != null) {
				currentCharge.setId(charge.getId());
			}
			currentCharge.setTitle(titleString);
			currentCharge.setFromDate(stringToDate(startDateString));
			currentCharge.setToDate(stringToDate(endDateString));
			return currentCharge;
		}
		return null;
	}

	private String dateToString(Date date) {
		return simpleDateFormat.format(date);
	}

	private Date stringToDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return  new Date();
		}
	}
}
