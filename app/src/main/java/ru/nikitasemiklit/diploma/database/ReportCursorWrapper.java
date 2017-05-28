package ru.nikitasemiklit.diploma.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ReportTable;
import ru.nikitasemiklit.diploma.model.Report;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class ReportCursorWrapper extends CursorWrapper {
    public ReportCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Report getReport (){
        Report report = new Report();

        report.setReportId(UUID.fromString(getString(getColumnIndex(ReportTable.Cols.UUID))));
        report.setUserId(UUID.fromString(getString(getColumnIndex(ReportTable.Cols.USER_UUID))));
        report.setSectionId(UUID.fromString(getString(getColumnIndex(ReportTable.Cols.SECTION_UUID))));
        report.setTitle(getString(getColumnIndex(ReportTable.Cols.TITLE)));
        report.setDesc(getString(getColumnIndex(ReportTable.Cols.DESC)));
        report.setArriveDate(new Date(getLong(getColumnIndex(ReportTable.Cols.DATE_ARRIVE))));
        report.setLeaveDate(new Date(getLong(getColumnIndex(ReportTable.Cols.DATE_LEAVE))));
        report.setTime(new Date(getLong(getColumnIndex(ReportTable.Cols.TIME))));

        return report;
    }
}
