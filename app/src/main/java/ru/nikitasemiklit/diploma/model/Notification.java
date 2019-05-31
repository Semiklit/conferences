package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("title")
    String title;
    @SerializedName("text")
    String text;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
