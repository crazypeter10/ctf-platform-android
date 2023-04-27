package com.example.platforma_ctf;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private ArrayList<User> leaderboardUsers;

    public LeaderboardAdapter(ArrayList<User> leaderboardUsers) {
        this.leaderboardUsers = leaderboardUsers;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        User user = leaderboardUsers.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvScore.setText(String.valueOf(user.getScore()));
    }

    @Override
    public int getItemCount() {
        return leaderboardUsers.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername;
        public TextView tvScore;

        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_leaderboard_username);
            tvScore = itemView.findViewById(R.id.tv_leaderboard_score);
        }
    }
}
