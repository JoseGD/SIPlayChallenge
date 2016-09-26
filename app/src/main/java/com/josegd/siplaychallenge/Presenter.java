package com.josegd.siplaychallenge;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

	private MainActivityFragment view;
	private SIPlayAPIService apiService;

	public Presenter(MainActivityFragment view, SIPlayAPIService apiService) {
		this.view = view;
		this.apiService = apiService;
	}

	public void loadTeamData() {
		Call<Team> call = apiService.getEndpoints().getTeamData();
		call.enqueue(new Callback<Team>() {
			@Override
			public void onResponse(Call<Team> call, Response<Team> response) {
				if (response.isSuccessful()) {
					view.setTeamName(response.body().getName());
					view.setTeamColor(response.body().getSettings().getHighlightColor());
					view.setPlayerRoster(response.body().getPlayers(), response.body().getTeamId());
				} else {
					view.showUnsuccessfulAPIRequestError(response.raw().code());
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				view.showFailedAPIRequestError(t.getMessage());
			}
		});
	}

	public void showPlayerInfo(Player player, int teamId) {
		OkHttpClient client = new OkHttpClient();
		Request req = SIPlayHTTPClient.getTappedPlayerRequest(player, teamId);
		client.newCall(req).enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(okhttp3.Call call, final IOException e) {
				view.showFailedNetworkRequestError(e.getMessage());
			}

			@Override
			public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
				if (response.isSuccessful()) {
					view.showSuccessfulNetworkRequestResult(response.body().string());
				} else {
					view.showFailedNetworkRequestError(response.code());
				}
			}
		});

	}

}
