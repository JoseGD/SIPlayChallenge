package com.josegd.siplaychallenge;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class SIPlayAPIService {

	private static final String BASE_URL = "http://iscoresports.com/bcl/challenge/";
	private static final String JSON_RESOURCE = "team.json";
	private final static int SBAR_LENGTH = Snackbar.LENGTH_LONG;
	//private static final String TAG = "challenge";

	private SIPlayEndpoints mEndpoints;
	private Team mTeamResponse;
	private Context mContext;

	private final String UNSUCCESSFUL_REQ;
	private final String FAILED_REQ ;
	private final String TEAM_STR;
	private final String PLAYERS_STR;

	public SIPlayAPIService(Context context) {
		mContext = context;
		Retrofit retrofit = new Retrofit.Builder()
				                     .baseUrl(BASE_URL)
				                     .addConverterFactory(GsonConverterFactory.create())
				                     .build();
		mEndpoints = retrofit.create(SIPlayEndpoints.class);
		UNSUCCESSFUL_REQ = mContext.getString(R.string.unsuccessful_response);
		FAILED_REQ = mContext.getString(R.string.failed_loading);
		TEAM_STR = mContext.getString(R.string.team_data);
		PLAYERS_STR = mContext.getString(R.string.players);
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
					Snackbar.make(teamNameTV, String.format(UNSUCCESSFUL_REQ, TEAM_STR, response.raw().code()), SBAR_LENGTH).show();
					//Log.d(TAG, String.format(UNSUCCESSFUL_REQ, TEAM_STR, response.raw().code()));
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				mTeamResponse = null;
				Snackbar.make(teamNameTV, String.format(FAILED_REQ, TEAM_STR, t.getMessage()), SBAR_LENGTH).show();
				//Log.d(TAG, String.format(FAILED_REQ, TEAM_STR, t.getMessage()));
			}
		});
	}

	public void loadPlayers(final RecyclerView teamRosterRV, final PlayersAdapter.ClickListener clickL) {
		Call<Team> call = mEndpoints.getTeamData();
		call.enqueue(new Callback<Team>() {
			@Override
			public void onResponse(Call<Team> call, Response<Team> response) {
				if (response.isSuccessful()) {
					PlayersAdapter pa = new PlayersAdapter(mContext, response.body().getPlayers(),
							                                 response.body().getTeamId(), clickL);
					teamRosterRV.setAdapter(pa);
				} else {
					Snackbar.make(teamRosterRV, String.format(UNSUCCESSFUL_REQ, PLAYERS_STR, response.raw().code()), SBAR_LENGTH).show();
					//Log.d(TAG, String.format(UNSUCCESSFUL_REQ, PLAYERS_STR, response.raw().code()));
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				Snackbar.make(teamRosterRV, String.format(FAILED_REQ, PLAYERS_STR, t.getMessage()), SBAR_LENGTH).show();
				//Log.d(TAG, String.format(FAILED_REQ, PLAYERS_STR, t.getMessage()));
			}
		});
	}

	public interface SIPlayEndpoints {

		@GET(JSON_RESOURCE)
	   Call<Team> getTeamData();

	}

}
