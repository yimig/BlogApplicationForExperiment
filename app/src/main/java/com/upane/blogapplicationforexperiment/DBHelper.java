package com.upane.blogapplicationforexperiment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;
    private static final String DB_NAME="database.db";
    private static final String TBL_NAME="t_img";

    DBHelper(Context c)
    {
        super(c,DB_NAME,null,2);
    }

    public void onCreate(SQLiteDatabase db)
    {
        this.db=db;
        buildDb();
    }

    private static void buildDb()
    {
        db.execSQL("create table "+TBL_NAME+"(img_id intger primary key,img_path text);");
    }

    public void onClose()
    {
        if(db!=null)db.close();
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}
}
