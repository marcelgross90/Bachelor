package de.fiw.fhws.lecturers.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.NetworkCallback;
import de.fiw.fhws.lecturers.network.NetworkClient;
import de.fiw.fhws.lecturers.network.NetworkRequest;
import de.fiw.fhws.lecturers.network.NetworkResponse;

public class DeleteDialogFragment extends DialogFragment {

    private String url;
    private DeleteDialogListener deleteDialogListener;

    public interface DeleteDialogListener {
        void onDialogClosed(boolean successfullyDeleted);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            deleteDialogListener = (DeleteDialogListener) getActivity();
        } catch (ClassCastException ex) {
            deleteDialogListener = (DeleteDialogListener) getTargetFragment();
        }
        Bundle bundle = getArguments();
        url = bundle.getString("url");
        String name = bundle.getString("name");

        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.delete_dialog_title, name))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(deleteDialogListener);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
    }

    private void delete(final DeleteDialogListener listener) {
        NetworkClient client = new NetworkClient(getActivity(), new NetworkRequest().url(url).delete());
        client.sendRequest(new NetworkCallback() {
            @Override
            public void onFailure() {
                listener.onDialogClosed(false);
            }

            @Override
            public void onSuccess(NetworkResponse response) {
                listener.onDialogClosed(true);
            }
        });
    }
}
