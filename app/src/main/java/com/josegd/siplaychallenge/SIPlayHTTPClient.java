package com.josegd.siplaychallenge;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class SIPlayHTTPClient {

	private static final String PHP_RESOURCE = "tapped.php";

	public static Request getTappedPlayerRequest(Player player, int teamId) {
		final Person p = player.getPerson();
		HttpUrl.Builder urlBuilder = HttpUrl.parse(SIPlayAPIService.BASE_URL + PHP_RESOURCE).newBuilder();
		urlBuilder.addQueryParameter("teamid", String.valueOf(teamId));
		urlBuilder.addQueryParameter("playerid", String.valueOf(player.getPlayerId()));
		urlBuilder.addQueryParameter("firstname", p.getFirstName());
		urlBuilder.addQueryParameter("lastname", p.getLastName());
		//urlBuilder.addQueryParameter("fail", "Failed connection for player " + p.getLastName());
		String urlForRequest = urlBuilder.build().toString();
		return new Request.Builder().url(urlForRequest).build();
	}

}
