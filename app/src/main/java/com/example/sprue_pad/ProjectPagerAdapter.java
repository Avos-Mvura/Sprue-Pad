package com.example.sprue_pad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProjectPagerAdapter extends FragmentStateAdapter {

    private final Project project;

    public ProjectPagerAdapter(@NonNull FragmentActivity fa, Project project) {
        super(fa);
        this.project = project;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return Paint_Fragment.newInstance(project.getId());
            case 1:
                return Paint_Fragment.newInstance(project.getId());
            case 2:
                return Paint_Fragment.newInstance(project.getId());
            case 3:
                return Notes_Fragment.newInstance(project);
            default:
                return new Fragment(); // Fallback
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}

