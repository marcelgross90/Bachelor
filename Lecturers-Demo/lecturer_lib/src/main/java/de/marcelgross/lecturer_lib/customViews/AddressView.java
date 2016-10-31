package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class AddressView extends TextView {

	private Context context;

	public AddressView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public AddressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public AddressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray address = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AddressView, defStyle, 0);

		try {
			if (address.getString(R.styleable.AddressView_address) != null){
				setText(address.getString(R.styleable.AddressView_address));
			} else {
				setText(R.string.address);
			}
		} finally {
			address.recycle();
		}

	}

}
