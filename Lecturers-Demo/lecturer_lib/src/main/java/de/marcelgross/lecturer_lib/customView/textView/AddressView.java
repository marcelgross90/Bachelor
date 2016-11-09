package de.marcelgross.lecturer_lib.customView.textView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class AddressView extends TextView {

	public AddressView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public AddressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AddressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AddressView, defStyle, 0);

		try {
			if (typedArray.getString(R.styleable.AddressView_address) != null) {
				setText(typedArray.getString(R.styleable.AddressView_address));
			} else {
				setText(R.string.address);
			}
		} finally {
			typedArray.recycle();
		}
	}
}
