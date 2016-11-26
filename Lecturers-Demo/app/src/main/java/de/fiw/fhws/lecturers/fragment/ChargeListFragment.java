package de.fiw.fhws.lecturers.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;
import de.fiw.fhws.lecturers.util.FragmentHandler;
import de.fiw.fhws.lecturers.util.GensonBuilder;
import de.fiw.fhws.lecturers.util.ScrollListener;
import de.marcelgross.lecturer_lib.adapter.ChargeListAdapter;
import de.marcelgross.lecturer_lib.model.Charge;
import de.marcelgross.lecturer_lib.model.Link;

public class ChargeListFragment extends Fragment implements ChargeListAdapter.OnChargeClickListener {

    private final Genson genson = new GensonBuilder().getDateFormater();
    private ChargeListAdapter chargeListAdapter;
    private ProgressBar progressBar;
    private String url;
    private String detailChargeTemplateUrl;
    private String mediaType;
    private String nextUrl;
    private Link createNewChargeLink;

    @Override
    public void onChargeClick(Charge charge) {
        Fragment fragment = new ChargeDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putString("url", detailChargeTemplateUrl.replace("{id}", String.valueOf(charge.getId())));
        bundle.putString("mediaType", charge.getSelf().getType());

        fragment.setArguments(bundle);

        FragmentHandler.replaceFragment(getFragmentManager(), fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        url = bundle.getString("url", "");
        mediaType = bundle.getString("mediaType", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charge_list, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        chargeListAdapter = new ChargeListAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.charge_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chargeListAdapter);
        recyclerView.addOnScrollListener(new ScrollListener(linearLayoutManager, new ScrollListener.OnScrollListener() {
            @Override
            public void load() {
                if (nextUrl != null && !nextUrl.isEmpty()) {
                    loadCharges(nextUrl);
                }
            }
        }));
        loadCharges(url);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lecturer_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addLecturer:
                Bundle bundle = new Bundle();
                if (createNewChargeLink != null) {
                    bundle.putString("url", createNewChargeLink.getHref());
                    bundle.putString("mediaType", createNewChargeLink.getType());
                }
                Fragment fragment = new NewChargeFragment();
                fragment.setArguments(bundle);
                FragmentHandler.replaceFragment(getFragmentManager(), fragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgressBar(final boolean show) {
        if (isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }
    }

    private void loadCharges(String url) {
        showProgressBar(true);

        NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().acceptHeader(mediaType).url(url));
        client.sendRequest(new NetworkCallback() {
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
                Map<String, Link> linkHeader = response.getLinkHeader();
                Link nextLink = linkHeader.get(getActivity().getString(R.string.rel_type_next));
                Link oneChargeOfLecturerLink = linkHeader.get(getActivity().getString(R.string.rel_type_get_one_charge_of_lecturer));

                createNewChargeLink = linkHeader.get(getActivity().getString(R.string.rel_type_create_charge_of_lecturer));
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
                        setHasOptionsMenu(createNewChargeLink != null);
                        showProgressBar(false);
                        chargeListAdapter.addCharge(charges);
                    }
                });

            }
        });
    }
}
