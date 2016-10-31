package de.fiw.fhws.lecturers.customView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.fiw.fhws.lecturers.R;

public class TitleView extends TextView {

	public TitleView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyle, 0);

		try {

			if (typedArray.getString(R.styleable.TitleView_title) != null) {
				setText(typedArray.getString(R.styleable.TitleView_title));
			} else {
				setText(R.string.title);
			}
		} finally {
			typedArray.recycle();
		}
	}
}
