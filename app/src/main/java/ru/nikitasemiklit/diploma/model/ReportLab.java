package ru.nikitasemiklit.diploma.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class ReportLab {

    public List<Report> getReports() {
        return mReports;
    }

    private List<Report> mReports;

    private static ReportLab sReportLab;

    public static ReportLab get(Context context){
        if (sReportLab == null){
            sReportLab = new ReportLab(context);
        }
        return sReportLab;
    }

    private ReportLab (Context context){
        mReports = new ArrayList<>();
    }

    public Report getReport (UUID reportId){
        for (Report report : mReports){
            if (report.getReportId().equals(reportId)){
                return report;
            }
        }
        return null;
    }
}
