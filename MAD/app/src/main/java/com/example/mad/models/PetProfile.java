package com.example.mad.models;

import android.net.Uri;

public class PetProfile {
    private String name;
    private String breed;
    private String age;
    private Uri imageUri;

    public PetProfile(String name, String breed, String age, Uri imageUri) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getAge() {
        return age;
    }

    public Uri getImageUri() {
        return imageUri;
    }

}
