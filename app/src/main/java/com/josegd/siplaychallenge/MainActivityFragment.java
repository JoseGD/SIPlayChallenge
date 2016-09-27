package com.josegd.siplaychallenge;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivityFragment extends Fragment
		                            implements PlayersAdapter.ClickListener {

	private static final int GRID_SPAN = 3;
	private final static int SBAR_LENGTH = Snackbar.LENGTH_LONG;

	private TextView tvTeamName;
	private RecyclerView rvPlayerRoster;

	private SIPlayAPIService api;
	private Presenter presenter;

	public MainActivityFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpUIControls();
		api = new SIPlayAPIService();
		presenter = new Presenter(this, api);
		presenter.loadTeamDataRx();
		//presenter.loadTeamData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, container, false);
		rvPlayerRoster = (RecyclerView) view.findViewById(R.id.team_grid);
		return view;
	}

	@Override
	public void onPlayerClick(Player player, int teamId) {
		presenter.showPlayerInfo(player, teamId);
	}

	// REST API actions -----------------------------------------------------------------------------

	protected void setTeamName(String name) {
		tvTeamName.setText(name);
	}

	protected void setTeamColor(String colorStr) {
		rvPlayerRoster.setBackgroundColor(Color.parseColor("#" + colorStr));
	}

	protected void setPlayerRoster(List<Player> roster, int teamId) {
		PlayersAdapter pa = new PlayersAdapter(getActivity(), roster, teamId, this);
		rvPlayerRoster.setAdapter(pa);
	}

	protected void showUnsuccessfulAPIRequestError(int code) {
		Snackbar.make(tvTeamName, String.format(getString(R.string.unsuccessful_response), code), SBAR_LENGTH).show();
	}

	protected void showFailedAPIRequestError(String msg) {
		Snackbar.make(tvTeamName, String.format(getString(R.string.failed_loading), msg), SBAR_LENGTH).show();
	}

	// Network call actions -------------------------------------------------------------------------

	protected void showSuccessfulNetworkRequestResult(final String msg) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				showNetworkRequestResultDialog(msg);
			}
		});
	}

	protected void showFailedNetworkRequestError(final String msg) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				showNetworkRequestResultDialog(String.format(getString(R.string.failed_accessing), msg));
			}
		});
	}

	protected void showFailedNetworkRequestError(final int code) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				showNetworkRequestResultDialog(String.format(getString(R.string.unsuccessful_response_2), code));
			}
		});
	}

	// ----------------------------------------------------------------------------------------------

	private void setUpUIControls() {
		tvTeamName = (TextView) getActivity().findViewById(R.id.team_name);
		rvPlayerRoster.setHasFixedSize(true);
		rvPlayerRoster.setLayoutManager(new GridLayoutManager(getActivity(), GRID_SPAN));
		rvPlayerRoster.setAdapter(new PlayersAdapter(getActivity(), null, 0, null));
	}

	private void showNetworkRequestResultDialog(String msg) {
		new AlertDialog.Builder(getActivity())
				  .setTitle(R.string.app_name)
				  .setMessage(msg)
				  .setPositiveButton(android.R.string.ok, null)
				  .create()
				  .show();
	}

}
