package com.upane.blogapplicationforexperiment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;
    private static final String DB_NAME="database.db";
    private static final String TBL_NAME1="t_user";
    private static final String TBL_NAME2="t_messages";

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
        db.execSQL("create table "+TBL_NAME1+"(user_name text primary key,gender text,e_mail text,device text,date text,img_code integer);");
        db.execSQL("create table "+TBL_NAME2+"(id integer primary key AUTOINCREMENT,user_name references t_user(user_name),content text,post_date text);");
    }

    public void onClose()
    {
        if(db!=null)db.close();
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}
}
