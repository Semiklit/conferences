package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("desc")
    private Date date;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
