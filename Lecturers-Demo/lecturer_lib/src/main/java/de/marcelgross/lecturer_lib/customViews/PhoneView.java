package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class PhoneView extends TextView {

	private Context context;

	public PhoneView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public PhoneView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public PhoneView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray phoneNumber = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PhoneView, defStyle, 0);

		try {
			if (phoneNumber.getString(R.styleable.PhoneView_phoneNumber) != null){
				setText(phoneNumber.getString(R.styleable.PhoneView_phoneNumber));
			} else {
				setText(R.string.phone_number);
			}
		} finally {
			phoneNumber.recycle();
		}
	}

}
