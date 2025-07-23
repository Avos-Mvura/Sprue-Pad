package com.example.sprue_pad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProjectPagerAdapter extends FragmentStateAdapter {

    public ProjectPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment;
        switch (position) {
            case 0:
                return new Notes_Fragment();
            case 1:
                return new Paint_Fragment();
            case 2:
                return new Notes_Fragment();
            case 3:
                return new Notes_Fragment();
            default:
                return new Notes_Fragment();
        }


    }

    @Override
    public int getItemCount() {
        return 4; // Tasks, Paint, Inventory, Notes
    }
}
