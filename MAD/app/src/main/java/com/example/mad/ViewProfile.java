package com.example.mad;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.PetProfile;

public class ViewProfile extends AppCompatActivity {

    private TextView viewName, viewBreed, viewAge;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);

        viewName = findViewById(R.id.viewname);
        viewBreed = findViewById(R.id.viewbreed);
        viewAge = findViewById(R.id.viewage);
        profileImage = findViewById(R.id.imageView);

        PetProfile petProfile = Profile.getPetProfile();

        if (petProfile != null) {
            viewName.setText(petProfile.getName());
            viewBreed.setText(petProfile.getBreed());
            viewAge.setText(petProfile.getAge());
            profileImage.setImageURI(Uri.parse(petProfile.getImageUri().toString()));
        }
    }
}

