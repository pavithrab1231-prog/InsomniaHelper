package com.example.insomniahelper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RelaxActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnRain, btnOcean, btnWhiteNoise, btnStop, btnBreathing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        btnRain = findViewById(R.id.btnRain);
        btnOcean = findViewById(R.id.btnOcean);
        btnWhiteNoise = findViewById(R.id.btnWhiteNoise);
        btnStop = findViewById(R.id.btnStop);
        btnBreathing = findViewById(R.id.btnBreathing);

        btnRain.setOnClickListener(v -> playSound(R.raw.rain, "Rain Sounds 🌧️"));
        btnOcean.setOnClickListener(v -> playSound(R.raw.ocean, "Ocean Waves 🌊"));
        btnWhiteNoise.setOnClickListener(v -> playSound(R.raw.whitenoise, "White Noise 🎶"));
        btnStop.setOnClickListener(v -> stopSound());

        btnBreathing.setOnClickListener(v -> {
            Intent intent = new Intent(RelaxActivity.this, BreathingActivity.class);
            startActivity(intent);
        });
    }

    private void playSound(int soundResource, String soundName) {
        // Stop any currently playing sound
        stopSound();

        // Create and start new media player
        mediaPlayer = MediaPlayer.create(this, soundResource);
        mediaPlayer.setLooping(true); // Loop the sound continuously
        mediaPlayer.start();

        Toast.makeText(this, "🎵 Playing: " + soundName, Toast.LENGTH_SHORT).show();
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Toast.makeText(this, "⏹️ Sound Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}