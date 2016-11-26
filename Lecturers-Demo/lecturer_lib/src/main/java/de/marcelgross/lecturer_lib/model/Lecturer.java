package de.marcelgross.lecturer_lib.model;

public class Lecturer implements Ressource {

	private int id;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String roomNumber;
	private String address;
	private String urlWelearn;
	private Link profileImageUrl;
	private Link self;
	private Link chargeUrl;

	public Lecturer() {
		//for genson
	}

	public Lecturer(int id, String title, String firstName, String lastName, String email, String phone, String roomNumber, String address, String urlWelearn, Link profileImageUrl, Link self, Link chargeUrl) {
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.roomNumber = roomNumber;
		this.address = address;
		this.urlWelearn = urlWelearn;
		this.profileImageUrl = profileImageUrl;
		this.self = self;
		this.chargeUrl = chargeUrl;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrlWelearn() {
		return urlWelearn;
	}

	public void setUrlWelearn(String urlWelearn) {
		this.urlWelearn = urlWelearn;
	}

	public Link getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(Link profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Link getSelf() {
		return self;
	}

	public void setSelf(Link self) {
		this.self = self;
	}

	public Link getChargeUrl() {
		return chargeUrl;
	}

	public void setChargeUrl(Link chargeUrl) {
		this.chargeUrl = chargeUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Lecturer lecturer = (Lecturer) o;

		if (id != lecturer.id) return false;
		if (title != null ? !title.equals(lecturer.title) : lecturer.title != null) return false;
		if (firstName != null ? !firstName.equals(lecturer.firstName) : lecturer.firstName != null)
			return false;
		if (lastName != null ? !lastName.equals(lecturer.lastName) : lecturer.lastName != null)
			return false;
		if (email != null ? !email.equals(lecturer.email) : lecturer.email != null) return false;
		if (phone != null ? !phone.equals(lecturer.phone) : lecturer.phone != null) return false;
		if (roomNumber != null ? !roomNumber.equals(lecturer.roomNumber) : lecturer.roomNumber != null)
			return false;
		if (address != null ? !address.equals(lecturer.address) : lecturer.address != null)
			return false;
		if (urlWelearn != null ? !urlWelearn.equals(lecturer.urlWelearn) : lecturer.urlWelearn != null)
			return false;
		if (profileImageUrl != null ? !profileImageUrl.equals(lecturer.profileImageUrl) : lecturer.profileImageUrl != null)
			return false;
		if (self != null ? !self.equals(lecturer.self) : lecturer.self != null) return false;
		return chargeUrl != null ? chargeUrl.equals(lecturer.chargeUrl) : lecturer.chargeUrl == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (urlWelearn != null ? urlWelearn.hashCode() : 0);
		result = 31 * result + (profileImageUrl != null ? profileImageUrl.hashCode() : 0);
		result = 31 * result + (self != null ? self.hashCode() : 0);
		result = 31 * result + (chargeUrl != null ? chargeUrl.hashCode() : 0);
		return result;
	}
}
