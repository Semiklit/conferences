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
    @SerializedName("conference_is_public")
    private boolean isPublic;
    @SerializedName("conference_owner_id")
    private UUID ownerID;
    @SerializedName("conference_city")
    private String city;
    @SerializedName("conference_is_favourite")
    private boolean isFavourite;


    //Из базы
    public Conference(UUID mConferenceId, String mTitle, String mDesc, Date mStartConference, Date mEndConference, Date mEndRegistration, boolean isPublic, UUID ownerID, String city, boolean isFavourite) {
        this.mConferenceId = mConferenceId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mStartConference = mStartConference;
        this.mEndConference = mEndConference;
        this.mEndRegistration = mEndRegistration;
        this.isPublic = isPublic;
        this.ownerID = ownerID;
        this.city = city;
        this.isFavourite = isFavourite;
    }

    //Новая конференция
    public Conference(String mTitle, String mDesc, Date mStartConference, Date mEndConference, Date mEndRegistration, boolean isPublic, UUID ownerID, String city, boolean isFavourite) {
        mConferenceId = UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mStartConference = mStartConference;
        this.mEndConference = mEndConference;
        this.mEndRegistration = mEndRegistration;
        this.isPublic = isPublic;
        this.ownerID = ownerID;
        this.city = city;
        this.isFavourite = isFavourite;
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

    public boolean isPublic() {
        return isPublic;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public String getCity() {
        return city;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
