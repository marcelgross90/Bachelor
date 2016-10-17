package de.fiw.fhws.lecturers.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fiw.fhws.lecturers.R;


public class LecturerDetailViewHolderOffice extends RecyclerView.ViewHolder {
	private TextView room;
	private TextView address;

	public LecturerDetailViewHolderOffice(View itemView, View.OnClickListener listener) {
		super(itemView);

		this.room = (TextView) itemView.findViewById(R.id.tvRoomValue);
		this.address = (TextView) itemView.findViewById(R.id.tvAddressValue);

		address.setOnClickListener(listener);
	}

	public void displayRoom(String room) {
		this.room.setText(room);
	}

	public void displayAddress(String address) {
		this.address.setText(address);
	}
}
