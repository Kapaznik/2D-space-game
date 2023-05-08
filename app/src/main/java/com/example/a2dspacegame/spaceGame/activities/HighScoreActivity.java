package com.example.a2dspacegame.spaceGame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2dspacegame.R;
import com.example.a2dspacegame.spaceGame.Models.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {
    //here we stopped//
    public void onBackPressed() {
        super.onPause();
        startActivity(new Intent(HighScoreActivity.this, ManuActivity.class));
        finishAffinity();
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_high_scores);
            Bundle bundle = getIntent().getBundleExtra("records");
            String json = bundle.getString("records");
            Log.i("JSON", json);
            Log.i("mery","anhel");
        }
}