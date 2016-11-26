package de.fiw.fhws.lecturers.spezific.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.owlike.genson.GenericType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.fragment.RessourceListFragment;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.marcelgross.lecturer_lib.adapter.ChargeListAdapter;
import de.marcelgross.lecturer_lib.adapter.RessourceListAdapter;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Link;
import de.marcelgross.lecturer_lib.model.Ressource;

public class ChargeListFragment extends RessourceListFragment {

	private String detailChargeTemplateUrl;
	private ChargeListAdapter chargeListAdapter;

	@Override
	public void onResourceClickWithView(Ressource ressource, View view) {
		//not Needed
	}

	@Override
	public void onResourceClick(Ressource ressource) {
		Charge charge = (Charge) ressource;
		Fragment fragment = new ChargeDetailFragment();
		Bundle bundle = new Bundle();

		bundle.putString("url", detailChargeTemplateUrl.replace("{id}", String.valueOf(charge.getId())));
		bundle.putString("mediaType", charge.getSelf().getType());

		fragment.setArguments(bundle);

		FragmentHandler.replaceFragment(getFragmentManager(), fragment);
	}

	@Override
	protected NetworkCallback getCallBack() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), R.string.load__charges_error, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final List<Charge> charges = genson.deserialize(response.getResponseReader(), new GenericType<List<Charge>>() {
				});
				final List<Ressource> ressources = new ArrayList<>();
				for (Charge charge : charges) {
					ressources.add(charge);
				}
				Map<String, Link> linkHeader = response.getLinkHeader();
				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				Link oneChargeOfLecturerLink = linkHeader.get(getActivity().getString(R.string.rel_type_get_one_charge_of_lecturer));

				createNewRessourceLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_charge_of_lecturer));
				if (nextLink != null) {
					nextUrl = nextLink.getHref();
				} else {
					nextUrl = "";
				}

				if (oneChargeOfLecturerLink != null) {
					detailChargeTemplateUrl = oneChargeOfLecturerLink.getHref();
				} else {
					detailChargeTemplateUrl = "";
				}

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setHasOptionsMenu(createNewRessourceLink != null);
						showProgressBar(false);
						getAdapter().addRessource(ressources);
					}
				});
			}
		};
	}

	@Override
	protected RessourceListAdapter getAdapter() {
		if (chargeListAdapter == null) {
			chargeListAdapter = new ChargeListAdapter(ChargeListFragment.this);
		}
		return chargeListAdapter;
	}

	@Override
	protected Fragment getFragment() {
		return new NewChargeFragment();
	}
}