package ru.nikitasemiklit.diploma.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ConferenceTable;
import ru.nikitasemiklit.diploma.model.Conference;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class ConferenceCursorWrapper extends CursorWrapper {

    public ConferenceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Conference getConference(){
        Conference conference = new Conference();

        conference.setConferenceId(UUID.fromString(getString(getColumnIndex(ConferenceTable.Cols.UUID))));
        conference.setTitle(getString(getColumnIndex(ConferenceTable.Cols.TITLE)));
        conference.setDesc(getString(getColumnIndex(ConferenceTable.Cols.DESC)));

        return conference;
    }
}
