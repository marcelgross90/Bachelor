package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;


public class AttributeInput extends TextInputLayout {

	private EditText attribute;

	public AttributeInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public AttributeInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AttributeInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_attribute, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AttributeInput, defStyle, 0);
		try {
			attribute = (EditText) findViewById(R.id.attribute_et);
			this.attribute.setHint(typedArray.getResourceId(R.styleable.AttributeInput_hintText, 0));
			this.attribute.setInputType(parseTextToInputType(typedArray.getString(R.styleable.AttributeInput_inputType)));
		} finally {
			typedArray.recycle();
		}
	}
	public void setText(String address) {
		this.attribute.setText(address);
	}

	public String getText() {
		return this.attribute.getText().toString().trim();
	}

	public int parseTextToInputType(String inputTypeString) {
		switch (inputTypeString) {
			case "text":
				return InputType.TYPE_CLASS_TEXT;
			case "phone":
				return InputType.TYPE_CLASS_PHONE;
			case "mail":
				return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
			default:
				return InputType.TYPE_CLASS_TEXT;
		}
	}
}
