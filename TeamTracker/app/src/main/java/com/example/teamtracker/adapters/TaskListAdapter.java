package com.example.teamtracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.util.DateTimeUtil;

import java.util.List;

public class TaskListAdapter extends ListAdapter<Task, TaskListViewHolder> {
    private LiveData<List<Task>> taskList;

    public TaskListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getProjectId().equals(newItem.getProjectId());
        }
    };

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_layout, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = getItem(position);
        holder.setData(task);
    }

}

class TaskListViewHolder extends RecyclerView.ViewHolder {
    public CardView card;
    public TextView startTimeTV, durationTV, titleTV;

    public TaskListViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.taskListCardView);
        startTimeTV = itemView.findViewById(R.id.startTimeTextView);
        durationTV = itemView.findViewById(R.id.durationTextView);
        titleTV = itemView.findViewById(R.id.taskTitleTextView);
    }

    public void setData(Task task) {
        startTimeTV.setText(DateTimeUtil.getDateFromEpoch(task.getStartTime()));
        durationTV.setText(DateTimeUtil.milliSecondToTimeFormat(task.getDuration()));
        titleTV.setText(task.getTitle());
    }
}