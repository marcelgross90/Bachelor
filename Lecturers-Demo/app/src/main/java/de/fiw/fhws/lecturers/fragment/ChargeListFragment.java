package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.owlike.genson.GenericType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.marcelgross.lecturer_lib.generic.adapter.ResourceListAdapter;
import de.marcelgross.lecturer_lib.generic.fragment.ResourceListFragment;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.model.Resource;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkResponse;
import de.marcelgross.lecturer_lib.generic.util.FragmentHandler;
import de.marcelgross.lecturer_lib.specific.adapter.ChargeListAdapter;
import de.marcelgross.lecturer_lib.specific.model.Charge;

public class ChargeListFragment extends ResourceListFragment {

	private String detailChargeTemplateUrl;
	private ChargeListAdapter chargeListAdapter;

	@Override
	public void onResourceClickWithView(Resource resource, View view) {
		//not Needed
	}

	@Override
	public void onResourceClick(Resource resource) {
		Charge charge = (Charge) resource;
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
						Toast.makeText(getActivity(), R.string.load_charges_error, Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				final List<Charge> charges = genson.deserialize(response.getResponseReader(), new GenericType<List<Charge>>() {
				});
				final List<Resource> resources = new ArrayList<>();
				for (Charge charge : charges) {
					resources.add(charge);
				}
				Map<String, Link> linkHeader = response.getLinkHeader();
				Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
				Link oneChargeOfLecturerLink = linkHeader.get(getActivity().getString(R.string.rel_type_get_one_charge_of_lecturer));

				createNewResourceLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_charge_of_lecturer));
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
						setHasOptionsMenu(createNewResourceLink != null);
						showProgressBar(false);
						getAdapter().addResource(resources);
					}
				});
			}
		};
	}

	@Override
	protected ResourceListAdapter getAdapter() {
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