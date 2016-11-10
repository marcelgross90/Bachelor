package de.fiw.fhws.lecturers.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.io.IOException;

import de.fiw.fhws.lecturers.R;
import de.fiw.fhws.lecturers.network.OKHttpSingleton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DeleteDialogFragment extends DialogFragment {

	private String url;


	public interface DeleteDialogListener {
		void onDialogClosed(boolean successfullyDeleted);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final DeleteDialogListener deleteDialogListener = (DeleteDialogListener) getActivity();
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
		final Request request = new Request.Builder().url(url).delete().build();
		OkHttpClient client = OKHttpSingleton.getInstance(getActivity()).getClient();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				listener.onDialogClosed(false);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				}
					listener.onDialogClosed(true);
			}
		});
	}
}
