package de.marcelgross.lecturer_lib.customView;


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

	public WelearnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context, attrs, defStyle);
	}

	private void init(final Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WelearnView, defStyle, 0);

		try {
			setText(R.string.welearn);
		} finally {
			typedArray.recycle();
		}
	}

	public void setAddress(final String welearnUrl) {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(welearnUrl));
				context.startActivity(browserIntent);
			}
		});
	}
}
