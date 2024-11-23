package com.example.mad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VetAppoinments extends AppCompatActivity {

    private Button selectAppointmentTimeBtn;
    private Button addAppointmentBtn;
    private Button addMedicalRecordBtn;
    private TextView appointmentTimeLabel;
    private TextView addedAppointmentLabel;
    private EditText medicalRecordInput;
    private GridView medicalHistoryGridView;
    private ArrayList<String> medicalHistoryList;
    private ArrayAdapter<String> medicalHistoryAdapter;

    private String selectedAppointmentTime = ""; // To store selected time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        // Initialize Views
        selectAppointmentTimeBtn = findViewById(R.id.select_appointment_time);
        addAppointmentBtn = findViewById(R.id.add_appointment_btn);
        addMedicalRecordBtn = findViewById(R.id.add_medical_record_btn);
        appointmentTimeLabel = findViewById(R.id.appointment_time_label);
        addedAppointmentLabel = findViewById(R.id.added_appointment_label);
        medicalRecordInput = findViewById(R.id.medical_record_input);
        medicalHistoryGridView = findViewById(R.id.medical_history_gridview);

        // Initialize Medical History List
        medicalHistoryList = new ArrayList<>();
        medicalHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicalHistoryList);
        medicalHistoryGridView.setAdapter(medicalHistoryAdapter);

        // Set onClickListeners
        selectAppointmentTimeBtn.setOnClickListener(v -> showDateTimePicker());
        addAppointmentBtn.setOnClickListener(v -> addAppointment());
        addMedicalRecordBtn.setOnClickListener(v -> addMedicalRecord());
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Time Picker Dialog after selecting the date
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                selectedAppointmentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime());
                appointmentTimeLabel.setText("Selected: " + selectedAppointmentTime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addAppointment() {
        if (!selectedAppointmentTime.isEmpty()) {
            addedAppointmentLabel.setText("New Appointment Scheduled for: " + selectedAppointmentTime);
        } else {
            addedAppointmentLabel.setText("Please select an appointment time first!");
        }
    }

    private void addMedicalRecord() {
        String medicalRecord = medicalRecordInput.getText().toString().trim();
        if (!medicalRecord.isEmpty()) {
            medicalHistoryList.add(medicalRecord);
            medicalHistoryAdapter.notifyDataSetChanged();
            medicalRecordInput.setText("");  // Clear input field after adding
        }
    }
}
