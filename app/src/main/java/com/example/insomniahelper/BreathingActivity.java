package com.example.insomniahelper;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BreathingActivity extends AppCompatActivity {

    private CardView circle;
    private TextView tvInstruction;
    private Handler handler;
    private boolean isInhaling = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        circle = findViewById(R.id.circle);
        tvInstruction = findViewById(R.id.tvInstruction);
        handler = new Handler();

        startBreathing();
    }

    private void startBreathing() {
        Runnable breathingRunnable = new Runnable() {
            @Override
            public void run() {
                if (isInhaling) {
                    tvInstruction.setText("INHALE... 🫁");
                    tvInstruction.setTextColor(getColor(android.R.color.holo_green_light));
                    animateCircle(200, 400, 4000);
                } else {
                    tvInstruction.setText("EXHALE... 🌬️");
                    tvInstruction.setTextColor(getColor(android.R.color.holo_blue_light));
                    animateCircle(400, 200, 4000);
                }
                isInhaling = !isInhaling;
                handler.postDelayed(this, 4000);
            }
        };

        handler.post(breathingRunnable);
    }

    private void animateCircle(int fromSize, int toSize, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(fromSize, toSize);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            int size = (int) animation.getAnimatedValue();
            circle.getLayoutParams().width = size;
            circle.getLayoutParams().height = size;
            circle.requestLayout();
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}