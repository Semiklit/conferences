package ru.nikitasemiklit.diploma.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.ConferenceTable;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.utils.DateUtil;

public class ConferenceCursorWrapper extends CursorWrapper {

    public ConferenceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Conference getConference() {
        return new Conference(UUID.fromString(getString(getColumnIndex(ConferenceTable.Cols.UUID))),
                getString(getColumnIndex(ConferenceTable.Cols.TITLE)),
                getString(getColumnIndex(ConferenceTable.Cols.DESC)),
                DateUtil.parseDate(getString(getColumnIndex(ConferenceTable.Cols.START_DATE))),
                DateUtil.parseDate(getString(getColumnIndex(ConferenceTable.Cols.END_DATE))),
                DateUtil.parseDate(getString(getColumnIndex(ConferenceTable.Cols.END_REGISTRATIOTN_DATE))),
                getInt(getColumnIndex(ConferenceTable.Cols.IS_PUBLIC)) == 1,
                UUID.fromString(getString(getColumnIndex(ConferenceTable.Cols.OWNER_UUID))),
                getString(getColumnIndex(ConferenceTable.Cols.CITY)),
                getInt(getColumnIndex(ConferenceTable.Cols.IS_FAVOURITE)) == 1);
    }
}
