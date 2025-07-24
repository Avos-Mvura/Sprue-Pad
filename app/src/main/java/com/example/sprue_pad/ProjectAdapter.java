package com.example.sprue_pad;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private final List<Project> projectList;
    private final Context context;

    public ProjectAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, detailsTextView;
        public ImageButton ivProjectSettings;
        public ImageButton ivProjectShare;
        public ShapeableImageView cardAvatar;

        public MaterialCardView projectInstance;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.project_name);
            detailsTextView = view.findViewById(R.id.project_details);
            ivProjectShare = view.findViewById(R.id.ivProjectShare);
            ivProjectSettings = view.findViewById(R.id.ivProjectSettings);
            cardAvatar = view.findViewById(R.id.tvProjectAvatar);
            projectInstance = view.findViewById(R.id.projectCard);
        }

        private void loadImageSafely(ShapeableImageView imageView, String imagePath, Context context) {
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Bitmap bitmap;
                    if (imagePath.startsWith("content://")) {
                        // Handle content URI
                        Uri imageUri = Uri.parse(imagePath);
                        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
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

        @SuppressLint("SetTextI18n")
        public void bind(Project project, Context context, List<Project> projectList, ProjectAdapter adapter) {
            nameTextView.setText(project.getName());
            detailsTextView.setText(project.getBrand() + " • " + project.getScale() + " • " + project.getStatus());

            // Set avatar on card using safe loading
            loadImageSafely(cardAvatar, project.getImageUri(), context);

            ivProjectSettings.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_project_card_options);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                EditText name = dialog.findViewById(R.id.changeProjectName);
                EditText brand = dialog.findViewById(R.id.changeBrand);
                EditText scale = dialog.findViewById(R.id.changeScale);
                EditText status = dialog.findViewById(R.id.changeStatus);
                EditText desc = dialog.findViewById(R.id.changeDescription);
                ShapeableImageView avatar = dialog.findViewById(R.id.dialogProjectAvatar);

                Button saveBtn = dialog.findViewById(R.id.btnSaveProject);
                Button deleteBtn = dialog.findViewById(R.id.btnDeleteProject);

                name.setText(project.getName());
                brand.setText(project.getBrand());
                scale.setText(project.getScale());
                status.setText(project.getStatus());
                desc.setText(project.getDescription());

                loadImageSafely(avatar, project.getImageUri(), context);

                Button changeAvatar = dialog.findViewById(R.id.btnChangeAvatar);
                changeAvatar.setOnClickListener(b -> {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).setActiveAvatarContext(avatar, project);
                        ((MainActivity) context).launchImagePicker();
                        ((MainActivity) context).saveProjects(); // optional here
                    }
                });

                saveBtn.setOnClickListener(b -> {

                    if ((name.getText().toString()).isEmpty()) {
                        name.setError("Project name is required");
                        name.requestFocus();
                        return;
                    }
                    project.setName(name.getText().toString());
                    project.setBrand(brand.getText().toString());
                    project.setScale(scale.getText().toString());
                    project.setStatus(status.getText().toString());
                    project.setDescription(desc.getText().toString());

                    adapter.notifyItemChanged(getAdapterPosition());
                    ((MainActivity) context).saveProjects();
                    dialog.dismiss();
                });

                deleteBtn.setOnClickListener(b -> {
                    int position = getAdapterPosition();
                    projectList.remove(position);
                    adapter.notifyItemRemoved(position);
                    ((MainActivity) context).saveProjects();
                    dialog.dismiss();
                });

                dialog.show();
            });

            ivProjectShare.setOnClickListener(view1 -> {

                String shareText = "Project: " + project.getName() + "\n"
                        + "Brand: " + project.getBrand() + "\n"
                        + "Scale: " + project.getScale() + "\n"
                        + "Status: " + project.getStatus() + "\n"
                        + "Notes: " + project.getDescription();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share project via");
                view1.getContext().startActivity(shareIntent);
            });

            projectInstance.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProjectContentsActivity.class);
                intent.putExtra("project", project);
                context.startActivity(intent);

            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.bind(project, context, projectList, this);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }
}