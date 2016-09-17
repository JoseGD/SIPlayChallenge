package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Team {

	@SerializedName("Name")
	private String name;

	@SerializedName("Players")
	private List<Player> players;

	@SerializedName("Settings")
	private TeamSettings settings;

	public String getName() {
		return name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public TeamSettings getSettings() {
		return settings;
	}

}
