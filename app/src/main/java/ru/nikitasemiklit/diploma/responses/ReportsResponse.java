package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Report;

public class ReportsResponse extends Response {

    @SerializedName("ReportsList")
    private List<Report> reports;

    public List<Report> getReports() {
        return reports;
    }
}
