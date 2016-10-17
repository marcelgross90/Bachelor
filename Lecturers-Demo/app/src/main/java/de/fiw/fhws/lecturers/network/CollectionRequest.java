package de.fiw.fhws.lecturers.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.fiw.fhws.lecturers.model.Lecturer;

public class CollectionRequest {

	public interface CollectionRequestListener {
		void onResponse(List<Lecturer> lecturerList);
		void onError(VolleyError error);
	}

	private String url;
	private Context context;
	private CollectionRequestListener listener;

	public CollectionRequest(String url, Context context, CollectionRequestListener listener) {
		this.url = url;
		this.context = context;
		this.listener = listener;
	}

	public void sendRequest() {
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, getResponseListener(), getErrorListener());
		VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
	}

	private Response.Listener getResponseListener() {
		return new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				List<Lecturer> lecturerList = new ArrayList<>();
				for (int i = 0; i < response.length(); i++) {
					try {
						lecturerList.add(Lecturer.toEntity(response.getJSONObject(i)));
					} catch (JSONException e) {
						//do nothing
					}
				}
				listener.onResponse(lecturerList);
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
