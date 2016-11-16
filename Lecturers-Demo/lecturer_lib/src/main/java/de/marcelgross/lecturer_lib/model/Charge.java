package de.marcelgross.lecturer_lib.model;


import java.util.Date;

public class Charge {

	private int id;
	private String title;
	private Date fromDate;
	private Date toDate;
	private Link self;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Link getSelf() {
		return self;
	}

	public void setSelf(Link self) {
		this.self = self;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Charge charge = (Charge) o;

		if (id != charge.id) return false;
		if (title != null ? !title.equals(charge.title) : charge.title != null) return false;
		if (fromDate != null ? !fromDate.equals(charge.fromDate) : charge.fromDate != null)
			return false;
		if (toDate != null ? !toDate.equals(charge.toDate) : charge.toDate != null) return false;
		return self != null ? self.equals(charge.self) : charge.self == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
		result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
		result = 31 * result + (self != null ? self.hashCode() : 0);
		return result;
	}
}
