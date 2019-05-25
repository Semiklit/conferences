package ru.nikitasemiklit.diploma.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class Report {
    @SerializedName("report_id")
    private UUID mReportId;
    @SerializedName("user_id")
    private UUID mUserId;
    @SerializedName("section_id")
    private UUID mSectionId;
    @SerializedName("report_title")
    private String mTitle;
    @SerializedName("report_desc")
    private String mDesc;
    @SerializedName("report_arrive_date")
    private Date mArriveDate;
    @SerializedName("report_leave_date")
    private Date mLeaveDate;
    @SerializedName("report_time")
    private Date mTime;

    public UUID getReportId() {
        return mReportId;
    }

    public void setReportId(UUID reportId) {
        mReportId = reportId;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }

    public UUID getSectionId() {
        return mSectionId;
    }

    public void setSectionId(UUID sectionId) {
        mSectionId = sectionId;
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

    public Date getArriveDate() {
        return mArriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        mArriveDate = arriveDate;
    }

    public Date getLeaveDate() {
        return mLeaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        mLeaveDate = leaveDate;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }
}
