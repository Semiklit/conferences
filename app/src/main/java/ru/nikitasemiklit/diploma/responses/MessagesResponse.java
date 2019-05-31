package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Message;

public class MessagesResponse extends Response {
    @SerializedName("notifications")
    List<Message> messages;

}
