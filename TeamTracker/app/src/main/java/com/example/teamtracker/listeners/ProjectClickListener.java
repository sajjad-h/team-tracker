package com.example.teamtracker.listeners;

import android.widget.TextView;
import com.example.teamtracker.models.Project;

public interface ProjectClickListener {
    void onClick(Project project);
    void onLongClick(Project project, TextView textView);
}
