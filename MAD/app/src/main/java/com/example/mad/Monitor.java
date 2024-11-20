package com.example.mad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Monitor extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private ImageView capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        Button capturePhotoBtn = findViewById(R.id.capture_photo_btn);
        Button petSelfieBtn = findViewById(R.id.pet_selfie_mode_btn);
        //Button recordEventBtn = findViewById(R.id.record_event_btn);
        capturedImage = findViewById(R.id.captured_image_view);

        // Request camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

        // Capture photo button
        capturePhotoBtn.setOnClickListener(v -> openCamera());

        // Pet selfie mode button
        petSelfieBtn.setOnClickListener(v -> Toast.makeText(Monitor.this, "Pet Selfie Mode Activated!", Toast.LENGTH_SHORT).show());

        // Record event button
       // recordEventBtn.setOnClickListener(v -> Toast.makeText(Monitor.this, "Event Recorded Successfully!", Toast.LENGTH_SHORT).show());
    }

    // Open camera to capture image
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
        }
    }

    // Handle captured image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            capturedImage.setImageBitmap(imageBitmap);
        }
    }

    // Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
