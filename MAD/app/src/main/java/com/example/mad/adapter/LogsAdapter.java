package com.example.mad.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.models.DiaryLog;

import java.util.ArrayList;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogViewHolder> {

    private ArrayList<DiaryLog> diaryLogs;

    public LogsAdapter(ArrayList<DiaryLog> diaryLogs) {
        this.diaryLogs = diaryLogs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        DiaryLog log = diaryLogs.get(position);
        holder.logText.setText(log.getText());
    }

    @Override
    public int getItemCount() {
        return diaryLogs.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView logText;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            logText = itemView.findViewById(R.id.log_text);
        }
    }
}

