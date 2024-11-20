package com.example.mad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.PetProfile;

import java.io.IOException;

public class Profile extends AppCompatActivity {

    private EditText petName, petBreed, petAge;
    private ImageView profileImage;
    private Button uploadImageButton, createButton, viewButton;
    private Uri imageUri;

    private static PetProfile petProfile; // Holds the created profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        petName = findViewById(R.id.name);
        petBreed = findViewById(R.id.breed);
        petAge = findViewById(R.id.age);
        profileImage = findViewById(R.id.profileImage);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        createButton = findViewById(R.id.createBtn);
        viewButton = findViewById(R.id.viewBtn);

        uploadImageButton.setOnClickListener(v -> openGallery());

        createButton.setOnClickListener(v -> saveProfile());

        viewButton.setOnClickListener(v -> {
            if (petProfile != null) {
                Intent intent = new Intent(Profile.this, ViewProfile.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No profile created yet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfile() {
        String name = petName.getText().toString();
        String breed = petBreed.getText().toString();
        String age = petAge.getText().toString();

        if (name.isEmpty() || breed.isEmpty() || age.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and upload an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        petProfile = new PetProfile(name, breed, age, imageUri);
        Toast.makeText(this, "Profile created successfully!", Toast.LENGTH_SHORT).show();

        // Clear inputs
        petName.setText("");
        petBreed.setText("");
        petAge.setText("");
        profileImage.setImageResource(R.drawable.ic_default_profile);
    }

    public static PetProfile getPetProfile() {
        return petProfile;
    }
}
