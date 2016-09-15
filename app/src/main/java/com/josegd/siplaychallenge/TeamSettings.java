package com.josegd.siplaychallenge;

import com.google.gson.annotations.SerializedName;

public class TeamSettings {

	@SerializedName("HighlightColor")
	private String highlightColor;

	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

}
