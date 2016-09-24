package com.josegd.siplaychallenge;

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
					view.showUnsuccessfulRequestError(response.raw().code());
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				view.showFailedRequestError(t.getMessage());
			}
		});
	}

}
