package com.example.insomniahelper;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity {

    private RatingBar rbMood, rbSleep;
    private EditText etNote;
    private Button btnSave;
    private TextView tvTodayDate, tvDaysLogged, tvAvgMood, tvAvgSleep, tvLog;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        dbHelper = DatabaseHelper.getInstance(this);

        rbMood = findViewById(R.id.rbMood);
        rbSleep = findViewById(R.id.rbSleep);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSaveJournal);
        tvTodayDate = findViewById(R.id.tvTodayDate);
        tvDaysLogged = findViewById(R.id.tvDaysLogged);
        tvAvgMood = findViewById(R.id.tvAvgMood);
        tvAvgSleep = findViewById(R.id.tvAvgSleep);
        tvLog = findViewById(R.id.tvLog);

        // Set today's date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String displayDate = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault()).format(new Date());
        tvTodayDate.setText(displayDate);

        // Load existing entry for today
        loadTodayEntry(currentDate);

        // Load stats
        loadStats();

        // Load recent entries
        loadRecentEntries();

        btnSave.setOnClickListener(v -> {
            float mood = rbMood.getRating();
            float sleep = rbSleep.getRating();
            String note = etNote.getText().toString();

            boolean saved = dbHelper.saveJournalEntry(currentDate, (int) mood, (int) sleep, note);

            if (saved) {
                Toast.makeText(JournalActivity.this, "✅ Entry saved successfully!", Toast.LENGTH_SHORT).show();
                loadStats();
                loadRecentEntries();
            } else {
                Toast.makeText(JournalActivity.this, "❌ Failed to save entry", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTodayEntry(String date) {
        Cursor cursor = dbHelper.getTodayEntry(date);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int mood = cursor.getInt(cursor.getColumnIndexOrThrow("mood"));
                int sleepQuality = cursor.getInt(cursor.getColumnIndexOrThrow("sleep_quality"));
                String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));

                rbMood.setRating(mood);
                rbSleep.setRating(sleepQuality);
                etNote.setText(note);
            }
            cursor.close();
        }
    }

    private void loadStats() {
        int daysLogged = dbHelper.getDaysLoggedCount();
        float avgMood = dbHelper.getAverageMood();
        float avgSleep = dbHelper.getAverageSleep();

        tvDaysLogged.setText(String.valueOf(daysLogged));

        if (avgMood > 0) {
            tvAvgMood.setText(String.format("%.1f", avgMood));
        } else {
            tvAvgMood.setText("--");
        }

        if (avgSleep > 0) {
            tvAvgSleep.setText(String.format("%.1f", avgSleep));
        } else {
            tvAvgSleep.setText("--");
        }
    }

    private void loadRecentEntries() {
        Cursor cursor = dbHelper.getAllEntries();
        StringBuilder entries = new StringBuilder();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    int mood = cursor.getInt(cursor.getColumnIndexOrThrow("mood"));
                    int sleep = cursor.getInt(cursor.getColumnIndexOrThrow("sleep_quality"));
                    String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));

                    // Format date for display
                    String displayDate = formatDate(date);

                    entries.append("📅 ").append(displayDate).append("\n");
                    entries.append("   Mood: ").append("⭐".repeat(mood)).append("\n");
                    entries.append("   Sleep: ").append("🛏️".repeat(sleep)).append("\n");
                    if (!note.isEmpty()) {
                        entries.append("   Note: ").append(note).append("\n");
                    }
                    entries.append("\n");

                } while (cursor.moveToNext());
            } else {
                entries.append("No entries yet.\nStart logging above!");
            }
            tvLog.setText(entries.toString());
            cursor.close();
        }
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (Exception e) {
            return date;
        }
    }
}