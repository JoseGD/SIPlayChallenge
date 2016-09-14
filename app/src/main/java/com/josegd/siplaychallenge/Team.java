package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class Team {
	@SerializedName("Name")
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
