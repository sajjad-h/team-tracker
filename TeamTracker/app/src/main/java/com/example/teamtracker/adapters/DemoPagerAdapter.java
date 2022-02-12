package com.example.teamtracker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.teamtracker.fragments.DemoFragment;

public class DemoPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 3;

    public DemoPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show fragment with message "first fragment"
                return DemoFragment.newInstance("first fragment");
            case 1: // Fragment # 0 - This will show fragment with message "second fragment"
                return DemoFragment.newInstance("second fragment");
            case 2: // Fragment # 1 - This will show fragment with message "third fragment"
                return DemoFragment.newInstance("third fragment");
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}