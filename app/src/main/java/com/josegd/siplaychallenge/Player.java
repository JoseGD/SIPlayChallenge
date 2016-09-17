package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class Player {

	@SerializedName("Person")
	private Person person;

	@SerializedName("JerseyNumber")
	private String jerseyNumber;

	public Person getPerson() {
		return person;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

}
