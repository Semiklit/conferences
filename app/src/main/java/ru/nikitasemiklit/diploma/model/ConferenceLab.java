package ru.nikitasemiklit.diploma.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceBaseHelper;
import ru.nikitasemiklit.diploma.database.ConferenceCursorWrapper;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ConferenceTable;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ConferenceLab {

    public List<Conference> getConferences() {

        List<Conference> conferences = new ArrayList<>();
        ConferenceCursorWrapper cursor = queryConferences(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                conferences.add(cursor.getConference());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return conferences;
    }

    private Context mContext;
    private SQLiteDatabase mDataBase;

    private static ConferenceLab sConferenceLab;

    public static ConferenceLab get(Context context){
        if (sConferenceLab == null){
            sConferenceLab = new ConferenceLab(context);
        }
        return sConferenceLab;
    }

    private ConferenceLab(Context context){
        mContext = context.getApplicationContext();
        mDataBase = new SusuConferenceBaseHelper(mContext).getWritableDatabase();
    }

    public Conference getConference(UUID id){
        ConferenceCursorWrapper cursor = queryConferences(
                ConferenceTable.Cols.UUID + " = ?", new String[] { id.toString()}
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getConference();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (Conference conference){
        ContentValues values = new ContentValues();
        values.put(ConferenceTable.Cols.UUID, conference.getConferenceId().toString());
        values.put(ConferenceTable.Cols.TITLE, conference.getTitle().toString());
        values.put(ConferenceTable.Cols.DESC, conference.getDesc().toString());

        return values;
    }

    public void addConference (Conference conference){
        ContentValues values = getContentValues(conference);

        mDataBase.insert(ConferenceTable.NAME, null, values);
    }

    public void updateConference (Conference conference){
        String uuidString = conference.getConferenceId().toString();
        ContentValues values = getContentValues(conference);

        mDataBase.update(ConferenceTable.NAME, values, ConferenceTable.Cols.UUID + " = ?" , new String[] { uuidString });
    }

    public void addConferences (List<Conference> conferences){
        mDataBase.delete(ConferenceTable.NAME, null, null);

        ContentValues values;

        for (Conference conference : conferences){
            values = getContentValues(conference);
            mDataBase.insert(ConferenceTable.NAME, null, values);
        }
    }

    private ConferenceCursorWrapper queryConferences (String whereClause, String [] whereArgs){
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
