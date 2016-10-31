package de.marcelgross.lecturer_lib.model;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Link link = (Link) o;

		if (href != null ? !href.equals(link.href) : link.href != null) return false;
		if (rel != null ? !rel.equals(link.rel) : link.rel != null) return false;
		return type != null ? type.equals(link.type) : link.type == null;

	}

	@Override
	public int hashCode() {
		int result = href != null ? href.hashCode() : 0;
		result = 31 * result + (rel != null ? rel.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}
}