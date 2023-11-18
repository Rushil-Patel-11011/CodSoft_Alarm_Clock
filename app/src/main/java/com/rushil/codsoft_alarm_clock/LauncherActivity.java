package com.rushil.codsoft_alarm_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class LauncherActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextView currentDate, currentTime;
    Handler handler;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        fab = findViewById(R.id.btnADDALARM);
        currentTime = findViewById(R.id.textViewTime);
        currentDate = findViewById(R.id.textViewDate);

        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        currentDate.setText(dateFormat.format(new Date()));

        fab.setOnClickListener(view -> {
            Intent i = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(i);
        });

        handler = new Handler();
        updateCurrentTime();
    }

    // Method to update the current time
    private void updateCurrentTime() {
        currentTime.setText(timeFormat.format(new Date()));
        handler.postDelayed(this::updateCurrentTime, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
