package com.example.insomniahelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("SCORE", 0);

        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvSeverity = findViewById(R.id.tvSeverity);
        TextView tvMessage = findViewById(R.id.tvMessage);
        Button btnGetTips = findViewById(R.id.btnGetTips);

        tvScore.setText("Sleep Score: " + score + "/18");

        String severity;
        String message;

        if (score <= 7) {
            severity = "Mild Insomnia";
            message = "Your sleep issues are mild. Small changes can help!";
            tvSeverity.setTextColor(Color.parseColor("#81C784"));
        } else if (score <= 11) {
            severity = "Moderate Insomnia";
            message = "You have moderate sleep issues. Consider lifestyle changes.";
            tvSeverity.setTextColor(Color.parseColor("#FFB74D"));
        } else {
            severity = "Severe Insomnia";
            message = "Your sleep issues are severe. Consider consulting a doctor.";
            tvSeverity.setTextColor(Color.parseColor("#E57373"));
        }

        tvSeverity.setText(severity);
        tvMessage.setText(message);

        btnGetTips.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, TipsActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        });
    }
}