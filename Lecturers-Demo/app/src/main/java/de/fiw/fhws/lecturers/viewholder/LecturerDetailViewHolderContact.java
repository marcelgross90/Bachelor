package de.fiw.fhws.lecturers.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.customView.LecturerDetailContactCardView;
import de.fiw.fhws.lecturers.customView.MailView;
import de.fiw.fhws.lecturers.customView.PhoneView;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerDetailViewHolderContact extends RecyclerView.ViewHolder {
	private final LecturerDetailContactCardView lecturerDetailContactCardView;

	public LecturerDetailViewHolderContact(View itemView, View.OnClickListener listener) {
		super(itemView);
		this.lecturerDetailContactCardView = (LecturerDetailContactCardView) itemView.findViewById(R.id.cardView);
		MailView email = (MailView) itemView.findViewById(R.id.tvEmailValue);
		PhoneView phone = (PhoneView) itemView.findViewById(R.id.tvPhoneValue);

		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
	}

	public void assignLecturer(Lecturer lecturer) {
		lecturerDetailContactCardView.setUpView(lecturer);
	}
}