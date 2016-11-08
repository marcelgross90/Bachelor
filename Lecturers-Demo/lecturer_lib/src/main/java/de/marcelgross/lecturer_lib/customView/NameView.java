package de.marcelgross.lecturer_lib.customView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;

public class NameView extends TextView {

	public NameView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public NameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public NameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NameView, defStyle, 0);

		try {

			if (typedArray.getString(R.styleable.NameView_fullName) != null) {
				setText(typedArray.getString(R.styleable.NameView_fullName));
			} else {
				setText(R.string.full_name);
			}
		} finally {
			typedArray.recycle();
		}
	}
}
