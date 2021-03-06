package de.marcelgross.lecturer_lib.generic.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;

public class AttributeView extends TextView {

	public AttributeView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public AttributeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AttributeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AttributeView, defStyle, 0);

		typedArray.recycle();
	}
}
