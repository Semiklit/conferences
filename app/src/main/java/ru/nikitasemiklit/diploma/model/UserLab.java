package ru.nikitasemiklit.diploma.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.database.SusuConferenceBaseHelper;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema;
import ru.nikitasemiklit.diploma.database.SusuConferenceDbSchema.UserTable;
import ru.nikitasemiklit.diploma.database.UsersCursorWrapper;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class UserLab {

    private Context mContext;
    private SQLiteDatabase mDataBase;

    private static UserLab sUserLab;

    public static UserLab get(Context context){
        if (sUserLab == null){
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }

    private UserLab(Context context){
        mContext = context.getApplicationContext();
        mDataBase = new SusuConferenceBaseHelper(mContext).getWritableDatabase();
    }

    public User getUser(UUID id){
        UsersCursorWrapper cursor = queryUsers(
                UserTable.Cols.UUID + " = ?", new String[] { id.toString()}
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.UUID, user.getUserId().toString());
        values.put(UserTable.Cols.NAME, user.getName());
        values.put(UserTable.Cols.SURNAME, user.getSurname());

        return values;
    }

    public void addUser (User user){
        ContentValues values = getContentValues(user);

        mDataBase.insert(UserTable.NAME, null, values);
    }

    public void updateUser (User user){
        String uuidString = user.getUserId().toString();
        ContentValues values = getContentValues(user);

        mDataBase.update(UserTable.NAME, values, UserTable.Cols.UUID + " = ?" , new String[] { uuidString });
    }

    private UsersCursorWrapper queryUsers (String whereClause, String [] whereArgs){
        Cursor cursor = mDataBase.query(
                UserTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new UsersCursorWrapper(cursor);
    }
}
