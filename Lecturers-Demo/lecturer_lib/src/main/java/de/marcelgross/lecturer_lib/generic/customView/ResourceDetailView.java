package de.marcelgross.lecturer_lib.generic.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public abstract class ResourceDetailView extends RelativeLayout {

	protected abstract int getLayout();
	protected abstract int[] getStyleable();
	protected abstract void initializeView();

	protected final Context context;

	public ResourceDetailView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public ResourceDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public ResourceDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(getLayout(), this, false));

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, getStyleable(), defStyle, 0);
		try {

			initializeView();

		} finally {
			typedArray.recycle();
		}
	}
}
