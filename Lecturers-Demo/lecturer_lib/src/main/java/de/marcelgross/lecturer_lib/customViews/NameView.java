package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class NameView extends TextView {

	private Context context;

	public NameView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public NameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public NameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray fullName = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NameView, defStyle, 0);

		try {
			if (fullName.getString(R.styleable.NameView_fullName) != null){
				setText(fullName.getString(R.styleable.NameView_fullName));
			} else {
				setText(R.string.full_name);
			}
		} finally {
			fullName.recycle();
		}

	}

}
