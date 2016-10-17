package de.fiw.fhws.lecturers.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import de.fiw.fhws.lecturers.model.Lecturer;

public class ObjectRequest {

	public interface ObjectRequestListener {
		void onResponse(Lecturer lecturer);
		void onError(VolleyError error);
	}

	private String url;
	private int method;
	private JSONObject jsonObject;
	private Context context;
	private ObjectRequestListener listener;

	public ObjectRequest(String url, int method, JSONObject jsonObject, Context context, ObjectRequestListener listener) {
		this.url = url;
		this.method = method;
		this.jsonObject = jsonObject;
		this.context = context;
		this.listener = listener;
	}

	public void sendRequest() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, getResponseListener(), getErrorListener());
		VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
	}

	private Response.Listener getResponseListener() {
		return new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				listener.onResponse(Lecturer.toEntity(response));
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
