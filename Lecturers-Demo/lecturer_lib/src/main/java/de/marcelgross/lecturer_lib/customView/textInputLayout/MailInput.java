package de.marcelgross.lecturer_lib.customView.textInputLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import de.marcelgross.lecturer_lib.R;

public class MailInput extends TextInputLayout {

	private EditText mail;

	public MailInput(Context context) {
		super(context);
		init(context, null, 0);
	}

	public MailInput(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public MailInput(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.textinput_mail, this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MailInput, defStyle, 0);
		try {
			mail = (EditText) findViewById(R.id.email_et);
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String mailAddress) {
		this.mail.setText(mailAddress);
	}

	public String getText() {
		return this.mail.getText().toString().trim();
	}
}
