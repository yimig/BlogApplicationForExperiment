package com.upane.blogapplicationforexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ImgService {
    private DBHelper dbHelper;
    private static final String TBL_NAME="t_img";
    public ImgService(Context c)
    {
        dbHelper=new DBHelper(c);
    }

    public void insert(ContentValues values)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.insert(TBL_NAME,null,values);
    }

    public Cursor query(int imgId)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor c= db.query(TBL_NAME,new String[]{"img_id","img_path"},"img_id=?",new String[]{String.valueOf(imgId)},null,null,null);
        return c;
    }

    public void del(int imgId)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBL_NAME,"img_id=?",new String[]{String.valueOf(imgId)});
    }
}
