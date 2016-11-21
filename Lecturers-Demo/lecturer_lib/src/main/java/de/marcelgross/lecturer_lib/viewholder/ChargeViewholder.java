package de.marcelgross.lecturer_lib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.ChargeListAdapter;
import de.marcelgross.lecturer_lib.customView.ChargeCardView;
import de.marcelgross.lecturer_lib.model.Charge;

public class ChargeViewHolder extends RecyclerView.ViewHolder {

	private final ChargeCardView chargeCardView;
	private final ChargeListAdapter.OnChargeClickListener onChargeClickListener;


	public ChargeViewHolder(View itemView, ChargeListAdapter.OnChargeClickListener onChargeClickListener) {
		super(itemView);

		chargeCardView = (ChargeCardView) itemView.findViewById(R.id.charge_card);
		this.onChargeClickListener = onChargeClickListener;
	}

	public void assignData(final Charge charge) {

		chargeCardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (onChargeClickListener != null) {
					onChargeClickListener.onChargeClick(charge);
				}
			}
		});
		chargeCardView.setUpView(charge);
	}
}