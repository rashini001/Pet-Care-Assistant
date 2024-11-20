package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.adapter.HealthLogAdapter;
import com.example.mad.db.DBHelper;
import com.example.mad.models.HealthLog;

import java.util.ArrayList;
import java.util.List;

public class ActivityTracking extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HealthLogAdapter adapter;
    private List<HealthLog> healthLogs;

    private Button addHealthLogBtn, saveHealthLogBtn, generateReportBtn;
    private EditText logActivityName, logDate, logNotes;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        dbHelper = new DBHelper(this);
        healthLogs = dbHelper.getAllLogs();

        recyclerView = findViewById(R.id.health_log_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HealthLogAdapter(healthLogs);
        recyclerView.setAdapter(adapter);

        logActivityName = findViewById(R.id.log_activity_name);
        logDate = findViewById(R.id.log_date);
        logNotes = findViewById(R.id.log_notes);
        addHealthLogBtn = findViewById(R.id.add_health_log_btn);
        saveHealthLogBtn = findViewById(R.id.save_health_log_btn);
        generateReportBtn = findViewById(R.id.generate_report_btn);

        // Add new health log
        addHealthLogBtn.setOnClickListener(v -> {
            logActivityName.setVisibility(View.VISIBLE);
            logDate.setVisibility(View.VISIBLE);
            logNotes.setVisibility(View.VISIBLE);
            saveHealthLogBtn.setVisibility(View.VISIBLE);
        });

        // Save health log to database
        saveHealthLogBtn.setOnClickListener(v -> saveHealthLog());

        // Generate health report
        generateReportBtn.setOnClickListener(v -> generateHealthReport());
    }

    private void saveHealthLog() {
        String activityName = logActivityName.getText().toString();
        String date = logDate.getText().toString();
        String notes = logNotes.getText().toString();

        if (activityName.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.insertLog(new HealthLog(activityName, date, notes, false));
        healthLogs.clear();
        healthLogs.addAll(dbHelper.getAllLogs());
        adapter.notifyDataSetChanged();

        logActivityName.setText("");
        logDate.setText("");
        logNotes.setText("");
        Toast.makeText(this, "Log saved!", Toast.LENGTH_SHORT).show();
    }

    private void generateHealthReport() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        StringBuilder report = new StringBuilder("Health Activity Report:\n");
        for (HealthLog log : healthLogs) {
            report.append("Activity: ").append(log.getActivityName())
                    .append(", Date: ").append(log.getDate())
                    .append(", Done: ").append(log.isDone() ? "Yes" : "No")
                    .append("\n");
        }

        intent.putExtra(Intent.EXTRA_TEXT, report.toString());
        startActivity(Intent.createChooser(intent, "Share Report"));
    }
}

