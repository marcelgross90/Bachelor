package de.fiw.fhws.lecturers.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.customView.AddressView;
import de.fiw.fhws.lecturers.customView.LecturerDetailOfficeCardView;
import de.fiw.fhws.lecturers.model.Lecturer;

public class LecturerDetailViewHolderOffice extends RecyclerView.ViewHolder {
	private final LecturerDetailOfficeCardView lecturerDetailOfficeCardView;

	public LecturerDetailViewHolderOffice(View itemView, View.OnClickListener listener) {
		super(itemView);

		this.lecturerDetailOfficeCardView = (LecturerDetailOfficeCardView) itemView.findViewById(R.id.cardView);
		AddressView address = (AddressView) itemView.findViewById(R.id.tvAddressValue);

		address.setOnClickListener(listener);
	}

	public void assignData(Lecturer lecturer) {
		this.lecturerDetailOfficeCardView.setUpView(lecturer);
	}
}

