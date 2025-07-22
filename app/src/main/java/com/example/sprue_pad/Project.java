package com.example.sprue_pad;

public class Project {
    private String name;
    private String brand;
    private String scale;
    private String status;
    private String notes;

    private String imageUri;
    private int imageResId = R.drawable.default_avatar;

    public Project(String name, String brand, String scale, String status, String notes) {
        this.name = name;
        this.brand = brand;
        this.scale = scale;
        this.status = status;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return notes;
    }

    public void setDescription(String notes) {
        this.notes = notes;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
