package ru.nikitasemiklit.diploma.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SectionCursorWrapper;
import ru.nikitasemiklit.diploma.database.SusuConferenceBaseHelper;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.SectionTable;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class SectionLab {

    public List<Section> getSections(UUID conferenceId) {

        List<Section> sections = new ArrayList<>();
        SectionCursorWrapper cursor = querySections(SectionTable.Cols.CONFERENCE_UUID + " = ?", new String[] { conferenceId.toString() });

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                sections.add(cursor.getSection());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return sections;
    }

    private Context mContext;
    private SQLiteDatabase mDataBase;

    private static SectionLab sSectionLab;

    public static SectionLab get(Context context){
        if (sSectionLab == null){
            sSectionLab = new SectionLab(context);
        }
        return sSectionLab;
    }

    private SectionLab(Context context){
        mContext = context.getApplicationContext();
        mDataBase = new SusuConferenceBaseHelper(mContext).getWritableDatabase();
    }

    public Section getSection(UUID id){
        SectionCursorWrapper cursor = querySections(
                SectionTable.Cols.UUID + " = ?", new String[] { id.toString()}
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSection();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (Section section){
        ContentValues values = new ContentValues();
        values.put(SectionTable.Cols.UUID, section.getSectionId().toString());


        return values;
    }

    public void addSection (Section section){
        ContentValues values = getContentValues(section);

        mDataBase.insert(SectionTable.NAME, null, values);
    }

    public void updateSection (Section section){
        String uuidString = section.getSectionId().toString();
        ContentValues values = getContentValues(section);

        mDataBase.update(SectionTable.NAME, values, SectionTable.Cols.UUID + " = ?" , new String[] { uuidString });
    }

    public void addSections (List<Section> sections, UUID conferenceId){
        mDataBase.delete(SectionTable.NAME, SectionTable.Cols.CONFERENCE_UUID + " = ?", new String[] { conferenceId.toString() });

        ContentValues values;

        for (Section section : sections){
            values = getContentValues(section);
            mDataBase.insert(SectionTable.NAME, null, values);
        }
    }

    private SectionCursorWrapper querySections (String whereClause, String [] whereArgs){
        Cursor cursor = mDataBase.query(
                SectionTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new SectionCursorWrapper(cursor);
    }
}
