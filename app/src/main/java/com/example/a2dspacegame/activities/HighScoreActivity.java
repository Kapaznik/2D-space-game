package com.example.a2dspacegame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2dspacegame.R;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
//here we stopped//
    public void onBackPressed() {
        super.onPause();
        startActivity(new Intent(HighScoreActivity.this, ManuActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);


        // Get the list of high scores from the database
        ArrayList<String> highScores = getHighScores();

        // Display the high scores in the UI
        displayHighScores(highScores);
    }

    /**
     * Retrieves the list of high scores from the database.
     * In this example implementation, the high scores are hard-coded.
     */
    private ArrayList<String> getHighScores() {
        ArrayList<String> highScores = new ArrayList<>();
        highScores.add("John Doe - 100");
        highScores.add("Jane Smith - 95");
        highScores.add("Bob Johnson - 85");
        highScores.add("Alice Brown - 75");
        highScores.add("Joe Black - 50");
        return highScores;
    }

    private void displayHighScores(ArrayList<String> highScores) {
        for (int i = 0; i < highScores.size(); i++) {
            String score = highScores.get(i);
            LayoutInflater inflater = getLayoutInflater();
        }
    }
}