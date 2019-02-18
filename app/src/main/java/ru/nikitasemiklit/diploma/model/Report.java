package ru.nikitasemiklit.diploma.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class Report {
    private UUID mReportId;
    private UUID mUserId;
    private UUID mSectionId;
    private String mTitle;
    private String mDesc;
    private Date mArriveDate;
    private Date mLeaveDate;
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
