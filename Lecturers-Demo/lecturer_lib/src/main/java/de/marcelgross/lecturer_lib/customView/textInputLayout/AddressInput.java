package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class AddressInput extends TextInputLayout {

	private EditText address;

	public AddressInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public AddressInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AddressInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_address, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AddressInput, defStyle, 0);
		try {
			address = (EditText) findViewById(R.id.address_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String address) {
		this.address.setText(address);
	}

	public String getText() {
		return this.address.getText().toString().trim();
	}
}
