package com.josegd.siplaychallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

	private Context mContext;
	private List<Player> mPlayerList;
	private int mTeamId;
	private ClickListener mClickListener;

	public PlayersAdapter(Context context, List<Player> playerList, int teamId, ClickListener clickListenerImpl) {
		mContext = context;
		mPlayerList = playerList;
		mTeamId = teamId;
		mClickListener = clickListenerImpl;
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
		if (mPlayerList != null) {
			holder.player = mPlayerList.get(position);
			holder.teamId = mTeamId;
			Player p = holder.player;
			Picasso.with(mContext)
					 .load(p.getPerson().getImageUrl())
					 .into(holder.playerPhoto);
			holder.playerFirstName.setText(p.getPerson().getFirstName());
			holder.playerLastName.setText(p.getPerson().getLastName());
			holder.playerJersey.setText(p.getJerseyNumber());
		}
	}

	@Override
	public int getItemCount() {
		return mPlayerList != null ? mPlayerList.size() : 0   ;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		Player player;
		int teamId;

		ImageView playerPhoto;
		TextView playerFirstName;
		TextView playerLastName;
		TextView playerJersey;

		public ViewHolder(View itemView) {
			super(itemView);
			playerPhoto  = (ImageView) itemView.findViewById(R.id.player_photo);
			playerFirstName = (TextView) itemView.findViewById(R.id.player_fname);
			playerLastName = (TextView) itemView.findViewById(R.id.player_lname);
			playerJersey = (TextView) itemView.findViewById(R.id.player_jersey);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mClickListener != null) {
						mClickListener.onPlayerClick(player, teamId);
					}
				}
			});
		}

	}

	public interface ClickListener {
		void onPlayerClick(Player player, int teamId);
	}

}
