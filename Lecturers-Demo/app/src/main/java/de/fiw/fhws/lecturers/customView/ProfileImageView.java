package de.fiw.fhws.lecturers.customView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.model.Link;

public class ProfileImageView extends ImageView {

	private final Context context;

	public ProfileImageView(Context context) {
		super(context);
		this.context = context;
		init(context, null, 0);

	}

	public ProfileImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs, 0);
	}

	public ProfileImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context, attrs, defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PhoneView, defStyle, 0);
		typedArray.recycle();
	}

	public void loadImage(Link profileImage) {
		String profileImageUrl = "empty";
		if (profileImage != null)
			profileImageUrl = profileImage.getHrefWithoutQueryParams();

		Picasso.with(context).load(profileImageUrl).resizeDimen(R.dimen.picture_width, R.dimen.picture_height).error(R.drawable.user_picture).into(this);
	}
}
