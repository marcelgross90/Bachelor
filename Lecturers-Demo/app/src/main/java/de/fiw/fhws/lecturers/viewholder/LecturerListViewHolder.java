package de.fiw.fhws.lecturers.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.adapter.LecturerListAdapter;
import de.fiw.fhws.lecturers.customView.AddressView;
import de.fiw.fhws.lecturers.customView.LecturerCardView;
import de.fiw.fhws.lecturers.customView.MailView;
import de.fiw.fhws.lecturers.customView.PhoneView;
import de.fiw.fhws.lecturers.customView.ProfileImageView;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private Lecturer lecturer;
	private Context context;
	private LecturerListAdapter.OnLecturerClickListener onLecturerClickListener;

	private LecturerCardView cardView;
	private MailView email;
	private PhoneView phone;
	private AddressView address;

	private ProfileImageView profileImg;

	@Override
	public void onClick(View view) {
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
		}
	}

	public LecturerListViewHolder(View itemView, Context context, LecturerListAdapter.OnLecturerClickListener onLecturerClickListener) {
		super(itemView);
		this.context = context;
		this.onLecturerClickListener = onLecturerClickListener;
		cardView = (LecturerCardView) itemView.findViewById(R.id.lecturer_card);

		email = (MailView) itemView.findViewById(R.id.email);
		phone = (PhoneView) itemView.findViewById(R.id.phone);
		address = (AddressView) itemView.findViewById(R.id.address);

		profileImg = (ProfileImageView) itemView.findViewById(R.id.profileImg);
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

		cardView.setUpView(lecturer);

		email.setOnClickListener(this);
		phone.setOnClickListener(this);
		address.setOnClickListener(this);
	}

}