package com.example.insomniahelper;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        int score = getIntent().getIntExtra("SCORE", -1);

        TextView tvTips = findViewById(R.id.tvTips);
        TextView tvDailyTip = findViewById(R.id.tvDailyTip);

        String tips;

        if (score == -1) {
            tips = "💡 GENERAL SLEEP TIPS 💡\n\n" +
                    "• Maintain a consistent sleep schedule\n" +
                    "• Create a relaxing bedtime routine\n" +
                    "• Keep your bedroom dark and cool\n" +
                    "• Avoid caffeine after 2 PM\n" +
                    "• Exercise regularly, but not before bed\n" +
                    "• Stop screen use 1 hour before sleep";
        } else if (score <= 7) {
            tips = "🌙 FOR MILD INSOMNIA 🌙\n\n" +
                    "✅ Reduce screen time before bed\n" +
                    "✅ Set a fixed sleep and wake time\n" +
                    "✅ Try the breathing exercise\n" +
                    "✅ Avoid napping during the day";
        } else if (score <= 11) {
            tips = "⚠️ FOR MODERATE INSOMNIA ⚠️\n\n" +
                    "✅ No caffeine after 12 PM\n" +
                    "✅ No phones in bedroom\n" +
                    "✅ Try 4-7-8 breathing technique\n" +
                    "✅ Consider melatonin (consult doctor)";
        } else {
            tips = "🚨 FOR SEVERE INSOMNIA 🚨\n\n" +
                    "⚠️ Please consult a doctor\n" +
                    "✅ Keep a sleep diary\n" +
                    "✅ Consider CBT-I therapy\n" +
                    "✅ No screens 2 hours before bed";
        }

        tvTips.setText(tips);

        String[] dailyTips = {
                "🌟 Tip: Keep bedroom at 65-68°F (18-20°C)",
                "🌟 Tip: Stop eating 2-3 hours before bedtime",
                "🌟 Tip: Morning sunlight helps regulate sleep",
                "🌟 Tip: Write down worries before bed",
                "🌟 Tip: Consistent wake time is important"
        };

        Random random = new Random();
        tvDailyTip.setText(dailyTips[random.nextInt(dailyTips.length)]);
    }
}