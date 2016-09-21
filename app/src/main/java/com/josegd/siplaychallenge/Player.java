package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class Player {

	@SerializedName("Id")
	private int playerId;

	@SerializedName("Person")
	private Person person;

	@SerializedName("JerseyNumber")
	private String jerseyNumber;

	public int getPlayerId() {
		return playerId;
	}

	public Person getPerson() {
		return person;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

}
