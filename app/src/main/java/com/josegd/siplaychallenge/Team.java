package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class Team {

	@SerializedName("Name")
	private String name;

	@SerializedName("Settings")
	private TeamSettings settings;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public TeamSettings getSettings() {
		return settings;
	}

	public void setSettings(TeamSettings settings) {
		this.settings = settings;
	}

}
