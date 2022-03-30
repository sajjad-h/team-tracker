package com.example.teamtracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.listeners.ProjectClickListener;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;

import java.util.List;

public class ProjectListAdapter extends ListAdapter<Project, ProjectListViewHolder> {
    private ProjectClickListener projectClickListener;

    public ProjectListAdapter(ProjectClickListener projectClickListener) {
        super(DIFF_CALLBACK);
        this.projectClickListener = projectClickListener;
    }

    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getOwner().equals(newItem.getOwner());
        }
    };

    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item_layout, parent, false);
        return new ProjectListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListViewHolder holder, int position) {
        Project project = getItem(position);
        holder.setData(project);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectClickListener.onClick(project);
            }
        });

        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                projectClickListener.onLongClick(project, holder.tv);
                return true;
            }
        });
    }
}

class ProjectListViewHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public ProjectListViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.projectListTextView);
    }

    public void setData(Project project) {
        tv.setText(project.getTitle());
    }
}