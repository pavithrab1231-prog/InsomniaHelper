package com.example.insomniahelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartQuiz = findViewById(R.id.btnStartQuiz);
        Button btnRelax     = findViewById(R.id.btnRelax);
        Button btnTips      = findViewById(R.id.btnTips);
        Button btnJournal   = findViewById(R.id.btnJournal);   // NEW
        Button btnLogout    = findViewById(R.id.btnLogout);

        btnStartQuiz.setOnClickListener(v ->
                startActivity(new Intent(this, QuizActivity.class)));

        btnRelax.setOnClickListener(v ->
                startActivity(new Intent(this, RelaxActivity.class)));

        btnTips.setOnClickListener(v ->
                startActivity(new Intent(this, TipsActivity.class)));

        btnJournal.setOnClickListener(v ->                       // NEW
                startActivity(new Intent(this, JournalActivity.class)));

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
