package com.josegd.siplaychallenge;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

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
		mRecyclerView.setAdapter(new PlayersAdapter(getActivity(), null));
		api = new SIPlayAPIService();
		api.loadTeamData(tvTeamName, mRecyclerView);
		api.loadPlayers(getActivity(), mRecyclerView);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.team_grid);
		return view;
	}

}
