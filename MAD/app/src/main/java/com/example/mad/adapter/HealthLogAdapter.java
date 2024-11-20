package com.example.mad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.models.HealthLog;

import java.util.List;

public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.ViewHolder> {

    private final List<HealthLog> healthLogs;

    public HealthLogAdapter(List<HealthLog> healthLogs) {
        this.healthLogs = healthLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthLog log = healthLogs.get(position);
        holder.activityName.setText(log.getActivityName());
        holder.date.setText(log.getDate());
        holder.isDone.setChecked(log.isDone());

        holder.isDone.setOnCheckedChangeListener((buttonView, isChecked) -> log.setDone(isChecked));
    }

    @Override
    public int getItemCount() {
        return healthLogs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityName, date;
        CheckBox isDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.log_activity_name);
            date = itemView.findViewById(R.id.log_date);
            isDone = itemView.findViewById(R.id.log_is_done);
        }
    }
}

