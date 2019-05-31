package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import ru.nikitasemiklit.diploma.model.User;

public class UserResponse extends Response {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }
}
