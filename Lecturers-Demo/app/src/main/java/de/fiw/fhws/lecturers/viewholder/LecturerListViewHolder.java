package de.fiw.fhws.lecturers.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.adapter.LecturerListAdapter;
import de.fiw.fhws.lecturers.model.Lecturer;
import de.fiw.fhws.lecturers.model.Link;

public class LecturerListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
	private Lecturer lecturer;
	private Context context;
	private LecturerListAdapter.OnLecturerClickListener onLecturerClickListener;

	private CardView cardView;
	private TextView title;
	private TextView name;
	private TextView email;
	private TextView phone;
	private TextView address;
	private TextView room;
	private TextView welearn;

	private ImageView profileImg;

	@Override
	public boolean onLongClick(View view) {
		switch (view.getId()) {
			case R.id.email:
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + lecturer.getEmail()));
				context.startActivity(Intent.createChooser(intent, "Send Email"));
				break;
			case R.id.phone:
				Intent dialIntent = new Intent(Intent.ACTION_DIAL);
				dialIntent.setData(Uri.parse("tel:" + lecturer.getPhone()));
				context.startActivity(dialIntent);
				break;
			case R.id.address:
				Uri location = Uri.parse("geo:0,0?q=" + lecturer.getAddress());
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
				mapIntent.setPackage("com.google.android.apps.maps");
				context.startActivity(mapIntent);
				break;
			case R.id.welearn:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturer.getUrlWelearn()));
				context.startActivity(browserIntent);
				break;
		}
		return false;
	}

	public LecturerListViewHolder(View itemView, Context context, LecturerListAdapter.OnLecturerClickListener onLecturerClickListener) {
		super(itemView);
		this.context = context;
		this.onLecturerClickListener = onLecturerClickListener;
		cardView = (CardView) itemView.findViewById(R.id.lecturer_card);
		title = (TextView) itemView.findViewById(R.id.title);
		name = (TextView) itemView.findViewById(R.id.name);
		email = (TextView) itemView.findViewById(R.id.email);
		phone = (TextView) itemView.findViewById(R.id.phone);
		address = (TextView) itemView.findViewById(R.id.address);
		room = (TextView) itemView.findViewById(R.id.room);
		welearn = (TextView) itemView.findViewById(R.id.welearn);

		profileImg = (ImageView) itemView.findViewById(R.id.profileImg);
	}

	public void assignData(final Lecturer lecturer) {
		this.lecturer = lecturer;

		cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (onLecturerClickListener != null) {
					onLecturerClickListener.onLecturerClick(lecturer, profileImg);
				}
			}
		});
		Link profileImage = lecturer.getProfileImageUrl();
		String profileImageUrl="empty";
		if (profileImage != null)
			profileImageUrl = profileImage.getHrefWithoutQueryParams();

		Picasso.with(context).load(profileImageUrl).resizeDimen(R.dimen.picture_width, R.dimen.picture_height).error(R.drawable.user_picture).into(profileImg);
		hideUnnecessaryViews(lecturer);

		title.setText(lecturer.getTitle());
		name.setText(lecturer.getFirstName() + " " + lecturer.getLastName());
		email.setText(lecturer.getEmail());
		email.setOnLongClickListener(this);
		phone.setText(lecturer.getPhone());
		phone.setOnLongClickListener(this);
		address.setText(lecturer.getAddress());
		address.setOnLongClickListener(this);
		room.setText(lecturer.getRoomNumber());
		welearn.setOnLongClickListener(this);
	}

	private void hideUnnecessaryViews(Lecturer lecturer) {
		if (lecturer.getTitle().trim().isEmpty()) {
			title.setVisibility(View.GONE);
		} else {
			title.setVisibility(View.VISIBLE);
		}
		if (lecturer.getPhone().trim().isEmpty()) {
			phone.setVisibility(View.GONE);
		} else {
			phone.setVisibility(View.VISIBLE);
		}
		if (lecturer.getUrlWelearn().trim().isEmpty()) {
			welearn.setVisibility(View.GONE);
		} else {
			welearn.setVisibility(View.VISIBLE);
		}
		if (lecturer.getRoomNumber().trim().isEmpty()) {
			room.setVisibility(View.GONE);
		} else {
			room.setVisibility(View.VISIBLE);
		}
	}
}