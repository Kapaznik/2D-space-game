package com.example.a2dspacegame;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class GameUtils {

    public static void makeToast(Context context, int lifeCounter) {
        switch (lifeCounter) {
            case 2:
                Toast.makeText(context, "2 lives left", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(context, "1 life left", Toast.LENGTH_LONG).show();
                break;
            case 0:
                Toast.makeText(context, "game over!", Toast.LENGTH_LONG).show();
                MainActivity.resetGame();
        }
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
