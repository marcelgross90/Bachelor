package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class MailView extends TextView {

	private Context context;

	public MailView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public MailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public MailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray mail = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MailView, defStyle, 0);

		try {
			if (mail.getString(R.styleable.MailView_email) != null){
				setText(mail.getString(R.styleable.MailView_email));
			} else {
				setText(R.string.email);
			}
		} finally {
			mail.recycle();
		}

	}

}
