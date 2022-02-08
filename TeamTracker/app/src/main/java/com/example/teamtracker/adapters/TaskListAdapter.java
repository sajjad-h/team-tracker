package com.example.teamtracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {
    private final Context context;
    private List<Task> taskList;

    public TaskListAdapter(Context context, List<Task>taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item_layout, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.setData(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}

class TaskListViewHolder extends RecyclerView.ViewHolder {
    public CardView card;
    public TextView titleTV, descriptionTV;
    public TaskListViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.taskListCardView);
        titleTV = itemView.findViewById(R.id.taskTitleTextView);
        descriptionTV = itemView.findViewById(R.id.taskDescriptionTextView);
    }

    public void setData(Task task) {
        titleTV.setText(task.getTaskTitle());
        descriptionTV.setText(task.getTaskDescription());
    }
}