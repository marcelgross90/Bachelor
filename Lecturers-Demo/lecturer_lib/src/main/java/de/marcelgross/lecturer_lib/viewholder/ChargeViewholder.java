package de.marcelgross.lecturer_lib.viewholder;

import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.RessourceListAdapter;
import de.marcelgross.lecturer_lib.customView.ChargeCardView;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Ressource;

public class ChargeViewHolder extends RessourceViewHolder {

	private final ChargeCardView chargeCardView;
	private final RessourceListAdapter.OnRessourceClickListener onChargeClickListener;


	public ChargeViewHolder(View itemView, RessourceListAdapter.OnRessourceClickListener onChargeClickListener) {
		super(itemView);

		chargeCardView = (ChargeCardView) itemView.findViewById(R.id.charge_card);
		this.onChargeClickListener = onChargeClickListener;
	}

	@Override
	public void assignData(final Ressource ressource) {
		final Charge charge = (Charge) ressource;
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