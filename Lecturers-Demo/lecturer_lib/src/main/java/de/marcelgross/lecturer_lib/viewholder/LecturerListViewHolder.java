package de.marcelgross.lecturer_lib.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.LecturerListAdapter;
import de.marcelgross.lecturer_lib.customView.AddressView;
import de.marcelgross.lecturer_lib.customView.LecturerCardView;
import de.marcelgross.lecturer_lib.customView.MailView;
import de.marcelgross.lecturer_lib.customView.PhoneView;
import de.marcelgross.lecturer_lib.customView.ProfileImageView;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private Lecturer lecturer;
	private final Context context;
	private final LecturerListAdapter.OnLecturerClickListener onLecturerClickListener;

	private final LecturerCardView cardView;
	private final MailView email;
	private final PhoneView phone;
	private final AddressView address;

	private final ProfileImageView profileImg;

	@Override
	public void onClick(View view) {
		int i = view.getId();
		if (i == R.id.email) {
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + lecturer.getEmail()));
			context.startActivity(Intent.createChooser(intent, "Send Email"));

		} else if (i == R.id.phone) {
			Intent dialIntent = new Intent(Intent.ACTION_DIAL);
			dialIntent.setData(Uri.parse("tel:" + lecturer.getPhone()));
			context.startActivity(dialIntent);

		} else if (i == R.id.address) {
			Uri location = Uri.parse("geo:0,0?q=" + lecturer.getAddress());
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
			mapIntent.setPackage("com.google.android.apps.maps");
			context.startActivity(mapIntent);

		}
	}

	public LecturerListViewHolder(View itemView, LecturerListAdapter.OnLecturerClickListener onLecturerClickListener) {
		super(itemView);
		this.context = itemView.getContext();
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