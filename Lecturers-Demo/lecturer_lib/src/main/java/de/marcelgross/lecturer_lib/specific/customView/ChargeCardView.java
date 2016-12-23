package de.marcelgross.lecturer_lib.specific.customView;

import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.generic.customView.AttributeView;
import de.marcelgross.lecturer_lib.generic.customView.ResourceCardView;
import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.specific.model.Charge;

public class ChargeCardView extends ResourceCardView {

	private AttributeView chargeTitle;
	private AttributeView chargePeriod;

	public ChargeCardView(Context context) {
		super(context);
	}

	public ChargeCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChargeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected int[] getStyleable() {
		return R.styleable.ChargeCardView;
	}

	@Override
	protected int getLayout() {
		return R.layout.view_charge_card;
	}

	@Override
	protected void initializeView() {
		chargeTitle = (AttributeView) findViewById(R.id.charge_title);
		chargePeriod = (AttributeView) findViewById(R.id.charge_period);
	}

	@Override
	public void setUpView(Resource resource) {
		Charge charge = (Charge) resource;
		chargeTitle.setText(charge.getTitle());
		chargePeriod.setText(convertDate(charge.getFromDate()) + " - " + convertDate(charge.getToDate()));
	}

	private String convertDate(Date date) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
		return formatter.format(date);
	}
}
