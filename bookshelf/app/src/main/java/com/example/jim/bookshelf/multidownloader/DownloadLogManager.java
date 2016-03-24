package com.example.jim.bookshelf.multidownloader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jim.bookshelf.utils.NumUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 16-3-19.
 */
public class DownloadLogManager
{
    private static DBOpenHelper openHelper;
    private static DownloadLogManager taskLogService;
    private DownloadManager downloadManager;

    private DownloadLogManager(Context paramContext, DownloadManager paramDownloadManager)
    {
        this.downloadManager = paramDownloadManager;
        openHelper = new DBOpenHelper(paramContext);
    }

    public static DownloadLogManager getInstance(Context paramContext, DownloadManager paramDownloadManager)
    {
        if (taskLogService == null)
            taskLogService = new DownloadLogManager(paramContext, paramDownloadManager);
        return taskLogService;
    }

    public void createTaskLog(DownloadTask paramDownloadTask)
    {
        if (isExist(paramDownloadTask))
            return;
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[7];
        arrayOfObject[0] = paramDownloadTask.getDownloaderId();
        arrayOfObject[1] = paramDownloadTask.getUrl();
        arrayOfObject[2] = paramDownloadTask.getFilePath().getAbsolutePath();
        arrayOfObject[3] = Integer.valueOf(paramDownloadTask.getTaskState());
        arrayOfObject[4] = Integer.valueOf(0);
        arrayOfObject[5] = Integer.valueOf(0);
        arrayOfObject[6] = Integer.valueOf(0);
        localSQLiteDatabase.execSQL("insert into taskLogTb(downloaderId,downloadUrl,savePath,taskState,downloadLength,totalLength,downloadTime) values(?,?,?,?,?,?,?)", arrayOfObject);
    }

    public boolean deleteTaskLog(DownloadTask paramDownloadTask)
    {
        return deleteTaskLog(paramDownloadTask.getUrl());
    }

    public boolean deleteTaskLog(String paramString)
    {
        openHelper.getWritableDatabase().execSQL("delete from taskLogTb where downloadUrl = ?", new Object[] { paramString });
        return true;
    }

    public boolean deleteTaskLog(List<String> paramList)
    {
        for (int i = 0; i < paramList.size(); i++)
            deleteTaskLog((String)paramList.get(i));
        return true;
    }

    public List<DownloadTask> getRunningTask()
    {
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = openHelper.getWritableDatabase().rawQuery("select * from taskLogTb where taskState = ?", new String[] { "3" });
        if (localCursor.moveToNext())
        {
            String str = localCursor.getString(localCursor.getColumnIndex("downloadUrl"));
            File localFile = new File(localCursor.getString(localCursor.getColumnIndex("savePath")));
            localArrayList.add(this.downloadManager.buildDownloadTask("id", str, localFile));
        }
        return localArrayList;
    }

    public int getTaskDownloadPercent(DownloadTask paramDownloadTask)
    {
        long l1 = getTaskTotalSize(paramDownloadTask);
        long l2 = getTaskProgress(paramDownloadTask);
        if (l1 <= 0L)
            return 0;
        return NumUtils.computePercent(l2, l1);
    }

    public int getTaskProgress(DownloadTask paramDownloadTask)
    {
        return getTaskProgress(paramDownloadTask.getUrl());
    }

    public int getTaskProgress(String paramString)
    {
        Cursor localCursor = openHelper.getWritableDatabase().rawQuery("select * from taskLogTb where downloadUrl = ?", new String[] { paramString });
        boolean bool = localCursor.moveToNext();
        int i = 0;
        if (bool)
            i = localCursor.getInt(localCursor.getColumnIndex("downloadLength"));
        return i;
    }

    public int getTaskState(DownloadTask paramDownloadTask)
    {
        return getTaskState(paramDownloadTask.getUrl());
    }

    public int getTaskState(String paramString)
    {
        Cursor localCursor = openHelper.getWritableDatabase().rawQuery("select * from taskLogTb where downloadUrl = ?", new String[] { paramString });
        boolean bool = localCursor.moveToFirst();
        int i = 0;
        if (bool)
            i = localCursor.getInt(localCursor.getColumnIndex("taskState"));
        return i;
    }

    public int getTaskTotalSize(DownloadTask paramDownloadTask)
    {
        return getTaskTotalSize(paramDownloadTask.getUrl());
    }

    public int getTaskTotalSize(String paramString)
    {
        Cursor localCursor = openHelper.getWritableDatabase().rawQuery("select * from taskLogTb where downloadUrl = ?", new String[] { paramString });
        if (localCursor.moveToNext())
            return localCursor.getInt(localCursor.getColumnIndex("totalLength"));
        return -1;
    }

    public boolean isExist(DownloadTask paramDownloadTask)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        String[] arrayOfString = new String[1];
        arrayOfString[0] = paramDownloadTask.getUrl();
        return localSQLiteDatabase.rawQuery("select * from taskLogTb where downloadUrl = ?", arrayOfString).moveToFirst();
    }

    public void updateDownloadTime(DownloadTask paramDownloadTask)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramDownloadTask.getUrl();
        localSQLiteDatabase.execSQL("update taskLogTb set downloadTime = datetime() where downloadUrl = ? and downloadTime = 0", arrayOfObject);
    }

    public void updateTaskProgress(DownloadTask paramDownloadTask)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Long.valueOf(paramDownloadTask.getFileDownSize());
        arrayOfObject[1] = paramDownloadTask.getUrl();
        localSQLiteDatabase.execSQL("update taskLogTb set downloadLength = ? where downloadUrl = ?", arrayOfObject);
    }

    public void updateTaskState(DownloadTask paramDownloadTask)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramDownloadTask.getTaskState());
        arrayOfObject[1] = paramDownloadTask.getUrl();
        localSQLiteDatabase.execSQL("update taskLogTb set taskState = ? where downloadUrl = ?", arrayOfObject);
    }

    public void updateTaskState(String paramString, int paramInt)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        if (paramInt == 0);
        for (String str = "update taskLogTb set taskState = ?,downloadLength=0,downloadPercent=0 where downloadUrl = ?"; ; str = "update taskLogTb set taskState = ? where downloadUrl = ?")
        {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = Integer.valueOf(paramInt);
            arrayOfObject[1] = paramString;
            localSQLiteDatabase.execSQL(str, arrayOfObject);
            return;
        }
    }

    public void updateTotalSize(DownloadTask paramDownloadTask)
    {
        SQLiteDatabase localSQLiteDatabase = openHelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Long.valueOf(paramDownloadTask.getFileTotalSize());
        arrayOfObject[1] = paramDownloadTask.getUrl();
        localSQLiteDatabase.execSQL("update taskLogTb set totalLength = ? where downloadUrl = ?", arrayOfObject);
    }
}