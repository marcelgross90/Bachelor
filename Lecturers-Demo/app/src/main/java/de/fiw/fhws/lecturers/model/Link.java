package de.fiw.fhws.lecturers.model;

public class Link {

	private String href;
	private String rel;
	private String type;

	public Link() {
		//for genson
	}

	public Link(String href, String rel, String type) {
		this.href = href;
		this.rel = rel;
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public String getHrefWithoutQueryParams() {
		if (href.contains("?")) {
			return this.href.split("\\?")[0];
		}
		return this.href;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}