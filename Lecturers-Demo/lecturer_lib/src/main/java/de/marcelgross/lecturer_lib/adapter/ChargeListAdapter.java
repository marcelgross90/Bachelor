package de.marcelgross.lecturer_lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.marcelgross.lecturer_lib.R;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.viewholder.ChargeViewHolder;

public class ChargeListAdapter extends RecyclerView.Adapter<ChargeViewHolder> {

	private final List<Charge> chargeList = new ArrayList<>();
	private final OnChargeClickListener onChargeClickListener;

	public interface OnChargeClickListener {
		void onChargeClick(Charge charge);
	}

	public ChargeListAdapter(OnChargeClickListener onChargeClickListener) {
		this.onChargeClickListener = onChargeClickListener;
	}

	public void addCharge(List<Charge> newCharges) {
		for (Charge newCharge : newCharges) {
			if (!this.chargeList.contains(newCharge)) {
				this.chargeList.add(newCharge);
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public ChargeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View moduleCard = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.card_charge, parent, false);
		return new ChargeViewHolder(moduleCard, onChargeClickListener);
	}

	@Override
	public void onBindViewHolder(ChargeViewHolder holder, int position) {
		holder.assignData(chargeList.get(position));
	}

	@Override
	public int getItemCount() {
		return chargeList.size();
	}
}