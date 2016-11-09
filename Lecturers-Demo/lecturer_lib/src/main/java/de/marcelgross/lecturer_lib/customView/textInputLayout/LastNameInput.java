package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class LastNameInput extends TextInputLayout {

	private EditText lastName;

	public LastNameInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public LastNameInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public LastNameInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_last_name, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LastNameInput, defStyle, 0);
		try {
			lastName = (EditText) findViewById(R.id.lastName_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String lastName) {
		this.lastName.setText(lastName);
	}

	public String getText() {
		return this.lastName.getText().toString().trim();
	}
}
