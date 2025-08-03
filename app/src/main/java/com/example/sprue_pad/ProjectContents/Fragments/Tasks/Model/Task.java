package com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model;

public class Task {
    private boolean isCompleted;
    private String text;

    public Task(boolean isCompleted, String text) {
        this.isCompleted = isCompleted;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isCompleted;
    }

    public void setChecked(boolean checked) {
        this.isCompleted = checked;
    }
}
