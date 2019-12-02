package com.upane.blogapplicationforexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.List;

public class UserService {
    private DBHelper dbHelper;
    private static final String TBL_NAME="t_user";
    public UserService(Context c)
    {
        dbHelper=new DBHelper(c);
    }

    public void insert(ContentValues values)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.insert(TBL_NAME,null,values);
    }

    public Cursor query()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        return db.query(TBL_NAME,null,null,null,null,null,null);

    }

    public void del(String userName)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBL_NAME,"user_name=?",new String[]{userName});
    }
}
