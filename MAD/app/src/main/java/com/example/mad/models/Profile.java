package com.example.mad.models;

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
    // This will delete the profile by setting it to null
    public static void clearPetProfile() {
        Profile = null;
    }

    // Optional: You can add a method to check if the profile exists
    public static boolean hasProfile() {
        return Profile != null;
    }
}
