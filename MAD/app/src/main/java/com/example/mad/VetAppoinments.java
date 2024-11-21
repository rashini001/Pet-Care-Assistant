package com.example.mad;

import static com.example.mad.R.layout.activity_vet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VetAppoinments extends AppCompatActivity {

    private Button selectAppointmentTimeBtn;
    private Button addAppointmentBtn;
    private Button addMedicalRecordBtn;
    private Button viewMedicalHistoryBtn;
    private TextView appointmentTimeLabel;
    private EditText medicalRecordInput;
    private GridView medicalHistoryGridView;
    private ArrayList<String> medicalHistoryList;
    private ArrayAdapter<String> medicalHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        selectAppointmentTimeBtn = findViewById(R.id.select_appointment_time);
        addAppointmentBtn = findViewById(R.id.add_appointment_btn);
        addMedicalRecordBtn = findViewById(R.id.add_medical_record_btn);
        viewMedicalHistoryBtn = findViewById(R.id.view_history_btn);
        appointmentTimeLabel = findViewById(R.id.appointment_time_label);
        medicalRecordInput = findViewById(R.id.medical_record_input);
        medicalHistoryGridView = findViewById(R.id.medical_history_gridview);

        medicalHistoryList = new ArrayList<>();
        medicalHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicalHistoryList);
        medicalHistoryGridView.setAdapter(medicalHistoryAdapter);

        // Existing appointment functions
        selectAppointmentTimeBtn.setOnClickListener(v -> showDateTimePicker());
        addAppointmentBtn.setOnClickListener(v -> addAppointment());
        addMedicalRecordBtn.setOnClickListener(v -> addMedicalRecord());
        viewMedicalHistoryBtn.setOnClickListener(v -> viewMedicalHistory());
    }

    // Appointment scheduling with reminders (existing functionality)
    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                scheduleReminder(calendar.getTimeInMillis());
                appointmentTimeLabel.setText("Scheduled for: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime()));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void scheduleReminder(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AppointmentReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    private void addAppointment() {
        // Logic to add new appointment to the list
        appointmentTimeLabel.setText("Appointment Added");
    }

    // New functionality for medical records
    private void addMedicalRecord() {
        String medicalRecord = medicalRecordInput.getText().toString().trim();
        if (!medicalRecord.isEmpty()) {
            medicalHistoryList.add(medicalRecord);
            medicalHistoryAdapter.notifyDataSetChanged();
            medicalRecordInput.setText("");  // Clear input field after adding
        }
    }

    private void viewMedicalHistory() {
        // Logic to view or process medical history
    }
}
