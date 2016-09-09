package com.josegd.siplaychallenge;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView playerPhoto;
		public TextView playerName;
		public TextView playerJersey;

		public ViewHolder(View itemView) {
			super(itemView);
			playerPhoto  = (ImageView) itemView.findViewById(R.id.player_photo);
         playerName   = (TextView) itemView.findViewById(R.id.player_name);
			playerJersey = (TextView) itemView.findViewById(R.id.player_jersey);
		}

	}

	@Override
	public PlayersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View playerView = inflater.inflate(R.layout.grid_cell, parent, false);
		ViewHolder viewHolder = new ViewHolder(playerView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(PlayersAdapter.ViewHolder holder, int position) {
		// Just show the default cell to start
	}

	@Override
	public int getItemCount() {
		return 20;
	}

}
