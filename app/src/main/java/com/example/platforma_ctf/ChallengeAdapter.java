package com.example.platforma_ctf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private ArrayList<Challenge> challenges;
    private OnItemClickListener onItemClickListener;
    private User currentUser;

    public ChallengeAdapter(ArrayList<Challenge> challenges, User currentUser) {
        this.challenges = challenges;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_item, parent, false);
        return new ChallengeViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);
        holder.tvTitle.setText(challenge.getTitle());
        holder.tvPoints.setText(String.valueOf(challenge.getPoints()));

        // Set visibility for the solved image
        if (currentUser != null && currentUser.getSolvedChallenges().contains(challenge.getId())) {
            holder.ivChallengeSolved.setVisibility(View.VISIBLE);
        } else {
            holder.ivChallengeSolved.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvPoints;
        public ImageView ivChallengeSolved;

        public ChallengeViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPoints = itemView.findViewById(R.id.tv_points);
            ivChallengeSolved = itemView.findViewById(R.id.iv_challenge_solved);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
