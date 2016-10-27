package de.fiw.fhws.lecturers.model;

public class Lecturer {

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

	public Lecturer() {
		//for genson
	}

	public Lecturer(int id, String title, String firstName, String lastName, String email, String phone, String roomNumber, String address, String urlWelearn, Link profileImageUrl, Link self) {
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
}
