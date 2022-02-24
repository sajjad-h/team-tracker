package com.example.teamtracker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.teamtracker.fragments.DemoFragment;
import com.example.teamtracker.fragments.GraphFragment;
import com.example.teamtracker.fragments.TaskViewFragment;
import com.example.teamtracker.models.Project;

public class ProjectPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 3;
    private Project project;

    public ProjectPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show all tasks view of a project
                return TaskViewFragment.newInstance(project);
            case 1: // Fragment # 1 - This will show team members of a project
                return DemoFragment.newInstance("team fragment");
            case 2: // Fragment # 2 - This will show graphs of a project
                return GraphFragment.newInstance(project);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}