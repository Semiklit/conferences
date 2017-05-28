package ru.nikitasemiklit.diploma.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.SectionTable;
import ru.nikitasemiklit.diploma.model.Section;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class SectionCursorWrapper extends CursorWrapper {
    public SectionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Section getSection (){
        Section section = new Section();

        section.setSectionId(UUID.fromString(getString(getColumnIndex(SectionTable.Cols.UUID))));
        section.setConferenceId(UUID.fromString(getString(getColumnIndex(SectionTable.Cols.CONFERENCE_UUID))));
        section.setTitle(getString(getColumnIndex(SectionTable.Cols.TITLE)));
        section.setDesc(getString(getColumnIndex(SectionTable.Cols.DESC)));

        return section;
    }

}
