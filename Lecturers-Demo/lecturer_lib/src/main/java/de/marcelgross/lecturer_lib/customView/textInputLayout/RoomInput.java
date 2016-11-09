package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class RoomInput extends TextInputLayout {

	private EditText room;

	public RoomInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public RoomInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public RoomInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_room, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.RoomInput, defStyle, 0);
		try {
			room = (EditText) findViewById(R.id.room_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String roomNumber) {
		this.room.setText(roomNumber);
	}

	public String getText() {
		return this.room.getText().toString().trim();
	}
}
