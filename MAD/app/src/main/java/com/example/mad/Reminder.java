package com.example.mad;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class Reminder extends AppCompatActivity {

    private ListView healthLogList;
    private Button addHealthLogBtn;
    private Button confirmTaskBtn, snoozeTaskBtn;
    private TextView notificationStatus;

    private ArrayList<String> reminders;
    private ArrayAdapter<String> adapter;
    private AlarmManager alarmManager;
    private Calendar calendar;

    private PendingIntent alarmIntent; // For canceling the alarm

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        healthLogList = findViewById(R.id.health_log_list);
        addHealthLogBtn = findViewById(R.id.add_health_log_btn);
        confirmTaskBtn = findViewById(R.id.confirm_task_btn);
        snoozeTaskBtn = findViewById(R.id.snooze_task_btn);
        notificationStatus = findViewById(R.id.notifications_status);

        reminders = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reminders);
        healthLogList.setAdapter(adapter);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Add New Alarm
        addHealthLogBtn.setOnClickListener(v -> showTimePicker());

        // Task Confirmation: Stop Alarm and Show Notification
        confirmTaskBtn.setOnClickListener(v -> {
            if (alarmIntent != null) {
                alarmManager.cancel(alarmIntent); // Stop the alarm
                Toast.makeText(this, "Alarm Stopped!", Toast.LENGTH_SHORT).show();

                // Show Task Completion Notification
                showNotification("Reminder", "Task Completed Successfully!");

                // Update UI
                notificationStatus.setVisibility(View.VISIBLE);
                notificationStatus.setText("Task Completed!");
            } else {
                Toast.makeText(this, "No active alarm to stop.", Toast.LENGTH_SHORT).show();
            }
        });

        // Snooze Task
        snoozeTaskBtn.setOnClickListener(v -> snoozeAlarm());
    }

    private void showTimePicker() {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(), "timePicker");

        picker.addOnPositiveButtonClickListener(view -> {
            int hour = picker.getHour();
            int minute = picker.getMinute();

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            String reminder = "Alarm set for: " + hour + ":" + String.format("%02d", minute);
            reminders.add(reminder);
            adapter.notifyDataSetChanged();

            setAlarm();
        });
    }

    private void setAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        Toast.makeText(this, "Alarm Set!", Toast.LENGTH_SHORT).show();
    }

    private void snoozeAlarm() {
        if (calendar != null) {
            calendar.add(Calendar.MINUTE, 10); // Snooze for 10 minutes
            setAlarm();
            Toast.makeText(this, "Alarm Snoozed for 10 minutes!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No alarm to snooze!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "pet_reminder_channel")
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, builder.build());
    }
}
