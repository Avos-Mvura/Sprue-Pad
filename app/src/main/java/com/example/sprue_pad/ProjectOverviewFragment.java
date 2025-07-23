package com.example.sprue_pad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.io.InputStream;

public class ProjectOverviewFragment extends Fragment {

    private TextView nameText, detailsText, descriptionText;
    private ShapeableImageView avatarImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_overview, container, false);

        nameText = view.findViewById(R.id.text_project_name);
        detailsText = view.findViewById(R.id.text_project_details);
        descriptionText = view.findViewById(R.id.text_project_description);
        avatarImage = view.findViewById(R.id.image_project_avatar);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String brand = args.getString("brand");
            String scale = args.getString("scale");
            String status = args.getString("status");
            String description = args.getString("description");
            String imageUri = args.getString("imageUri");

            nameText.setText(name);
            detailsText.setText(brand + " • " + scale + " • " + status);
            descriptionText.setText(description);

            if (imageUri != null && !imageUri.isEmpty()) {
                try {
                    Uri uri = Uri.parse(imageUri);
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (inputStream != null) inputStream.close();
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException | SecurityException e) {
                    e.printStackTrace();
                    avatarImage.setImageResource(R.drawable.default_avatar);
                }
            }
        }

        return view;
    }
}
