package ru.nikitasemiklit.diploma.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.UserTable;
import ru.nikitasemiklit.diploma.model.User;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class UsersCursorWrapper extends CursorWrapper {
    public UsersCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser (){
        User user = new User();

        user.setUserId(UUID.fromString(getString(getColumnIndex(UserTable.Cols.UUID))));
        user.setName(getString(getColumnIndex(UserTable.Cols.NAME)));
        user.setSurname(getString(getColumnIndex(UserTable.Cols.SURNAME)));

        return user;
    }
}
