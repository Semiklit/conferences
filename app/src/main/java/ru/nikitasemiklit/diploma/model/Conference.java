package ru.nikitasemiklit.diploma.model;

import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class Conference {

    UUID mConferenceId;
    String mTitle;
    String mDesc;

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
