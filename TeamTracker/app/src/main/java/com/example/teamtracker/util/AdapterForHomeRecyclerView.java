package com.example.teamtracker.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;

import java.util.List;

public class AdapterForHomeRecyclerView extends RecyclerView.Adapter<AdapterForHomeRecyclerView.ViewHolderForHomeRecyclerView> {
    private List<ModelClass> projectList;
    public AdapterForHomeRecyclerView(List<ModelClass>projectList) {
        this.projectList=projectList;
    }

    @NonNull
    @Override
    public AdapterForHomeRecyclerView.ViewHolderForHomeRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item_layout,parent,false);
        return new ViewHolderForHomeRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForHomeRecyclerView holder, int position) {
        String name=projectList.get(position).getProjectName();
        holder.setData(name);

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ViewHolderForHomeRecyclerView extends RecyclerView.ViewHolder {
        private TextView tv;
        public ViewHolderForHomeRecyclerView(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.projectListTextView);

        }

        public void setData(String name) {
            tv.setText(name);
        }
    }
}
