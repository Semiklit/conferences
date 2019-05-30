package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import ru.nikitasemiklit.diploma.model.User;

public class LoginResponse extends Response {
    @SerializedName("token")
    private UUID token;
    @SerializedName("user")
    private User user;

    public UUID getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
