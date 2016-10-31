package de.marcelgross.lecturer_lib.customViews;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import de.marcelgross.lecturer_lib.R;


public class WelearnView extends TextView {

	private final Context context;

	public WelearnView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public WelearnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public WelearnView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(final Context context, AttributeSet attributeSet, int defStyle) {
		TypedArray welearnAddress = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.WelearnView, defStyle, 0);
		try {
			setText(R.string.welearn);
		} finally {
			welearnAddress.recycle();
		}
	}

	public void setAddress(final String url) {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				context.startActivity(browserIntent);
			}
		});
	}
}
