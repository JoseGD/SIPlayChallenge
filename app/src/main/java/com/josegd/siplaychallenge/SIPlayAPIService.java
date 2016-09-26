package com.josegd.siplaychallenge;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class SIPlayAPIService {

	public static final String BASE_URL = "http://iscoresports.com/bcl/challenge/";

	private static final String JSON_RESOURCE = "team.json";

	private SIPlayEndpoints mEndpoints;

	public SIPlayAPIService() {
		Retrofit retrofit = new Retrofit.Builder()
				                     .baseUrl(BASE_URL)
				                     .addConverterFactory(GsonConverterFactory.create())
				                     .build();
		mEndpoints = retrofit.create(SIPlayEndpoints.class);
	}

	public interface SIPlayEndpoints {

		@GET(JSON_RESOURCE)
	   Call<Team> getTeamData();

	}

	public SIPlayEndpoints getEndpoints() {
		return mEndpoints;
	}

}
