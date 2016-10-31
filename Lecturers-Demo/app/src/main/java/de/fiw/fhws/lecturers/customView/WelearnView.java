package de.fiw.fhws.lecturers.customView;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import de.fiw.fhws.lecturers.R;

public class WelearnView extends TextView {

	public WelearnView(Context context) {
		super(context);
		init(context, null, 0);

	}

	public WelearnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public WelearnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(final Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WelearnView, defStyle, 0);

		try {
			setText(R.string.welearn);
			final String welearnUrl = typedArray.getString(R.styleable.WelearnView_welearnAddress);
			if (welearnUrl != null) {
				setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(welearnUrl));
						context.startActivity(browserIntent);
					}
				});
			}
		} finally {
			typedArray.recycle();
		}
	}
}
