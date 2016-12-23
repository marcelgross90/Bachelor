package de.marcelgross.lecturer_lib.generic.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import de.marcelgross.lecturer_lib.generic.model.Resource;


public abstract class ResourceCardView extends CardView {

	protected abstract int getLayout();
	protected abstract int[] getStyleable();
	protected abstract void initializeView();
	public abstract void setUpView(Resource resource);

	public ResourceCardView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public ResourceCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public ResourceCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
