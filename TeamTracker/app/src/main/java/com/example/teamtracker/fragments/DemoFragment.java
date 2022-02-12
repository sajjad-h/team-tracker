package com.example.teamtracker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teamtracker.R;

public class DemoFragment extends Fragment {
    private String demoMessage;

    public DemoFragment(String demoMessage) {
        this.demoMessage = demoMessage;
    }

    public static DemoFragment newInstance(String demoMessage) {
        DemoFragment fragment = new DemoFragment(demoMessage);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView demoTextView = view.findViewById(R.id.demo_text_view);
        demoTextView.setText(demoMessage);
    }

    public static class newInstance extends Fragment {
        public newInstance(String demo_fragment) {
        }
    }
}