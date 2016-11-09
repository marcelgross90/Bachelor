package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class PhoneInput extends TextInputLayout {

	private EditText phone;

	public PhoneInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public PhoneInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public PhoneInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_phone, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PhoneInput, defStyle, 0);
		try {
			phone = (EditText) findViewById(R.id.phoneNumber_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String phoneNumber) {
		this.phone.setText(phoneNumber);
	}

	public String getText() {
		return this.phone.getText().toString().trim();
	}
}
