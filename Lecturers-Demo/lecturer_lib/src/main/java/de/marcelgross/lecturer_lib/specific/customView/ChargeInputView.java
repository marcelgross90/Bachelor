package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.util.AttributeSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.AttributeInput;
import de.marcelgross.lecturer_lib.generic.customView.DateTimeView;
import de.marcelgross.lecturer_lib.generic.customView.ResourceInputView;
import de.marcelgross.lecturer_lib.specific.model.Charge;
import de.marcelgross.lecturer_lib.generic.model.Resource;

public class ChargeInputView extends ResourceInputView {

	private Charge currentCharge;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
	private AttributeInput titleInput;
	private DateTimeView startDateView;
	private DateTimeView endDateView;
	private Charge oldCharge;

	public ChargeInputView(Context context) {
		super(context);
	}

	public ChargeInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChargeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	public void setResource(Resource resource) {
		Charge charge = (Charge) resource;
		this.oldCharge = charge;
		titleInput.setText(charge.getTitle());
		startDateView.setText(dateToString(charge.getFromDate()));
		endDateView.setText(dateToString(charge.getToDate()));
	}

	@Override
	public Resource getResource() {
		boolean error = false;
		String titleString = titleInput.getText();
		String startDateString = startDateView.getText().toString();
		String endDateString = endDateView.getText().toString();

		if (titleString.isEmpty()) {
			titleInput.setError(context.getString(R.string.title_missing));
			error = true;
		}

		if (!error) {
			if (currentCharge == null) {
				currentCharge = new Charge();
			}
			if (oldCharge != null) {
				currentCharge.setId(oldCharge.getId());
			}
			currentCharge.setTitle(titleString);
			currentCharge.setFromDate(stringToDate(startDateString));
			currentCharge.setToDate(stringToDate(endDateString));
			return currentCharge;
		}
		return null;
	}

	@Override
	protected void initializeViews() {
		titleInput = (AttributeInput) findViewById(R.id.title);
		startDateView = (DateTimeView) findViewById(R.id.startDate);
		endDateView = (DateTimeView) findViewById(R.id.endDate);
	}

	@Override
	protected int getLayout() {
		return R.layout.view_charge_input;
	}

	@Override
	protected int[] getStyleable() {
		return R.styleable.ChargeInputView;
	}

	private String dateToString(Date date) {
		return simpleDateFormat.format(date);
	}

	private Date stringToDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return new Date();
		}
	}
}