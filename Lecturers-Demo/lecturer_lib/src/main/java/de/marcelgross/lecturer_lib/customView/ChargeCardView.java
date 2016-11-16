package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Charge;

public class ChargeCardView extends CardView {

	private TextView chargeTitle;
	private TextView chargePeriod;

	public ChargeCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public ChargeCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public ChargeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.view_charge_card, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ChargeCardView, defStyle, 0);
		try {

			chargeTitle = (TextView) findViewById(R.id.charge_title);
			chargePeriod = (TextView) findViewById(R.id.charge_period);

		} finally {
			typedArray.recycle();
		}

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
