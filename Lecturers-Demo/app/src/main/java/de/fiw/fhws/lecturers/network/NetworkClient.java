package de.fiw.fhws.lecturers.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NetworkClient {

	private OkHttpClient client;
	private NetworkRequest request;

	public NetworkClient(Context context, NetworkRequest request) {
		this.client = OKHttpSingleton.getCacheInstance(context).getClient();
		this.request = request;
	}

	public void sendRequest(final NetworkCallback callback) {
		client.newCall(request.buildRequest()).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				callback.onFailure();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					Log.d("Request failure", response.toString());
					callback.onFailure();
				}
				callback.onSuccess(
						new NetworkResponse(response.body().charStream(), response.headers().toMultimap())
				);
			}
		});
	}
}
