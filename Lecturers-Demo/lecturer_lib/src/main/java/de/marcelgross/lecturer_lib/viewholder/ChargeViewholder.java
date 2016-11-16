package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.customView.ChargeCardView;
import de.marcelgross.lecturer_lib.model.Charge;

public class ChargeViewHolder extends RecyclerView.ViewHolder {

	private final ChargeCardView chargeCardView;


	public ChargeViewHolder(View itemView) {
		super(itemView);

		chargeCardView = (ChargeCardView) itemView.findViewById(R.id.charge_card);
	}

	public void assignData(final Charge charge) {
		chargeCardView.setUpView(charge);
	}
}