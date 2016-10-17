package de.fiw.fhws.lecturers.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fiw.fhws.lecturers.R;

public class LecturerDetailViewHolderContact extends RecyclerView.ViewHolder {
	private TextView email;
	private TextView phone;
	private TextView website;

	public LecturerDetailViewHolderContact(View itemView, View.OnClickListener listener) {
		super(itemView);

		this.email = (TextView) itemView.findViewById(R.id.tvEmailValue);
		this.phone = (TextView) itemView.findViewById(R.id.tvPhoneValue);
		this.website = (TextView) itemView.findViewById(R.id.tvWebsiteValue);

		email.setOnClickListener(listener);
		phone.setOnClickListener(listener);
		website.setOnClickListener(listener);
	}

	public void displayEmail(String email) {
		this.email.setText(email);
	}

	public void displayPhone(String phone) {
		this.phone.setText(phone);
	}

	public void displayWebsite(String website) {
		this.website.setText(website);
	}
}