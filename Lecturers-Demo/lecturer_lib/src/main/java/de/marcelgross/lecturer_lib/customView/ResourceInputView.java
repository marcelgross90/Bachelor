package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import de.marcelgross.lecturer_lib.model.Resource;

public abstract class ResourceInputView extends ScrollView {

	protected final Context context;

	public ResourceInputView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public ResourceInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init(context, attrs, 0);
	}

	public ResourceInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attributeSet, int defStyle) {
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.addView(inflater.inflate(getLayout(), this, false));

			TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, getStyleable(), defStyle, 0);
			try {
				initializeViews();

			} finally {
				typedArray.recycle();
			}
		}
	}


	public abstract void setResource(Resource resource);

	public abstract Resource getResource();

	protected abstract void initializeViews();

	protected abstract int getLayout();

	protected abstract int[] getStyleable();
}
