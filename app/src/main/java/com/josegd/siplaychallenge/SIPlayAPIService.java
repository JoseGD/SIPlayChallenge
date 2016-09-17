package com.josegd.siplaychallenge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class SIPlayAPIService {

	private static final String BASE_URL = "http://iscoresports.com/bcl/challenge/";

	private SIPlayEndpoints mEndpoints;
	private Team mTeamResponse;

	public SIPlayAPIService() {
		Retrofit retrofit = new Retrofit.Builder()
				                     .baseUrl(BASE_URL)
				                     .addConverterFactory(GsonConverterFactory.create())
				                     .build();
		mEndpoints = retrofit.create(SIPlayEndpoints.class);
	}

	public void loadTeamData(final View teamNameTV, final View teamRosterRV) {
		Call<Team> call = mEndpoints.getTeamData();
		call.enqueue(new Callback<Team>() {
			@Override
			public void onResponse(Call<Team> call, Response<Team> response) {
				if (response.isSuccessful()) {
					mTeamResponse = response.body();
					((TextView) teamNameTV).setText(mTeamResponse.getName());
					int color = Color.parseColor("#" + mTeamResponse.getSettings().getHighlightColor());
					teamRosterRV.setBackgroundColor(color);
				} else {
					Log.d("challenge", "Unsuccessful response loading team data - HTTP error code " + response.raw().code());
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				mTeamResponse = null;
				Log.d("challenge", "Failed loading team data because " + t.getMessage());
			}
		});
	}

	public void loadPlayers(final Context context, final RecyclerView teamRosterRV) {
		Call<Team> call = mEndpoints.getTeamData();
		call.enqueue(new Callback<Team>() {
			@Override
			public void onResponse(Call<Team> call, Response<Team> response) {
				if (response.isSuccessful()) {
					PlayersAdapter pa = new PlayersAdapter(context, response.body().getPlayers());
					teamRosterRV.setAdapter(pa);
				} else {
					Log.d("challenge", "Unsuccessful response loading players - HTTP error code " + response.raw().code());
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				Log.d("challenge", "Failed loading players because " + t.getMessage());
			}
		});
	}

	public interface SIPlayEndpoints {

		@GET("team.json")
	   Call<Team> getTeamData();

	}

}
