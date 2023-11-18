package com.rushil.codsoft_alarm_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int hour, minuteOfHour;
    private List<Alarm> alarms;
    private AlarmAdapter alarmAdapter;
    private static final String PREFS_NAME = "AlarmPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker timePicker = findViewById(R.id.timePicker);
        FloatingActionButton btnTimer = findViewById(R.id.btnTimer);
        ListView listView = findViewById(R.id.listView);

        alarms = loadAlarms();

        alarmAdapter = new AlarmAdapter(this, alarms);
        listView.setAdapter(alarmAdapter);

        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            hour = hourOfDay;
            minuteOfHour = minute;
        });


        btnTimer.setOnClickListener(v -> {
            Alarm alarm = new Alarm(hour, minuteOfHour, true);
            alarms.add(alarm);
            saveAlarms(alarms);
            alarmAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Alarm Set " + hour + " : " + minuteOfHour, Toast.LENGTH_SHORT).show();
            setTimer();
            notification();
        });
    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Reminders";
            String description = "Hey, Wake Up!!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("Notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setTimer() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        for (Alarm alarm : alarms) {
            if (alarm.isEnabled()) {
                Intent i = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, PendingIntent.FLAG_IMMUTABLE);

                Calendar cal_alarm = Calendar.getInstance();
                Calendar cal_now = Calendar.getInstance();
                cal_alarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                cal_alarm.set(Calendar.MINUTE, alarm.getMinute());
                cal_alarm.set(Calendar.SECOND, 0);

                if (cal_alarm.before(cal_now)) {
                    cal_alarm.add(Calendar.DATE, 1);
                }

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        }
    }
    private List<Alarm> loadAlarms() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("alarms", null);
        Type type = new TypeToken<List<Alarm>>() {}.getType();

        if (json == null) {
            return new ArrayList<>();
        } else {
            return gson.fromJson(json, type);
        }
    }

    private void saveAlarms(List<Alarm> alarms) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarms);
        editor.putString("alarms", json);
        editor.apply();
    }
}