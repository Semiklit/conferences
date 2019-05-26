package ru.nikitasemiklit.diploma.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.ConferenceCursorWrapper;
import ru.nikitasemiklit.diploma.database.SusuConferenceBaseHelper;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ConferenceTable;
import ru.nikitasemiklit.diploma.utils.DateUtil;

public class ConferenceLab {

    private ConferenceLab(Context context) {
        mDataBase = new SusuConferenceBaseHelper(context).getWritableDatabase();
    }

    private SQLiteDatabase mDataBase;

    private static ConferenceLab sConferenceLab;

    public static ConferenceLab get(Context context) {
        if (sConferenceLab == null) {
            sConferenceLab = new ConferenceLab(context);
        }
        return sConferenceLab;
    }

    private static ContentValues getContentValues(Conference conference) {
        ContentValues values = new ContentValues();
        values.put(ConferenceTable.Cols.UUID, conference.getConferenceId().toString());
        values.put(ConferenceTable.Cols.TITLE, conference.getTitle());
        values.put(ConferenceTable.Cols.DESC, conference.getDesc());
        values.put(ConferenceTable.Cols.START_DATE, DateUtil.formatDate(conference.getStartConference()));
        values.put(ConferenceTable.Cols.END_DATE, DateUtil.formatDate(conference.getEndConference()));
        values.put(ConferenceTable.Cols.END_REGISTRAIOTN_DATE, DateUtil.formatDate(conference.getEndRegistration()));

        return values;
    }

    public List<Conference> getConferences() {
        List<Conference> conferences = new ArrayList<>();
        try (ConferenceCursorWrapper cursor = queryConferences(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                conferences.add(cursor.getConference());
                cursor.moveToNext();
            }
        }
        return conferences;
    }

    public Conference getConference(UUID id) {
        try (ConferenceCursorWrapper cursor = queryConferences(
                ConferenceTable.Cols.UUID + " = ?", new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getConference();
        }
    }

    public void addConference(Conference conference) {
        ContentValues values = getContentValues(conference);
        mDataBase.insert(ConferenceTable.NAME, null, values);
    }

    public void updateConference(Conference conference) {
        String uuidString = conference.getConferenceId().toString();
        mDataBase.update(ConferenceTable.NAME, getContentValues(conference), ConferenceTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public void addConferences(List<Conference> conferences) {
        mDataBase.delete(ConferenceTable.NAME, null, null);
        for (Conference conference : conferences) {
            mDataBase.insert(ConferenceTable.NAME, null, getContentValues(conference));
        }
    }

    private ConferenceCursorWrapper queryConferences(String whereClause, String[] whereArgs) {
        Cursor cursor = mDataBase.query(
                ConferenceTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ConferenceCursorWrapper(cursor);
    }
}
