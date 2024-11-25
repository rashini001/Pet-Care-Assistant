package com.example.mad;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SoundManager extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_commands);

        // Set up buttons
        Button btnSit = findViewById(R.id.btn_sit);
        Button btnStandUp = findViewById(R.id.btn_standup);
        Button btn_stop = findViewById(R.id.btn_stop);
        Button btnJump = findViewById(R.id.btn_jump);
        Button btnEat = findViewById(R.id.btn_eat);
        Button btnCatch = findViewById(R.id.btn_catch);

        // Set onClickListeners for each button
        btnSit.setOnClickListener(view -> playSound(R.raw.sit));
        btnStandUp.setOnClickListener(view -> playSound(R.raw.standup));
        btn_stop.setOnClickListener(view -> playSound(R.raw.stop));
        btnJump.setOnClickListener(view -> playSound(R.raw.jump));
        btnEat.setOnClickListener(view -> playSound(R.raw.eat));
        btnCatch.setOnClickListener(view -> playSound(R.raw.ctch));
    }

    // Method to play sound
    private void playSound(int soundResourceId) {
        // Release any previous MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Initialize and play the new sound
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();

        // Release MediaPlayer when sound is complete
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer when activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
