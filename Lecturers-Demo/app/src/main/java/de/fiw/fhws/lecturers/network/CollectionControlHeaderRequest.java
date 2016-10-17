package de.fiw.fhws.lecturers.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import static de.fiw.fhws.lecturers.network.HeaderParser.getLinks;

public class CollectionControlHeaderRequest {

	public interface CollectionControlHeaderRequestListener {
		void onResponse(int numberOfResults, int totalNumberOfResults, String selfUrl);
		void onError(VolleyError error);
	}

	private CollectionControlHeaderRequestListener listener;
	private String url;
	private Context context;

	public CollectionControlHeaderRequest(String url, Context context, CollectionControlHeaderRequestListener listener) {
		this.listener = listener;
		this.url = url;
		this.context = context;
	}

	public void sendRequest() {
		HeaderRequest headerRequest = new HeaderRequest(url, getResponseListener(), getErrorListener());
		VolleySingleton.getInstance(context).addToRequestQueue(headerRequest);
	}

	private Response.Listener getResponseListener() {
		return new Response.Listener<Map<String, String>>() {
			@Override
			public void onResponse(Map<String, String> response) {
				int numberOfResults = getResultFromHeader(response, "X-numberofresults");
				int totalNumberOfResults = getResultFromHeader(response, "X-totalnumberofresults");
				String selfUrl = getLinks(response.get("Link")).get("self");
				listener.onResponse(numberOfResults, totalNumberOfResults, selfUrl);
			}
		};
	}

	private int getResultFromHeader(Map<String, String> response, String header) {
		return Integer.valueOf(response.get(header));
	}

	private Response.ErrorListener getErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError(error);
			}
		};
	}
}
