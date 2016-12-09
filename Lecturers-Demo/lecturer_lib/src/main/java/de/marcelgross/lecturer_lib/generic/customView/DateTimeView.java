package de.marcelgross.lecturer_lib.generic.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.marcelgross.lecturer_lib.R;


public class DateTimeView extends TextView {

	public DateTimeView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public DateTimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public DateTimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DateTimeView, defStyle, 0);

		try {
			Calendar calendar = Calendar.getInstance();

			String date =
					calendar.get(Calendar.DAY_OF_MONTH) +
							"." +
							calendar.get(Calendar.MONTH) +
							"." +
							calendar.get(Calendar.YEAR) +
							" " +
							calendar.get(Calendar.HOUR_OF_DAY) +
							":" +
							calendar.get(Calendar.MINUTE);

			setText(date);
		} finally {
			typedArray.recycle();
		}
	}

	public void setDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
		setText(simpleDateFormat.format(date));
	}
}
