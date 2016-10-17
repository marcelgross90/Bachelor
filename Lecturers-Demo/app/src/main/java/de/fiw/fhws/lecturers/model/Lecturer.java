package de.fiw.fhws.lecturers.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Lecturer {

	private int id;
	private String address;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String roomNumber;
	private String title;
	private String urlProfileImage;
	private String urlWelearn;
	private Link roundImage;
	private Link self;

	public Lecturer() {
		//for genson
	}

	public Lecturer(int id, String address, String email, String firstName, String lastName, String phone, String roomNumber, String title, String urlProfileImage, String urlWelearn, Link roundImage, Link self) {
		this.id = id;
		this.address = address;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.roomNumber = roomNumber;
		this.title = title;
		this.urlProfileImage = urlProfileImage;
		this.urlWelearn = urlWelearn;
		this.roundImage = roundImage;
		this.self = self;
	}

	public static Lecturer toEntity(JSONObject jsonObject) {
		try {
			Lecturer lecturer = new Lecturer();
			lecturer.setId(jsonObject.getInt("id"));
			lecturer.setAddress(jsonObject.getString("address"));
			lecturer.setEmail(jsonObject.getString("email"));
			lecturer.setFirstName(jsonObject.getString("firstName"));
			lecturer.setLastName(jsonObject.getString("lastName"));
			lecturer.setPhone(jsonObject.getString("phone"));
			lecturer.setRoomNumber(jsonObject.getString("roomNumber"));
			lecturer.setTitle(jsonObject.getString("title"));
			lecturer.setUrlProfileImage(jsonObject.getString("urlProfileImage"));
			lecturer.setUrlWelearn(jsonObject.getString("urlWelearn"));
			lecturer.setRoundImage(Link.toEntity(jsonObject.getJSONObject("roundImage")));
			lecturer.setSelf(Link.toEntity(jsonObject.getJSONObject("self")));
			return lecturer;
		} catch (JSONException ex) {
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlProfileImage() {
		return urlProfileImage;
	}

	public void setUrlProfileImage(String urlProfileImage) {
		this.urlProfileImage = urlProfileImage;
	}

	public String getUrlWelearn() {
		return urlWelearn;
	}

	public void setUrlWelearn(String urlWelearn) {
		this.urlWelearn = urlWelearn;
	}

	public Link getRoundImage() {
		return roundImage;
	}

	public void setRoundImage(Link roundImage) {
		this.roundImage = roundImage;
	}

	public Link getSelf() {
		return self;
	}

	public void setSelf(Link self) {
		this.self = self;
	}
}
