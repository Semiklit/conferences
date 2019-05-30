package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class Section{
    @SerializedName("section_id")
    private UUID mSectionId;
    @SerializedName("conference_id")
    private UUID mConferenceId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("desc")
    private String mDesc;
    @SerializedName("reports_ids")
    List<UUID> reportsIds;

    public Section() {

    }

    public Section(String mTitle, String mDesc) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
    }

    public UUID getSectionId() {
        return mSectionId;
    }

    public void setSectionId(UUID sectionId) {
        mSectionId = sectionId;
    }

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

    public List<UUID> getReportsIds() {
        return reportsIds;
    }
}
