package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class Conference {
    @SerializedName("conference_id")
    private UUID mConferenceId;
    @SerializedName("conference_title")
    private String mTitle;
    @SerializedName("conference_desc")
    private String mDesc;

    public UUID getConferenceId() {
        return mConferenceId;
    }

    public void setConferenceId(UUID conferenceId) {
        mConferenceId = conferenceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }
}
