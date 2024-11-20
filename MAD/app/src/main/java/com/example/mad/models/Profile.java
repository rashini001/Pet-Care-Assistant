package com.example.mad.models;


import com.example.mad.models.PetProfile;

public class Profile {
    private static PetProfile Profile; // Static instance to hold the pet profile

    // Method to retrieve the current pet profile
    public static PetProfile getPetProfile() {
        return Profile;
    }

    // Method to set or update the pet profile
    public static void setPetProfile(PetProfile profile) {
        Profile = profile;
    }

    // Method to clear the pet profile (delete operation)
    public static void clearPetProfile() {
        Profile = null;
    }
}

