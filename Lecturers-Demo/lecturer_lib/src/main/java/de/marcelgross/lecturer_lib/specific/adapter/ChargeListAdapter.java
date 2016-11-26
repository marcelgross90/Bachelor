package de.marcelgross.lecturer_lib.specific.adapter;

import android.view.View;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.specific.viewholder.ChargeViewHolder;

public class ChargeListAdapter extends ResourceListAdapter<ChargeViewHolder> {

	public ChargeListAdapter(OnResourceClickListener onChargeClickListener) {
		super(onChargeClickListener);
	}

	@Override
	protected ChargeViewHolder getViewHolder(View moduleCard, OnResourceClickListener onResourceClickListener) {
		return new ChargeViewHolder(moduleCard, onResourceClickListener);
	}

	@Override
	protected int getLayout() {
		return R.layout.card_charge;
	}
}