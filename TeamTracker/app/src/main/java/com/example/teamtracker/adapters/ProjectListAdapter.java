package com.example.teamtracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.listeners.ProjectClickListener;
import com.example.teamtracker.models.Project;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListViewHolder> {
    private final Context context;
    private List<Project> projectList;
    private ProjectClickListener projectClickListener;

    public ProjectListAdapter(Context context, List<Project>projectList, ProjectClickListener projectClickListener) {
        this.context = context;
        this.projectList = projectList;
        this.projectClickListener = projectClickListener;
    }

    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_list_item_layout, parent, false);
        return new ProjectListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListViewHolder holder, int position) {
        Project project = projectList.get(position);
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

    @Override
    public int getItemCount() {
        return projectList.size();
    }

}

class ProjectListViewHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public ProjectListViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.projectListTextView);
    }

    public void setData(Project project) {
        tv.setText(project.getProjectName());
    }
}