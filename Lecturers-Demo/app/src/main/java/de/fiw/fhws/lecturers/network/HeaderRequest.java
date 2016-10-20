package de.fiw.fhws.lecturers.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.List;
import java.util.Map;

public class HeaderRequest extends Request<Map<String, String>> {

	private final Response.Listener<Map<String, String>> listener;

	public HeaderRequest(String url, Response.Listener<Map<String, String>> listener, Response.ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = listener;
	}

	@Override
	protected Response<Map<String, String>> parseNetworkResponse(NetworkResponse response) {
		return Response.success(response.headers, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(Map<String, String> response) {
		listener.onResponse(response);
	}
}