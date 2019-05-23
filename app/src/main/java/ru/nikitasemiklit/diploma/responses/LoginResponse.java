package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class LoginResponse extends Response {
    @SerializedName("Token")
    private UUID token;

    public UUID getToken() {
        return token;
    }
}
