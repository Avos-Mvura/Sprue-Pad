package com.example.sprue_pad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import top.defaults.colorpicker.ColorPickerPopup;

public class CreateColorPickerActivity extends AppCompatActivity {

    private EditText nameInput, brandInput, typeInput, notesInput;
    private int mDefaultColor;
    private View mColorPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_paint_picker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createPaintTracker), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.createPaintName);
        brandInput = findViewById(R.id.createBrandName);
        typeInput= findViewById(R.id.createPaintType);
        notesInput = findViewById(R.id.createPaintNote);

        Button setPaint = findViewById(R.id.createPaintSet);
        CardView colorPicker = findViewById(R.id.createColorPicker);
        mColorPreview = findViewById(R.id.create_preview_selected_color);
        mDefaultColor = 0;

        //Function to choose paint
        colorPicker.setOnClickListener(view -> new ColorPickerPopup.Builder(CreateColorPickerActivity.this)
                .initialColor(Color.RED)
                .enableBrightness(true)
                .enableAlpha(true)
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        mDefaultColor = color;
                        mColorPreview.setBackgroundColor(mDefaultColor);
                    }
                }));

        //Call function to set Paint
        setPaint.setOnClickListener(view -> createPaintCard());
    }

    //Function to set Paint
    private void createPaintCard() {
        String name = nameInput.getText().toString().trim();
        String brand = brandInput.getText().toString().trim();
        String type = typeInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Paint name required");
            nameInput.requestFocus();
            return;
        }

        Intent data = new Intent();
        data.putExtra("name", name);
        data.putExtra("brand", brand);
        data.putExtra("type", type);
        data.putExtra("notes", notes);
        setResult(RESULT_OK, data);
        finish();
    }
}