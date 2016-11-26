package de.marcelgross.lecturer_lib.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import de.marcelgross.lecturer_lib.model.Ressource;

/**
 * Created by marcelgross on 26.11.16.
 */

public abstract class RessourceInputView extends ScrollView {

	protected final Context context;

	public RessourceInputView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);
	}

	public RessourceInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init(context, attrs, 0);
	}

	public RessourceInputView(Context context, AttributeSet attrs, int defStyleAttr) {
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


	public abstract void setRessource(Ressource ressource);

	public abstract Ressource getRessource();

	protected abstract void initializeViews();

	protected abstract int getLayout();

	protected abstract int[] getStyleable();
}
