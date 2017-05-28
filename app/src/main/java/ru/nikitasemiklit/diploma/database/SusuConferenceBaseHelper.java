package ru.nikitasemiklit.diploma.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ConferenceTable;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ReportTable;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.SectionTable;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.UserTable;
import ru.nikitasemiklit.diploma.model.Section;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class SusuConferenceBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "conferenceBase.db";

    public SusuConferenceBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ConferenceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ConferenceTable.Cols.UUID + ", " +
                ConferenceTable.Cols.TITLE + ", " +
                ConferenceTable.Cols.DESC + ")"
        );

        db.execSQL("create table " + ReportTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ReportTable.Cols.UUID + ", " +
                ReportTable.Cols.USER_UUID + ", " +
                ReportTable.Cols.SECTION_UUID + ", " +
                ReportTable.Cols.TITLE + ", " +
                ReportTable.Cols.DESC + ", " +
                ReportTable.Cols.DATE_ARRIVE + ", " +
                ReportTable.Cols.DATE_LEAVE + ", " +
                ReportTable.Cols.TIME + ")"
        );

        db.execSQL("create table " + SectionTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SectionTable.Cols.UUID + ", " +
                SectionTable.Cols.CONFERENCE_UUID + ", " +
                SectionTable.Cols.TITLE + ", " +
                SectionTable.Cols.DESC + ")"
        );

        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.NAME + ", " +
                UserTable.Cols.SURNAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
