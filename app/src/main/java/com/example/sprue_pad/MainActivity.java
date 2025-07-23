package com.example.sprue_pad;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView projectContainer;

    private ArrayList<Project> projectList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private static final String PREF_KEY = "projects";
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ShapeableImageView activeAvatarView;
    private Project activeProject;

    private ActivityResultLauncher<Intent> createProjectLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        projectContainer = findViewById(R.id.project_container);
        projectContainer.setLayoutManager(new GridLayoutManager(this, 2));
        sharedPreferences = getSharedPreferences("sprue_pad_pref", MODE_PRIVATE);

        createProjectLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        String name = data.getStringExtra("name");
                        String brand = data.getStringExtra("brand");
                        String scale = data.getStringExtra("scale");
                        String status = data.getStringExtra("status");
                        String notes = data.getStringExtra("notes");

                        Project newProj = new Project(name, brand, scale, status, notes);
                        projectList.add(newProj);
                        saveProjects();
                        if (projectContainer.getAdapter() != null) {
                            projectContainer.getAdapter().notifyItemInserted(projectList.size() - 1);
                        }

                    }
                });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();

                        // Copy to internal storage to avoid permission issues
                        String internalPath = copyImageToInternalStorage(imageUri);

                        if (internalPath != null) {
                            // Set the image using safe bitmap loading
                            if (activeAvatarView != null) {
                                loadImageSafely(activeAvatarView, internalPath);
                            }

                            // Save internal path to the project and persist
                            if (activeProject != null) {
                                activeProject.setImageUri(internalPath);
                                saveProjects();
                            }
                        }
                    }
                });

        ExtendedFloatingActionButton addProject = findViewById(R.id.fab_add_project);

        addProject.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateProjectActivity.class);
            createProjectLauncher.launch(intent);
        });

        loadProjects();
        renderProjects();
    }

    // Safe image loading method
    private void loadImageSafely(ShapeableImageView imageView, String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Bitmap bitmap;
                if (imagePath.startsWith("content://")) {
                    // Handle content URI
                    Uri imageUri = Uri.parse(imagePath);
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    if (inputStream != null) inputStream.close();
                } else {
                    // Handle file path
                    bitmap = BitmapFactory.decodeFile(imagePath);
                }

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.drawable.default_avatar);
                }
            } catch (IOException | SecurityException e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.default_avatar);
            }
        } else {
            imageView.setImageResource(R.drawable.default_avatar);
        }
    }

    // Copy image to internal storage to avoid permission issues
    private String copyImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream == null) return null;

            // Create a unique filename
            String fileName = "project_image_" + System.currentTimeMillis() + ".jpg";
            File file = new File(getFilesDir(), fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadProjects() {
        String json = sharedPreferences.getString(PREF_KEY, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Project>>() {}.getType();
            projectList = gson.fromJson(json, type);
        } else {
            projectList = new ArrayList<>();
        }
    }

    public void saveProjects() {
        String json = gson.toJson(projectList);
        sharedPreferences.edit().putString(PREF_KEY, json).apply();
    }

    private void renderProjects() {
        ProjectAdapter adapter = new ProjectAdapter(this, projectList);
        projectContainer.setAdapter(adapter);
    }

    public void setActiveAvatarContext(ShapeableImageView avatarView, Project project) {
        this.activeAvatarView = avatarView;
        this.activeProject = project;
    }

    public void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }
}