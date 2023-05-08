package com.example.a2dspacegame.spaceGame.utilities;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.a2dspacegame.spaceGame.activities.GameActivity;


public class SignalGenerator {
    private static SignalGenerator instance;
    private Context context;

    private SignalGenerator(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
        }
    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public static void makeToast(Context context, int lifeCounter) {
        if (lifeCounter == -1)
            Toast.makeText(context, "You got +10 points!", Toast.LENGTH_LONG).show();
        switch (lifeCounter) {
            case 1:
                Toast.makeText(context, "2 lives left", Toast.LENGTH_LONG).show();
                break;
            case 0:
                Toast.makeText(context, "1 life left", Toast.LENGTH_LONG).show();
                break;
            case -1:
                Toast.makeText(context, "game over!", Toast.LENGTH_LONG).show();
                GameActivity.resetGame();
        }
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
