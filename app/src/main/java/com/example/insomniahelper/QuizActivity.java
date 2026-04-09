package com.example.insomniahelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rgQ1, rgQ2, rgQ3, rgQ4, rgQ5, rgQ6;
    private Button btnSubmit;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rgQ1 = findViewById(R.id.rgQ1);
        rgQ2 = findViewById(R.id.rgQ2);
        rgQ3 = findViewById(R.id.rgQ3);
        rgQ4 = findViewById(R.id.rgQ4);
        rgQ5 = findViewById(R.id.rgQ5);
        rgQ6 = findViewById(R.id.rgQ6);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            if (calculateScore()) {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            } else {
                Toast.makeText(QuizActivity.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean calculateScore() {
        score = 0;

        int q1 = rgQ1.getCheckedRadioButtonId();
        int q2 = rgQ2.getCheckedRadioButtonId();
        int q3 = rgQ3.getCheckedRadioButtonId();
        int q4 = rgQ4.getCheckedRadioButtonId();
        int q5 = rgQ5.getCheckedRadioButtonId();
        int q6 = rgQ6.getCheckedRadioButtonId();

        if (q1 == -1 || q2 == -1 || q3 == -1 || q4 == -1 || q5 == -1 || q6 == -1) {
            return false;
        }

        // Q1: Time to fall asleep
        RadioButton rb1 = findViewById(q1);
        String text1 = rb1.getText().toString();
        if (text1.contains("More than 60")) score += 3;
        else if (text1.contains("30-60")) score += 2;
        else if (text1.contains("15-30")) score += 1;

        // Q2: Phone usage
        RadioButton rb2 = findViewById(q2);
        String text2 = rb2.getText().toString();
        if (text2.equals("Always")) score += 3;
        else if (text2.equals("Often")) score += 2;
        else if (text2.equals("Sometimes")) score += 1;

        // Q3: Stress level
        RadioButton rb3 = findViewById(q3);
        String text3 = rb3.getText().toString();
        if (text3.equals("Very High")) score += 3;
        else if (text3.equals("High")) score += 2;
        else if (text3.equals("Moderate")) score += 1;

        // Q4: Sleep hours
        RadioButton rb4 = findViewById(q4);
        String text4 = rb4.getText().toString();
        if (text4.equals("Less than 5 hours")) score += 3;
        else if (text4.equals("5-6 hours")) score += 2;
        else if (text4.equals("6-7 hours")) score += 1;

        // Q5: Wake up tired
        RadioButton rb5 = findViewById(q5);
        String text5 = rb5.getText().toString();
        if (text5.equals("Always")) score += 3;
        else if (text5.equals("Often")) score += 2;
        else if (text5.equals("Sometimes")) score += 1;

        // Q6: Wake up at night
        RadioButton rb6 = findViewById(q6);
        String text6 = rb6.getText().toString();
        if (text6.equals("Always")) score += 3;
        else if (text6.equals("Often")) score += 2;
        else if (text6.equals("Sometimes")) score += 1;

        return true;
    }
}