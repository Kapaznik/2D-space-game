package com.example.a2dspacegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final static int LANES = 3;
    final static int ROWS = 6;

    private static ImageView[][] AlienView;
    private static ImageView[] playerView;
    private static ImageView[] LivesView;
    private ImageButton leftArrow, rightArrow;

    private TextView PointsView;

    private static final int LEFT = 0;
    private static final int CENTER = 1;
    private static final int RIGHT = 2;

    private static int spacePos = CENTER;

    public static int lifeCounter = 3;
    private static int score = 0;

    private static final int DELAY = 500;

    public int clock = 0;

    Random random = new Random();

    CountDownTimer countDownTimer;

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

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        finishAffinity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setArrowsListeners();
    }

    private void initViews() {
        AlienView = new ImageView[6][3];
        playerView = new ImageView[3];
        LivesView = new ImageView[3];
        PointsView = findViewById(R.id.points_view);

        AlienView[0][LEFT] = findViewById(R.id.alien00);
        AlienView[1][LEFT] = findViewById(R.id.alien10);
        AlienView[2][LEFT] = findViewById(R.id.alien20);
        AlienView[3][LEFT] = findViewById(R.id.alien30);
        AlienView[4][LEFT] = findViewById(R.id.alien40);
        AlienView[5][LEFT] = findViewById(R.id.alien50);


        AlienView[0][CENTER] = findViewById(R.id.alien01);
        AlienView[1][CENTER] = findViewById(R.id.alien11);
        AlienView[2][CENTER] = findViewById(R.id.alien21);
        AlienView[3][CENTER] = findViewById(R.id.alien31);
        AlienView[4][CENTER] = findViewById(R.id.alien41);
        AlienView[5][CENTER] = findViewById(R.id.alien51);


        AlienView[0][RIGHT] = findViewById(R.id.alien02);
        AlienView[1][RIGHT] = findViewById(R.id.alien12);
        AlienView[2][RIGHT] = findViewById(R.id.alien22);
        AlienView[3][RIGHT] = findViewById(R.id.alien32);
        AlienView[4][RIGHT] = findViewById(R.id.alien42);
        AlienView[5][RIGHT] = findViewById(R.id.alien52);


        playerView[LEFT] = findViewById(R.id.ship_left);
        playerView[CENTER] = findViewById(R.id.ship_mid);
        playerView[RIGHT] = findViewById(R.id.ship_right);

        leftArrow = findViewById(R.id.btn_left);
        rightArrow = findViewById(R.id.btn_right);
        LivesView[0] = findViewById(R.id.health1);
        LivesView[1] = findViewById(R.id.health2);
        LivesView[2] = findViewById(R.id.health3);

        resetGame();
    }

    private void setArrowsListeners() {
        playerView[RIGHT].setVisibility(View.INVISIBLE);
        playerView[LEFT].setVisibility(View.INVISIBLE);

        rightArrow.setOnClickListener(v -> {
            if (spacePos < RIGHT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                spacePos++;
                playerView[spacePos].setVisibility(View.VISIBLE);
            }
        });
        
        leftArrow.setOnClickListener(v -> {
            if (spacePos > LEFT) {
                playerView[spacePos].setVisibility(View.INVISIBLE);
                spacePos--;
                playerView[spacePos].setVisibility(View.VISIBLE);
            }
        });
    }

    public static void resetGame() {
        score = 0;
        lifeCounter = 3;
        spacePos = CENTER;

        playerView[LEFT].setVisibility(View.INVISIBLE);
        playerView[CENTER].setVisibility(View.VISIBLE);
        playerView[RIGHT].setVisibility(View.INVISIBLE);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < LANES; j++) {
                AlienView[i][j].setVisibility(View.INVISIBLE);
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

            for (int j = ROWS-1; j >= 0; j--) {
                if (AlienView[j][i].getVisibility() == View.VISIBLE) {
                    AlienView[j][i].setVisibility(View.INVISIBLE);
                    AlienView[j + 1][i].setVisibility(View.VISIBLE);
                }
            }
        }
        if (clock % 2 == 0) {
            newAlien(RandomLane);
        }
        checkHit();
    }

        private void checkHit() {
        if (AlienView[ROWS-1][spacePos].getVisibility() == View.VISIBLE
                && playerView[spacePos].getVisibility() == View.VISIBLE) {
            LivesView[--lifeCounter].setVisibility(View.INVISIBLE);
            GameUtils.makeToast(this, lifeCounter);
            GameUtils.vibrate(this);
        }
        else{
            score++;
            //PointsView.setText(String.valueOf(score));
        }
    }

}