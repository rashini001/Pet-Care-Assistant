package com.example.mad.models;

import android.net.Uri;

public class DiaryLog {
    private String text;
    private Uri photoUri;
    private Uri videoUri;

    public DiaryLog(String text, Uri photoUri, Uri videoUri) {
        this.text = text;
        this.photoUri = photoUri;
        this.videoUri = videoUri;
    }

    public String getText() {
        return text;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public Uri getVideoUri() {
        return videoUri;
    }
}
