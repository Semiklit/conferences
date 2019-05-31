package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Notification;

public class NotificationsResponse extends Response {
    @SerializedName("notifications")
    List<Notification> notifications;


}
