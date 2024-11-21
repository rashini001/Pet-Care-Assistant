package com.example.mad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.ViewHolder> {

    private final ArrayList<String> healthLogs;

    public HealthLogAdapter(ArrayList<String> healthLogs) {
        this.healthLogs = healthLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.logText.setText(healthLogs.get(position));
    }

    @Override
    public int getItemCount() {
        return healthLogs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logText;

        ViewHolder(View itemView) {
            super(itemView);
            logText = itemView.findViewById(android.R.id.text1);
        }
    }
}
