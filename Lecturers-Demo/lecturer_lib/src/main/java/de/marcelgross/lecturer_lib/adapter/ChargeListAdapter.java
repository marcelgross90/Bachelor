package de.marcelgross.lecturer_lib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.viewholder.ChargeViewHolder;

public class ChargeListAdapter extends RessourceListAdapter<ChargeViewHolder> {

	private final OnRessourceClickListener onChargeClickListener;

	public ChargeListAdapter(OnRessourceClickListener onChargeClickListener) {
		this.onChargeClickListener = onChargeClickListener;
	}


	@Override
	public ChargeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.card_charge, parent, false);
		return new ChargeViewHolder(moduleCard, onChargeClickListener);
	}
}