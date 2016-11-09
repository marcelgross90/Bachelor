package de.marcelgross.lecturer_lib.customView.textView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;

public class RoomView extends TextView {

	public RoomView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public RoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public RoomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoomView, defStyle, 0);

		try {

			if (typedArray.getString(R.styleable.RoomView_roomNumber) != null) {
				setText(typedArray.getString(R.styleable.RoomView_roomNumber));
			} else {
				setText(R.string.room);
			}
		} finally {
			typedArray.recycle();
		}
	}
}
