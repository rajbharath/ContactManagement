package com.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	private int id;
	private String name;
	private String mobile;
	private String landLine;
	private String email;

	public Contact() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLandLine() {
		return landLine;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Contact))
			return false;
		Contact anotherObject = (Contact) obj;
		return anotherObject.getId() == id;
	}

}
