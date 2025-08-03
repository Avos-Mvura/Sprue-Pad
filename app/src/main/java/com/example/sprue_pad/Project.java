package com.example.sprue_pad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Project implements Serializable {
    private final String id;
    private String name;
    private String brand;
    private String scale;
    private String status;
    private String desc;
    private ArrayList<String> notes;
    private ArrayList<String> tasks;
    private Map<String, Integer> inventory;
    private String imageUri;
    private int imageResId = R.drawable.default_avatar;

    public Project(String name, String brand, String scale, String status, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.brand = brand;
        this.scale = scale;
        this.status = status;
        this.desc = description;
        this.notes = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.inventory = new Map<String, Integer>() {
            @Override
            public void clear() {

            }

            @Override
            public boolean containsKey(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsValue(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Set<Entry<String, Integer>> entrySet() {
                return Collections.emptySet();
            }

            @Override
            public boolean equals(@Nullable Object o) {
                return false;
            }

            @Nullable
            @Override
            public Integer get(@Nullable Object o) {
                return 0;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return Collections.emptySet();
            }

            @Nullable
            @Override
            public Integer put(String s, Integer integer) {
                return 0;
            }

            @Override
            public void putAll(@NonNull Map<? extends String, ? extends Integer> map) {

            }

            @Nullable
            @Override
            public Integer remove(@Nullable Object o) {
                return 0;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public Collection<Integer> values() {
                return Collections.emptyList();
            }
        };
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
        return desc;
    }

    public void setDescription(String notes) {
        this.desc = notes;
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

    public String getId() {
        return id;
    }

    public void addNotes(String newNote) {
        this.notes.add(newNote);
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void clearNotes() {
        this.notes.clear();
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void addTask(String newTask) {
        this.tasks.add(newTask);
    }

    public void cleartasks() {
        this.tasks.clear();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void addInventory(String newInventory, int amount) {
        this.inventory.put(newInventory, 0);
    }

    public void updateInventory(String item, int amount) {
        this.inventory.put(item, amount);
    }

    public void clearInventory() {
        this.inventory.clear();
    }
}
