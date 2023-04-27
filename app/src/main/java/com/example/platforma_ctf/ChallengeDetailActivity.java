package com.example.platforma_ctf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeDetailActivity extends AppCompatActivity {

    private TextView tvChallengeTitle;
    private TextView tvChallengeDescription;
    private EditText etFlag;
    private Button btnSubmit;
    private String challengeFlag;
    private int challengePoints;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        tvChallengeTitle = findViewById(R.id.tv_challenge_title);
        tvChallengeDescription = findViewById(R.id.tv_challenge_description);
        etFlag = findViewById(R.id.et_flag);
        btnSubmit = findViewById(R.id.btn_submit);
        userManager = new UserManager(this);

        // Retrieve challenge information from the Intent extras
        Intent intent = getIntent();
        int challengeId = intent.getIntExtra("challenge_id", 0);
        String challengeTitle = intent.getStringExtra("challenge_title");
        String challengeDescription = intent.getStringExtra("challenge_description");
        challengeFlag = intent.getStringExtra("challenge_flag");
        challengePoints = intent.getIntExtra("challenge_points", 0);

        // Set challenge information to the views
        tvChallengeTitle.setText(challengeTitle);
        tvChallengeDescription.setText(challengeDescription);
        String username = getIntent().getStringExtra("username");
        // Set up the submit button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String submittedFlag = etFlag.getText().toString().trim();
                Log.d("Answer",challengeFlag ); // Added log statement

                if (submittedFlag.equals(challengeFlag)) {
                    Toast.makeText(ChallengeDetailActivity.this, "Correct flag!", Toast.LENGTH_SHORT).show();
                    Log.d("ChallengeDetailActivity", "onClick: Updating user score");

                    userManager.updateUserScore(username,challengeId,challengePoints);
                    userManager.saveChallengeProgress(username,challengeId);
                    // Log the completed challenge
                    Log.d("ChallengeDetailActivity", "User: " + username + " completed challenge ID: " + challengeId + " and new score is: " +   challengePoints);

                    // Set the result and finish the activity
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ChallengeDetailActivity.this, "Incorrect flag. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }


