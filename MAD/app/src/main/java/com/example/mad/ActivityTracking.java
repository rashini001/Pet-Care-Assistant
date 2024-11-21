package com.example.mad;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityTracking extends AppCompatActivity {

    private CheckBox checkWalks, checkFeeding, checkVetVisit, checkMedications;
    private EditText inputWeight, inputVaccinationDate, inputHealthIssues;
    private Button saveManualDataBtn, generateReportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Initialize views
        checkWalks = findViewById(R.id.check_walks);
        checkFeeding = findViewById(R.id.check_feeding);
        checkVetVisit = findViewById(R.id.check_vet_visit);
        checkMedications = findViewById(R.id.check_medications);

        inputWeight = findViewById(R.id.input_weight);
        inputVaccinationDate = findViewById(R.id.input_vaccination_date);
        inputHealthIssues = findViewById(R.id.input_health_issues);

        saveManualDataBtn = findViewById(R.id.save_manual_data_btn);
        generateReportBtn = findViewById(R.id.generate_report_btn);

        // Set up DatePicker for vaccination date
        inputVaccinationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Save manual data
        saveManualDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveManualData();
            }
        });

        // Generate health report
        generateReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateHealthReport();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        inputVaccinationDate.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveManualData() {
        String weight = inputWeight.getText().toString();
        String vaccinationDate = inputVaccinationDate.getText().toString();
        String healthIssues = inputHealthIssues.getText().toString();

        if (weight.isEmpty() || vaccinationDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
        // Save data to the database or handle logic here
    }

    private void generateHealthReport() {
        StringBuilder report = new StringBuilder();
        report.append("Pet Health Activity Report\n\n");

        // Checklist Results
        report.append("Walks Completed: ").append(checkWalks.isChecked() ? "Yes" : "No").append("\n");
        report.append("Feeding Done: ").append(checkFeeding.isChecked() ? "Yes" : "No").append("\n");
        report.append("Vet Visit: ").append(checkVetVisit.isChecked() ? "Yes" : "No").append("\n");
        report.append("Medications Administered: ").append(checkMedications.isChecked() ? "Yes" : "No").append("\n");

        // Manual Input Data
        String weight = inputWeight.getText().toString();
        String vaccinationDate = inputVaccinationDate.getText().toString();
        String healthIssues = inputHealthIssues.getText().toString();

        if (!weight.isEmpty()) {
            report.append("Weight: ").append(weight).append(" kg\n");
        }

        if (!vaccinationDate.isEmpty()) {
            report.append("Vaccination Date: ").append(vaccinationDate).append("\n");
        }

        if (!healthIssues.isEmpty()) {
            report.append("Health Issues: ").append(healthIssues).append("\n");
        }

        // Display report as a toast (you can modify this to display it in a text view or a dialog box)
        Toast.makeText(this, report.toString(), Toast.LENGTH_LONG).show();

        // You can also log it or save to a file here if needed
    }
}
