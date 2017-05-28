package ru.nikitasemiklit.diploma.model;

import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class Users {
    UUID mUserId;
    String mName;
    String mSurname;
    String mPhotoUrl;

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
