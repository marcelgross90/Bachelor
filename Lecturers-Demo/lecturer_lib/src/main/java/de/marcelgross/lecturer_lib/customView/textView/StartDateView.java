package de.marcelgross.lecturer_lib.customView.textView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;


public class StartDateView extends TextView {

	public StartDateView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public StartDateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public StartDateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StartDateView, defStyle, 0);

		try {
			Calendar calendar = Calendar.getInstance();

			StringBuilder builder = new StringBuilder();
			builder.append(calendar.get(Calendar.DAY_OF_MONTH));
			builder.append(".");
			builder.append(calendar.get(Calendar.MONTH));
			builder.append(".");
			builder.append(calendar.get(Calendar.YEAR));
			builder.append(" ");
			builder.append(calendar.get(Calendar.HOUR_OF_DAY));
			builder.append(":");
			builder.append(calendar.get(Calendar.MINUTE));

			setText(builder.toString());
		} finally {
			typedArray.recycle();
		}
	}

	public void setDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
		setText(simpleDateFormat.format(date));
	}
}
