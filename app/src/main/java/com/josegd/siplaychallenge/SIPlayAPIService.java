package com.josegd.siplaychallenge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
	private static final String TAG = "challenge";

	private SIPlayEndpoints mEndpoints;
	private Team mTeamResponse;
	private Context mContext;

	public SIPlayAPIService(Context context) {
		mContext = context;
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
					Log.d(TAG, String.format(mContext.getString(R.string.unsuccessful_response),
							                   mContext.getString(R.string.team_data), response.raw().code()));
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				mTeamResponse = null;
				Log.d(TAG, String.format(mContext.getString(R.string.failed_loading),
						                   mContext.getString(R.string.team_data), t.getMessage()));
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
					Log.d(TAG, String.format(mContext.getString(R.string.unsuccessful_response),
							                   mContext.getString(R.string.players), response.raw().code()));
				}
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				Log.d(TAG, String.format(mContext.getString(R.string.failed_loading),
											    mContext.getString(R.string.players), t.getMessage()));
			}
		});
	}

	public interface SIPlayEndpoints {
		@GET(JSON_RESOURCE)
	   Call<Team> getTeamData();
	}

}
