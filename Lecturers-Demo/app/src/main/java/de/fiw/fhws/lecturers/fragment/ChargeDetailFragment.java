package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.marcelgross.lecturer_lib.generic.fragment.DetailResourceFragment;
import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.network.NetworkCallback;
import de.marcelgross.lecturer_lib.generic.network.NetworkResponse;
import de.marcelgross.lecturer_lib.specific.customView.ChargeDetailView;
import de.marcelgross.lecturer_lib.specific.model.Charge;

public class ChargeDetailFragment extends DetailResourceFragment {

	@Override
	protected int getResourceDeleteError() {
		return R.string.charge_delete_error;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_charge_detail;
	}

	@Override
	protected void initializeView(View view) {
		resourceDetailView = (ChargeDetailView) view.findViewById(R.id.charge_detail_view);
	}

	@Override
	protected Fragment getEditFragment() {
		return new EditChargeFragment();
	}

	@Override
	protected Bundle prepareDeleteBundle() {
		Charge charge = (Charge) currentResource;
		Bundle bundle = new Bundle();
		bundle.putString("name", charge.getTitle());
		return null;
	}

	@Override
	protected NetworkCallback getCallback() {
		return new NetworkCallback() {
			@Override
			public void onFailure() {
			}

			@Override
			public void onSuccess(NetworkResponse response) {
				currentResource = genson.deserialize(response.getResponseReader(), Charge.class);
				Map<String, Link> linkHeader = response.getLinkHeader();

				deleteLink = linkHeader.get(getActivity().getString(R.string.rel_type_delete_charge));
				updateLink = linkHeader.get(getActivity().getString(R.string.rel_type_update_charge));

				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setUp((Charge) currentResource);
					}
				});
			}
		};
	}

	private void setUp(Charge charge) {
		getActivity().invalidateOptionsMenu();
		((ChargeDetailView) resourceDetailView).setUpView(charge);
	}
}