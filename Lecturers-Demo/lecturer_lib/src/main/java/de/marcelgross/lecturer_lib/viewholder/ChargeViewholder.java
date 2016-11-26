package de.marcelgross.lecturer_lib.viewholder;

import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.customView.ChargeCardView;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Resource;

public class ChargeViewHolder extends ResourceViewHolder {

	private final ChargeCardView chargeCardView;
	private final ResourceListAdapter.OnResourceClickListener onChargeClickListener;


	public ChargeViewHolder(View itemView, ResourceListAdapter.OnResourceClickListener onChargeClickListener) {
		super(itemView);

		chargeCardView = (ChargeCardView) itemView.findViewById(R.id.charge_card);
		this.onChargeClickListener = onChargeClickListener;
	}

	@Override
	public void assignData(final Resource resource) {
		final Charge charge = (Charge) resource;
		chargeCardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (onChargeClickListener != null) {
					onChargeClickListener.onResourceClick(charge);
					onChargeClickListener.onResourceClickWithView(charge, chargeCardView);
				}
			}
		});
		chargeCardView.setUpView(charge);
	}
}