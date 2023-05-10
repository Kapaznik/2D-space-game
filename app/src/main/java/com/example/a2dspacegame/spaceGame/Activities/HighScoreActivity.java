package com.example.a2dspacegame.spaceGame.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2dspacegame.spaceGame.Fragments.ListFragment;
import com.example.a2dspacegame.spaceGame.Fragments.MapFragment;
import com.example.a2dspacegame.R;



public class HighScoreActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;



    public void onBackPressed() {
        super.onPause();
        startActivity(new Intent(HighScoreActivity.this, ManuActivity.class));
        finish();
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_high_scores);
            initFragments();
            beginTransactions();

        }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
    }
}