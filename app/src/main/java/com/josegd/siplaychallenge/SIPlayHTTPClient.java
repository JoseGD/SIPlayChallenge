package com.josegd.siplaychallenge;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SIPlayHTTPClient {

	private static final String BASE_URL = "http://iscoresports.com/bcl/challenge/";
	private static final String PHP_RESOURCE = "tapped.php";
	//private static final String TAG = "challenge";

	private Handler mHandler;

	public void doTappedPlayerRequest(final Context context, Player player,
	                                         int teamId, final RequestResponseHandler responseHandler) {
		final Person p = player.getPerson();
		OkHttpClient client = new OkHttpClient();
		HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + PHP_RESOURCE).newBuilder();
		urlBuilder.addQueryParameter("teamid", String.valueOf(teamId));
		urlBuilder.addQueryParameter("playerid", String.valueOf(player.getPlayerId()));
		urlBuilder.addQueryParameter("firstname", p.getFirstName());
		urlBuilder.addQueryParameter("lastname", p.getLastName());
		//urlBuilder.addQueryParameter("fail", "Failed connection for player " + p.getLastName());
		String urlForRequest = urlBuilder.build().toString();
		Request request = new Request.Builder().url(urlForRequest).build();
		mHandler = new Handler(Looper.getMainLooper());
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, final IOException e) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						responseHandler.onRequestResponseReceived(String.format(context.getString(R.string.failed_accessing), e.getMessage()));
					}
				});
				//Log.d(TAG, String.format(context.getString(R.string.failed_accessing), e.getMessage()));
			}

			@Override
			public void onResponse(Call call, final Response response) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						try {
							String resultMsg = response.isSuccessful() ? response.body().string() :
									  String.format(context.getString(R.string.unsuccessful_response_2), response.code());
							responseHandler.onRequestResponseReceived(resultMsg);
						} catch (IOException e) {
							responseHandler.onRequestResponseReceived(e.getMessage());
						}
					}
				});
			}
		});
	}

	public interface RequestResponseHandler {
		void onRequestResponseReceived(String msg);
	}

}
