package ru.nikitasemiklit.diploma.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.ReportCursorWrapper;
import ru.nikitasemiklit.diploma.database.SusuConferenceBaseHelper;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ReportTable;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class ReportLab {

    public List<Report> getReports(UUID sectionId) {

        List<Report> reports = new ArrayList<>();
        ReportCursorWrapper cursor = queryReport(ReportTable.Cols.SECTION_UUID + " = ?", new String[] { sectionId.toString()});

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                reports.add(cursor.getReport());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return reports;
    }

    private Context mContext;
    private SQLiteDatabase mDataBase;

    private static ReportLab sReportLab;

    public static ReportLab get(Context context){
        if (sReportLab == null){
            sReportLab = new ReportLab(context);
        }
        return sReportLab;
    }

    private ReportLab (Context context){
        mContext = context.getApplicationContext();
        mDataBase = new SusuConferenceBaseHelper(mContext).getWritableDatabase();
    }

    public Report getReport (UUID reportId){
        ReportCursorWrapper cursor = queryReport(
                ReportTable.Cols.UUID + " = ?", new String[] { reportId.toString()}
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getReport();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (Report report){
        ContentValues values = new ContentValues();
        values.put(ReportTable.Cols.UUID, report.getReportId().toString());
        values.put(ReportTable.Cols.USER_UUID, report.getUserId().toString());
        values.put(ReportTable.Cols.SECTION_UUID, report.getReportId().toString());
        values.put(ReportTable.Cols.TITLE, report.getTitle());
        values.put(ReportTable.Cols.DESC, report.getDesc());
        values.put(ReportTable.Cols.DATE_ARRIVE, report.getArriveDate().toString());
        values.put(ReportTable.Cols.DATE_LEAVE, report.getLeaveDate().toString());
        values.put(ReportTable.Cols.TIME, report.getTime().toString());

        return values;
    }

    public void addReport (Report report){
        ContentValues values = getContentValues(report);

        mDataBase.insert(ReportTable.NAME, null, values);
    }

    public void updateReport (Report report){
        String uuidString = report.getReportId().toString();
        ContentValues values = getContentValues(report);

        mDataBase.update(ReportTable.NAME, values, ReportTable.Cols.UUID + " = ?" , new String[] { uuidString });
    }

    public void addReports (List<Report> reports, UUID sectionId){
        mDataBase.delete(ReportTable.NAME, ReportTable.Cols.SECTION_UUID + " = ?", new String[] { sectionId.toString() });

        ContentValues values;

        for (Report report : reports){
            values = getContentValues(report);
            mDataBase.insert(ReportTable.NAME, null, values);
        }
    }

    private ReportCursorWrapper queryReport (String whereClause, String [] whereArgs) {
        Cursor cursor = mDataBase.query(
                ReportTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ReportCursorWrapper(cursor);
    }
}
