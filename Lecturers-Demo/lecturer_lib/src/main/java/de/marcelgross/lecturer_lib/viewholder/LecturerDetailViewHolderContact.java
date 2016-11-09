package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.LecturerDetailContactCardView;
import de.marcelgross.lecturer_lib.customView.textView.MailView;
import de.marcelgross.lecturer_lib.customView.textView.PhoneView;
import de.marcelgross.lecturer_lib.model.Lecturer;

public class LecturerDetailViewHolderContact extends RecyclerView.ViewHolder {
	private final LecturerDetailContactCardView lecturerDetailContactCardView;

	public LecturerDetailViewHolderContact(View itemView, View.OnClickListener listener) {
		super(itemView);
		this.lecturerDetailContactCardView = (LecturerDetailContactCardView) itemView.findViewById(R.id.contact_card);
		MailView email = (MailView) itemView.findViewById(R.id.tvEmailValue);
		PhoneView phone = (PhoneView) itemView.findViewById(R.id.tvPhoneValue);

		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
	}

	public void assignLecturer(Lecturer lecturer) {
		lecturerDetailContactCardView.setUpView(lecturer);
	}
}