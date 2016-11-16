package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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
		Button chargesButton = (Button) itemView.findViewById(R.id.charges_btn);

		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
		chargesButton.setOnClickListener(listener);
	}

	public void assignLecturer(Lecturer lecturer) {
		lecturerDetailContactCardView.setUpView(lecturer);
	}
}