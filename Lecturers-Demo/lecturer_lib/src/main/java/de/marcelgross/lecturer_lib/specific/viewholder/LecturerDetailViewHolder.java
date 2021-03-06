package de.marcelgross.lecturer_lib.specific.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.specific.customView.LecturerDetailCardView;
import de.marcelgross.lecturer_lib.generic.customView.AttributeView;
import de.marcelgross.lecturer_lib.specific.model.Lecturer;



public class LecturerDetailViewHolder extends RecyclerView.ViewHolder {
	private final LecturerDetailCardView lecturerDetailCardView;

	public LecturerDetailViewHolder(View itemView, View.OnClickListener listener) {
		super(itemView);

		this.lecturerDetailCardView = (LecturerDetailCardView) itemView.findViewById(R.id.lecturer_detail_card);
		AttributeView address = (AttributeView) itemView.findViewById(R.id.tvAddressValue);
		AttributeView email = (AttributeView) itemView.findViewById(R.id.tvEmailValue);
		AttributeView phone = (AttributeView) itemView.findViewById(R.id.tvPhoneValue);
		AttributeView welearn = (AttributeView) itemView.findViewById(R.id.tvWebsiteValue);
		Button chargesButton = (Button) itemView.findViewById(R.id.charges_btn);

		address.setOnClickListener(listener);
		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
		welearn.setOnClickListener(listener);
		chargesButton.setOnClickListener(listener);
	}

	public void assignData(Lecturer lecturer) {
		this.lecturerDetailCardView.setUpView(lecturer);
	}
}

