package com.example.mad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class AlarmReceiver extends BroadcastReceiver {

    private static MediaPlayer mediaPlayer; // To stop alarm sound

    @Override
    public void onReceive(Context context, Intent intent) {
        // Vibrate the phone
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(2000); // Vibrate for 2 seconds
        }

        // Play an alarm sound
        mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Stop the alarm sound when the Confirm button is clicked
    public static void stopAlarmSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
