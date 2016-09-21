package com.josegd.siplaychallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivityFragment extends Fragment
		                            implements PlayersAdapter.ClickListener,
		                                       SIPlayHTTPClient.RequestResponseHandler {

	private static final int GRID_SPAN = 3;

	private SIPlayAPIService api;
	private TextView tvTeamName;
	private RecyclerView mRecyclerView;

	public MainActivityFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvTeamName = (TextView) getActivity().findViewById(R.id.team_name);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_SPAN));
		mRecyclerView.setAdapter(new PlayersAdapter(getActivity(), null, 0, null));
		api = new SIPlayAPIService(getActivity());
		api.loadTeamData(tvTeamName, mRecyclerView);
		api.loadPlayers(mRecyclerView, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.team_grid);
		return view;
	}

	@Override
	public void onPlayerClick(Player player, int teamId) {
		new SIPlayHTTPClient().doTappedPlayerRequest(getActivity(), player, teamId, this);
	}

	@Override
	public void onRequestResponseReceived(String msg) {
		new AlertDialog.Builder(getActivity())
				  .setTitle(R.string.app_name)
				  .setMessage(msg)
				  .setPositiveButton(android.R.string.ok, null)
				  .create()
				  .show();
	}

}
