package com.josegd.siplaychallenge;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityFragment extends Fragment {

	private static final int GRID_SPAN = 3;

	private RecyclerView mRecyclerView;
	private PlayersAdapter mAdapter = new PlayersAdapter();

	public MainActivityFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_SPAN));
		mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.team_grid);
		return view;
	}

}
