package com.example.a2dspacegame.activities;

import androidx.appcompat.app.AppCompatActivity;
//import static com.example.a2dspacegame.activities.ManuActivity.GAME_MODE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

import com.example.a2dspacegame.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    final static int LANES = 5;
    final static int ROWS = 9;
    private static String GAME_MODE = "";

    private static ImageView[][] AlienView;
    private static ImageView[][] PowerView;
    private static ImageView[] playerView;
    private static ImageView[] LivesView;
    private ImageButton leftArrow, rightArrow;

    private TextView PointsView;

    private static final int LEFT = 0;

    private static final int LEFT_CENTER = 1;
    private static final int CENTER = 2;

    private static final int RIGHT_CENTER = 3;
    private static final int RIGHT = 4;

    private static int spacePos = CENTER;

    public static int lifeCounter = 3;
    private static int score = 0;

    private static int DELAY = 100;

    public int clock = 0;

    public Timer timer;

    Random random = new Random();

    CountDownTimer countDownTimer;

    private String gameMode = "";

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accSensorEventListener;

    public enum DirectionAction { LEFT,RIGHT }

    protected void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        start();
    }

    private void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateGame());
            }
        }, 0, DELAY);
    }

    private void newAlien(int rand) {

        if (AlienView[0][rand].getVisibility() != View.VISIBLE) {
            AlienView[0][rand].setVisibility(View.VISIBLE);
        }
    }

    private void newPower(int rand) {

        if (PowerView[0][rand].getVisibility() != View.VISIBLE) {
            PowerView[0][rand].setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onPause();
        if (countDownTimer!= null) {
            countDownTimer.cancel();
        }
        timer.cancel();
        finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer!= null) {
            countDownTimer.cancel();
        }
        timer.cancel();
        finishAffinity();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();

        // Retrieve game mode from intent and initialize sensors or arrows accordingly
        if (getIntent() != null) {
            String gameMode = getIntent().getStringExtra("GAME_MODE");
            Toast.makeText(getApplicationContext(), "gameMode: " + gameMode, Toast.LENGTH_SHORT).show();
            if (gameMode.equals("Sensors")) {
                initSensor();
            }
            else {
                setArrowsListeners();
            }

        }

        // Retrieve speed from intent and set the delay accordingly
        String speed = getIntent().getStringExtra("SPEED");
        if (speed != null && speed.equals("FAST")) {
            DELAY = 100;
        } else if (speed != null && speed.equals("SLOW")) {
            DELAY = 100;
        }
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            accSensorEventListener = new SensorEventListener() {
                private boolean directionChanged = false;

                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    if (!directionChanged && x <= -1) {
                        DirectionAction action = DirectionAction.LEFT;
                        moveCarBySensors(action);
                        directionChanged = true;
                    } else if (!directionChanged && x >= 1) {
                        DirectionAction action = DirectionAction.RIGHT;
                        moveCarBySensors(action);
                        directionChanged = true;
                    } else if (x > -1 && x < 1) {
                        // Reset directionChanged flag when phone is back to vertical position
                        directionChanged = false;
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
            leftArrow.setVisibility(View.INVISIBLE);
            rightArrow.setVisibility(View.INVISIBLE);
            setPlayerPositions();
            sensorManager.registerListener(accSensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    private void initViews() {
        AlienView = new ImageView[9][5];
        PowerView = new ImageView[9][5];
        playerView = new ImageView[5];
        LivesView = new ImageView[3];
        PointsView = findViewById(R.id.points_view);

        int[] alienIds = { R.id.alien00, R.id.alien10, R.id.alien20, R.id.alien30, R.id.alien40, R.id.alien50, R.id.alien60, R.id.alien70, R.id.alien80 };
        int[] powerIds = { R.id.power00, R.id.power10, R.id.power20, R.id.power30, R.id.power40, R.id.power50, R.id.power60, R.id.power70, R.id.power80 };

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                AlienView[i][j] = findViewById(alienIds[i] + j);
                PowerView[i][j] = findViewById(powerIds[i] + j);
            }
        }

        playerView[LEFT] = findViewById(R.id.ship_left);
        playerView[LEFT_CENTER] = findViewById(R.id.ship_mid_left);
        playerView[CENTER] = findViewById(R.id.ship_mid);
        playerView[RIGHT_CENTER] = findViewById(R.id.ship_mid_right);
        playerView[RIGHT] = findViewById(R.id.ship_right);

        leftArrow = findViewById(R.id.btn_left);
        rightArrow = findViewById(R.id.btn_right);
        LivesView[0] = findViewById(R.id.health1);
        LivesView[1] = findViewById(R.id.health2);
        LivesView[2] = findViewById(R.id.health3);

        resetGame();
    }

    private void setArrowsListeners() {
        setPlayerPositions();

        rightArrow.setOnClickListener(v -> {
            if (spacePos < RIGHT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                spacePos++;
                playerView[spacePos].setVisibility(View.VISIBLE);
                checkHit(1);
            }
        });

        leftArrow.setOnClickListener(v -> {
            if (spacePos > LEFT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                spacePos--;
                playerView[spacePos].setVisibility(View.VISIBLE);
                checkHit(1);
            }
        });
    }

    private static void setPlayerPositions() {
        playerView[RIGHT].setVisibility(View.INVISIBLE);
        playerView[RIGHT_CENTER].setVisibility(View.INVISIBLE);
        playerView[CENTER].setVisibility(View.VISIBLE);
        playerView[LEFT].setVisibility(View.INVISIBLE);
        playerView[LEFT_CENTER].setVisibility(View.INVISIBLE);
    }

    private void moveCarBySensors(DirectionAction action) {
        if (action == DirectionAction.LEFT) {
            if (spacePos < RIGHT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                playerView[++spacePos].setVisibility(View.VISIBLE);
                checkHit(1);
            }
        } else if (action == DirectionAction.RIGHT) {
            if (spacePos > LEFT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                playerView[--spacePos].setVisibility(View.VISIBLE);
                checkHit(1);
            }
        }
    }

    public static void resetGame() {
        score = 0;
        //lifeCounter = 3;
        spacePos = CENTER;

        setPlayerPositions();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < LANES; j++) {
                AlienView[i][j].setVisibility(View.INVISIBLE);
                PowerView[i][j].setVisibility(View.INVISIBLE);
            }
            LivesView[0].setVisibility(View.VISIBLE);
            LivesView[1].setVisibility(View.VISIBLE);
            LivesView[2].setVisibility(View.VISIBLE);
        }
    }
    private void updateGame() {
        int RandomLane = random.nextInt(LANES);
        clock++;

        for (int i = 0; i < LANES; i++) {
            if (AlienView[ROWS-1][i].getVisibility() == View.VISIBLE) {
                AlienView[ROWS-1][i].setVisibility(View.INVISIBLE);
            }
            if (PowerView[ROWS-1][i].getVisibility() == View.VISIBLE) {
                PowerView[ROWS-1][i].setVisibility(View.INVISIBLE);
            }

            for (int j = ROWS-1; j >= 0; j--) {
                if (AlienView[j][i].getVisibility() == View.VISIBLE) {
                    AlienView[j][i].setVisibility(View.INVISIBLE);
                    AlienView[j + 1][i].setVisibility(View.VISIBLE);
                }
                if (PowerView[j][i].getVisibility() == View.VISIBLE) {
                    PowerView[j][i].setVisibility(View.INVISIBLE);
                    PowerView[j + 1][i].setVisibility(View.VISIBLE);
                }
            }


        }
        if (clock % 2 == 0) {
            newAlien(RandomLane);
        }

        if (clock % 19 ==0){
            newPower(RandomLane);
        }
        if (lifeCounter == 0) {
            Intent intent = new Intent(GameActivity.this, HighScoreActivity.class);
            startActivity(intent);
        }
        checkHit(0);
    }

    private void checkHit(int move)
    {
        if (AlienView[ROWS-1][spacePos].getVisibility() == View.VISIBLE
                && playerView[spacePos].getVisibility() == View.VISIBLE) {
            AlienView[ROWS-1][spacePos].setVisibility(View.INVISIBLE);
            lifeCounter--;
            LivesView[lifeCounter].setVisibility(View.INVISIBLE);
            GameUtils.makeToast(this, lifeCounter);
            GameUtils.vibrate(this);
            if (score >=10) {
                score -= 10;
            }
            else{
                score=0;
            }
        }
        if (PowerView[ROWS-1][spacePos].getVisibility() == View.VISIBLE
                && playerView[spacePos].getVisibility() == View.VISIBLE) {
            PowerView[ROWS - 1][spacePos].setVisibility(View.INVISIBLE);
            score += 10;
            GameUtils.makeToast(this, -1);
            GameUtils.vibrate(this);
        }
        else{
            if (move ==0)
                score++;
            String scoreSetText = "" + score;
            PointsView.setText(scoreSetText);
        }
    }

}