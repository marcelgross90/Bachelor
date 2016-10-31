package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class TitleView extends TextView {

	private Context context;

	public TitleView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray title = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TitleView, defStyle, 0);

		try {
			if (title.getString(R.styleable.TitleView_title) != null){
				setText(title.getString(R.styleable.TitleView_title));
			} else {
				setText(R.string.title);
			}
		} finally {
			title.recycle();
		}

	}

}
