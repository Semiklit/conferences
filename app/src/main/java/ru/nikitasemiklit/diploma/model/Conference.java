package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
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
    @SerializedName("conference_start")
    private Date mStartConference;
    @SerializedName("conference_end")
    private Date mEndConference;
    @SerializedName("conference_registration_end")
    private Date mEndRegistration;

    public Conference(UUID mConferenceId, String mTitle, String mDesc, Date mStartConference, Date mEndConference, Date mEndRegistration) {
        this.mConferenceId = mConferenceId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mStartConference = mStartConference;
        this.mEndConference = mEndConference;
        this.mEndRegistration = mEndRegistration;
    }

    public Conference(String mTitle, String mDesc, Date mStartConference, Date mEndConference, Date mEndRegistration) {
        mConferenceId = UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mStartConference = mStartConference;
        this.mEndConference = mEndConference;
        this.mEndRegistration = mEndRegistration;
    }

    public UUID getConferenceId() {
        return mConferenceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }

    public Date getStartConference() {
        return mStartConference;
    }

    public Date getEndConference() {
        return mEndConference;
    }

    public Date getEndRegistration() {
        return mEndRegistration;
    }
}
