package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.ResourceDetailView;
import de.marcelgross.lecturer_lib.specific.model.Charge;

public class ChargeDetailView extends ResourceDetailView {

	private TextView chargeTitle;
	private TextView chargePeriod;

	public ChargeDetailView(Context context) {
		super(context);
	}

	public ChargeDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChargeDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected int getLayout() {
		return R.layout.view_charge_detail;
	}

	@Override
	protected int[] getStyleable() {
		return R.styleable.ChargeDetailView;
	}

	@Override
	protected void initializeView() {
		chargeTitle = (TextView) findViewById(R.id.charge_title);
		chargePeriod = (TextView) findViewById(R.id.charge_period);
	}

	public void setUpView(Charge charge) {
		chargeTitle.setText(charge.getTitle());
		chargePeriod.setText(convertDate(charge.getFromDate()) + " - " + convertDate(charge.getToDate()));
	}

	private String convertDate(Date date) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
		return formatter.format(date);
	}
}
