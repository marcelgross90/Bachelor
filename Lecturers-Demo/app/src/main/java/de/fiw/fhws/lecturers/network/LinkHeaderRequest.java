package de.fiw.fhws.lecturers.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.fiw.fhws.lecturers.network.util.HeaderParser.getLinks;

public class LinkHeaderRequest {

	public interface HeaderRequestListener {
		void onResponse(HashMap<String, String> links);
		void onResponse(String url);
		void onError(VolleyError error);
	}

	private HeaderRequestListener listener;
	private String url;
	private Context context;

	public LinkHeaderRequest(String url, Context context, HeaderRequestListener listener) {
		this.listener = listener;
		this.url = url;
		this.context = context;
	}


	public void sendRequest() {
		HeaderRequest headerRequest = new HeaderRequest(url, getResponseListener(), getErrorListener());
		VolleySingleton.getInstance(context).addToRequestQueue(headerRequest);
	}

	public void sendRequest(String relType) {
		HeaderRequest headerRequest = new HeaderRequest(url, getResponseListener(relType), getErrorListener());
		VolleySingleton.getInstance(context).addToRequestQueue(headerRequest);
	}


	private Response.Listener getResponseListener(final String relType) {
		return new Response.Listener<Map<String, String>>() {
			@Override
			public void onResponse(Map<String, String> response) {
				listener.onResponse(getLinks(response.get("Link")).get(relType));
			}
		};
	}

	private Response.Listener getResponseListener() {
		return new Response.Listener<Map<String, String>>() {
			@Override
			public void onResponse(Map<String, String> response) {
				listener.onResponse(getLinks(response.get("Link")));
			}
		};
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
