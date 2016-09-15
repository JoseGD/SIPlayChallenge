package com.josegd.siplaychallenge;

import android.graphics.Color;
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

	public static final String BASE_URL = "http://iscoresports.com/bcl/challenge/";

	private SIPlayEndpoints mEndpoints;
	private Team mResponseData;

	public SIPlayAPIService() {
		Retrofit retrofit = new Retrofit.Builder()
				                     .baseUrl(BASE_URL)
				                     .addConverterFactory(GsonConverterFactory.create())
				                     .build();
		mEndpoints = retrofit.create(SIPlayEndpoints.class);
	}

	public void loadTeamData(final View teamNameView, final View teamGridView) {
		Call<Team> call = mEndpoints.getTeamData();
		call.enqueue(new Callback<Team>() {
			@Override
			public void onResponse(Call<Team> call, Response<Team> response) {
				mResponseData = response.body();
				((TextView) teamNameView).setText(mResponseData.getName());
				int color = Color.parseColor("#" + mResponseData.getSettings().getHighlightColor());
				teamGridView.setBackgroundColor(color);
			}

			@Override
			public void onFailure(Call<Team> call, Throwable t) {
				mResponseData = null;
			}
		});
	}

	public interface SIPlayEndpoints {

		@GET("team.json")
	   Call<Team> getTeamData();

	}

}
