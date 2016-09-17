package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class Person {

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("ImageUrl")
	private String imageUrl;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

}
