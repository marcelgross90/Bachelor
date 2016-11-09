package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class WelearnInput extends TextInputLayout {

	private EditText welearn;

	public WelearnInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public WelearnInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public WelearnInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_welearn, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.WelearnInput, defStyle, 0);
		try {
			welearn = (EditText) findViewById(R.id.welearn_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String welearnUrl) {
		this.welearn.setText(welearnUrl);
	}

	public String getText() {
		return this.welearn.getText().toString().trim();
	}
}
