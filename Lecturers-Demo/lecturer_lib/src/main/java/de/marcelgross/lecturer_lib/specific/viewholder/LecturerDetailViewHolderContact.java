package de.marcelgross.lecturer_lib.specific.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.specific.customView.LecturerDetailContactCardView;
import de.marcelgross.lecturer_lib.customView.AttributeView;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;

public class LecturerDetailViewHolderContact extends RecyclerView.ViewHolder {
	private final LecturerDetailContactCardView lecturerDetailContactCardView;

	public LecturerDetailViewHolderContact(View itemView, View.OnClickListener listener) {
		super(itemView);
		this.lecturerDetailContactCardView = (LecturerDetailContactCardView) itemView.findViewById(R.id.contact_card);
		AttributeView email = (AttributeView) itemView.findViewById(R.id.tvEmailValue);
		AttributeView phone = (AttributeView) itemView.findViewById(R.id.tvPhoneValue);
		AttributeView welearn = (AttributeView) itemView.findViewById(R.id.tvWebsiteValue);
		Button chargesButton = (Button) itemView.findViewById(R.id.charges_btn);

		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
		welearn.setOnClickListener(listener);
		chargesButton.setOnClickListener(listener);
	}

	public void assignLecturer(Lecturer lecturer) {
		lecturerDetailContactCardView.setUpView(lecturer);
	}
}