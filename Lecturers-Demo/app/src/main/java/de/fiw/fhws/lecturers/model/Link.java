package de.fiw.fhws.lecturers.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Link {

	private String href;
	private String rel;

	public Link() {
		//for genson
	}

	public Link(String href, String rel) {
		this.href = href;
		this.rel = rel;
	}

	public static Link toEntity(JSONObject jsonObject) {
		try {
			return new Link(jsonObject.getString("href"), jsonObject.getString("rel"));
		} catch (JSONException ex) {
			return null;
		}
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
}