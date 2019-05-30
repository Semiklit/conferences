package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    @SerializedName("user_id")
    private UUID mUserId;
    @SerializedName("user_name")
    private String mName;
    @SerializedName("user_surname")
    private String mSurname;
    @SerializedName("user_photo_url")
    private String mPhotoUrl;

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }
}
