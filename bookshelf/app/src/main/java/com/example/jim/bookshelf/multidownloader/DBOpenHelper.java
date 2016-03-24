package com.example.jim.bookshelf.multidownloader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jim on 16-3-19.
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
    private static final String DBNAME = "pcgroupDownloadLog.db";
    private static final String TASKLOG_TB = "taskLogTb";
    private static final int VERSION = 200;
    public static String initSQL = "CREATE TABLE IF NOT EXISTS taskLogTb (    id INTEGER PRIMARY KEY,     downloaderId INT(255),      downloadUrl INT(255), \t     savePath varchar(100),  \t downloadLength INT(255),\t downloadPercent INT(255),\t \t totalLength INT(255),\t     downloadTime timestamp,    taskState INT(1) );";

    public DBOpenHelper(Context paramContext)
    {
        super(paramContext, "pcgroupDownloadLog.db", null, 200);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.execSQL(initSQL);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
    }
}
