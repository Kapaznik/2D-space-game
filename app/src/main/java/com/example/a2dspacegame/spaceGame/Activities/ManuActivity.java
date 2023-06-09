package com.example.a2dspacegame.spaceGame.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a2dspacegame.R;
import com.example.a2dspacegame.spaceGame.Models.RecordList;
import com.example.a2dspacegame.spaceGame.Utilities.MySPv3;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

public class ManuActivity extends AppCompatActivity {

    private SwitchMaterial gameModeSwitch;

    private boolean isFastButtonClicked = false;
    private boolean isSlowButtonClicked = false;

    private TextInputEditText name;

    private String nameString = "";

    private RecordList records;

    public ManuActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fromJSON = MySPv3.getInstance2(ManuActivity.this).getString("MY_DB", "");
        records = new Gson().fromJson(fromJSON, RecordList.class);
        if (records == null)
            records = new RecordList();

        final String GAME_MODE = "GAME_MODE";
        final String SPEED = "SPEED";
        final String NAME = "NAME";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        // Get references to all the views in the layout
        MaterialButton startGameButton = findViewById(R.id.main_BTN_startGame);
        MaterialButton fastButton = findViewById(R.id.main_BTN_fast);
        MaterialButton slowButton = findViewById(R.id.main_BTN_slow);
        gameModeSwitch = findViewById(R.id.main_TGL_gameMode);
        MaterialButton highScores = findViewById(R.id.main_BTN_highScores);
        name = findViewById(R.id.list_ET_name);


        // Set the background of the layout
        ConstraintLayout layout = findViewById(R.id.manu_activity);
        layout.setBackground(ContextCompat.getDrawable(this, R.drawable.manu_background));

        // Add OnClickListener to the fastButton
        fastButton.setOnClickListener(v -> {
            isFastButtonClicked = true;
            isSlowButtonClicked = false;
        });

        // Add OnClickListener to the slowButton
        slowButton.setOnClickListener(v -> {
            isSlowButtonClicked = true;
            isFastButtonClicked = false;
        });

        Intent gameIntent = new Intent(ManuActivity.this, GameActivity.class);
        startGameButton.setOnClickListener(v -> {
            if (gameModeSwitch.isChecked())
                gameIntent.putExtra(GAME_MODE,"Sensors");
            else
                gameIntent.putExtra(GAME_MODE,"Buttons");

            if (isFastButtonClicked) {
                gameIntent.putExtra(SPEED, "FAST");
            } else if (isSlowButtonClicked) {
                gameIntent.putExtra(SPEED, "SLOW");
            }
            nameString = Objects.requireNonNull(name.getText()).toString();
            gameIntent.putExtra(NAME, nameString);
            startActivity(gameIntent);


        });
        highScores.setOnClickListener(v -> {
            Intent intent = new Intent(ManuActivity.this, HighScoreActivity.class);
            Bundle bundle = new Bundle();
            String json = new Gson().toJson(records);
            bundle.putString("records", json);
            intent.putExtra("records", bundle);
            MySPv3.getInstance2(ManuActivity.this).putString("MY_DB", json);
            startActivity(intent);
        });
    }
}