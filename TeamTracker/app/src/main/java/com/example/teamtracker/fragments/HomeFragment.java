package com.example.teamtracker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.adapters.ProjectListAdapter;
import com.example.teamtracker.listeners.ProjectClickListener;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.sync.ProjectSync;
import com.example.teamtracker.util.SharedRefs;
import com.example.teamtracker.viewmodels.ProjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProjectListAdapter projectListAdapter;
    FloatingActionButton fabAddButton;
    private ProjectViewModel projectViewModel;
    private SharedRefs sharedRefs;
    private ProjectSync projectSync;

    public HomeFragment(Context context) {
        sharedRefs = new SharedRefs(context);
    }

    public static HomeFragment newInstance(Context context) {
        return new HomeFragment(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectSync = new ProjectSync(getContext());
        projectSync.sync();

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectListAdapter.submitList(projects);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }, 500);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProjectsRecycler();
        fabAddButton = view.findViewById(R.id.fab_add_button);
        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProjectDialog(getContext());
            }
        });
    }

    private final ProjectClickListener projectClickListener = new ProjectClickListener() {
        @Override
        public void onClick(Project project) {
            Fragment fragment = new ProjectFragment(project);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("TaskView").commit();
        }

        @Override
        public void onLongClick(Project project, TextView textView) {
            Toast.makeText(getContext(), "long selected: " + project.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    private void updateProjectsRecycler() {
        recyclerView = getView().findViewById(R.id.projectsRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        projectListAdapter = new ProjectListAdapter(projectClickListener);
        recyclerView.setAdapter(projectListAdapter);
    }

    private void showAddProjectDialog(Context context) {
        final EditText projectNameEditText = new EditText(context);
        projectNameEditText.setMaxLines(1);
        projectNameEditText.setLines(1);
        projectNameEditText.setSingleLine(true);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Create a Project")
                .setMessage("What is your project name?")
                .setView(projectNameEditText)
                .setPositiveButton("Create", null)
                .setNegativeButton("Cancel", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String projectName = String.valueOf(projectNameEditText.getText()).trim();
                        if (TextUtils.isEmpty(projectName)) {
                            projectNameEditText.setError("Project name can't be empty.");
                        } else {
                            Project project = new Project(projectName, sharedRefs.getString(SharedRefs.USER_ID, ""));
                            projectViewModel.saveProject(project);
                            Toast.makeText(context, "Project Created Successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner_menu_colored_rectangle);
    }
}