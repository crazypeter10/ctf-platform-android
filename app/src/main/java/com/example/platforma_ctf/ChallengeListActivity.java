package com.example.platforma_ctf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChallengeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChallengeAdapter challengeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ChallengeManager challengeManager;
    private UserManager userManager;
    private ArrayList<Challenge> challenges;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);

        challengeManager = new ChallengeManager(this);
        userManager = new UserManager(this);
        challenges = challengeManager.getChallenges();
        setUpRecyclerView();

        Button btnLeaderboard = findViewById(R.id.btn_leaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengeListActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        challengeAdapter = new ChallengeAdapter(challenges, userManager.getCurrentUser());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(challengeAdapter);
        String username = getIntent().getStringExtra("username");

        challengeAdapter.setOnItemClickListener(new ChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Challenge challenge = challenges.get(position);
                Intent intent = new Intent(ChallengeListActivity.this, ChallengeDetailActivity.class);
                intent.putExtra("username", username); // Add this line to pass the username

                intent.putExtra("challenge_id", challenge.getId());
                intent.putExtra("challenge_title", challenge.getTitle());
                intent.putExtra("challenge_description", challenge.getDescription());
                intent.putExtra("challenge_flag", challenge.getFlag());
                intent.putExtra("challenge_points", challenge.getPoints());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            challengeAdapter.notifyDataSetChanged();
        }
    }
}
