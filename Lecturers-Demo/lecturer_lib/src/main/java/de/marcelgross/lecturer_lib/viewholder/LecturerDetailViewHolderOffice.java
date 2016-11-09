package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.textView.AddressView;
import de.marcelgross.lecturer_lib.customView.LecturerDetailOfficeCardView;
import de.marcelgross.lecturer_lib.model.Lecturer;



public class LecturerDetailViewHolderOffice extends RecyclerView.ViewHolder {
	private final LecturerDetailOfficeCardView lecturerDetailOfficeCardView;

	public LecturerDetailViewHolderOffice(View itemView, View.OnClickListener listener) {
		super(itemView);

		this.lecturerDetailOfficeCardView = (LecturerDetailOfficeCardView) itemView.findViewById(R.id.office_card);
		AddressView address = (AddressView) itemView.findViewById(R.id.tvAddressValue);

		address.setOnClickListener(listener);
	}

	public void assignData(Lecturer lecturer) {
		this.lecturerDetailOfficeCardView.setUpView(lecturer);
	}
}

