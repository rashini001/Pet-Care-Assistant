package com.example.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.adapter.LogsAdapter;
import com.example.mad.models.DiaryLog;

import java.util.ArrayList;

public class Diary extends Activity {

    private EditText diaryEntryInput;
    private Button addDiaryEntryBtn, addPhotoBtn, addVideoBtn;
    private RecyclerView logsRecyclerView;
    private LogsAdapter logsAdapter;
    private ArrayList<DiaryLog> diaryLogs;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        diaryEntryInput = findViewById(R.id.diary_entry_input);
        addDiaryEntryBtn = findViewById(R.id.add_diary_entry_btn);
        addPhotoBtn = findViewById(R.id.add_photo_btn);
        addVideoBtn = findViewById(R.id.add_video_btn);
        logsRecyclerView = findViewById(R.id.logs_recycler_view);

        diaryLogs = new ArrayList<>();
        logsAdapter = new LogsAdapter(diaryLogs);

        logsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logsRecyclerView.setAdapter(logsAdapter);

        addDiaryEntryBtn.setOnClickListener(v -> {
            String entry = diaryEntryInput.getText().toString().trim();
            if (!entry.isEmpty()) {
                DiaryLog log = new DiaryLog(entry, null, null);
                diaryLogs.add(log);
                logsAdapter.notifyDataSetChanged();
                diaryEntryInput.setText("");
                Toast.makeText(Diary.this, "Entry added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Diary.this, "Entry cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        addPhotoBtn.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        addVideoBtn.setOnClickListener(v -> {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Handle image capture
                DiaryLog log = new DiaryLog("Photo Added", data.getData(), null);
                diaryLogs.add(log);
            } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
                // Handle video capture
                DiaryLog log = new DiaryLog("Video Added", null, data.getData());
                diaryLogs.add(log);
            }
            logsAdapter.notifyDataSetChanged();
        }
    }
}
