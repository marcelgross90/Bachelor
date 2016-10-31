package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class RoomView extends TextView {

	private Context context;

	public RoomView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public RoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public RoomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray roomNumber = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.RoomView, defStyle, 0);

		try {
			if (roomNumber.getString(R.styleable.RoomView_roomNumber) != null){
				setText(roomNumber.getString(R.styleable.RoomView_roomNumber));
			} else {
				setText(R.string.room);
			}
		} finally {
			roomNumber.recycle();
		}

	}

}
