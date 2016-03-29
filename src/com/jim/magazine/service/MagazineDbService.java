package com.jim.magazine.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class MagazineDbService
{
  private static MagazineDbHelpler dbHelper;
  private static MagazineDbService magazineDbService;

  private MagazineDbService(Context paramContext)
  {
    dbHelper = new MagazineDbHelpler(paramContext);
  }

  public static MagazineDbService getInstence(Context paramContext)
  {
    if (magazineDbService == null)
      magazineDbService = new MagazineDbService(paramContext);
    return magazineDbService;
  }

  public List<String> delAllMagazine()
  {
    SQLiteDatabase localSQLiteDatabase = dbHelper.getWritableDatabase();
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = localSQLiteDatabase.rawQuery("select url from magazine_tb where status = ?", new String[] { "2" });
    while (localCursor.moveToNext())
      localArrayList.add(localCursor.getString(0));
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(0);
    arrayOfObject[1] = Integer.valueOf(0);
    arrayOfObject[2] = Integer.valueOf(2);
    localSQLiteDatabase.execSQL("update magazine_tb set status=?,percent=? where status = ?", arrayOfObject);
    return localArrayList;
  }

  public void delMagazine(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = dbHelper.getWritableDatabase();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(0);
    arrayOfObject[1] = paramString;
    localSQLiteDatabase.execSQL("update magazine_tb set status=?,percent=0 where url = ?", arrayOfObject);
  }

  public void delMagazine(List<String> paramList)
  {
    for (int i = 0; i < paramList.size(); i++)
      delMagazine(paramList.get(i));
  }

  public List<String> delMagazineExceptLately(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = dbHelper.getWritableDatabase();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "2";
    arrayOfString[1] = "2";
    arrayOfString[2] = (paramInt + "");
    Cursor localCursor = localSQLiteDatabase.rawQuery("select * from magazine_tb where status = ?  order by time desc  limit (select count(*)  from magazine_tb where status = ? ) offset ?", arrayOfString);
    while (localCursor.moveToNext())
      localArrayList.add(localCursor.getString(localCursor.getColumnIndex("url")));
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = Integer.valueOf(0);
    arrayOfObject[1] = Integer.valueOf(0);
    arrayOfObject[2] = Integer.valueOf(2);
    arrayOfObject[3] = Integer.valueOf(2);
    arrayOfObject[4] = Integer.valueOf(paramInt);
    localSQLiteDatabase.execSQL("update magazine_tb  set status = ?,percent = ?  where id in(       select id as tid from magazine_tb where status=?        order by time desc        limit (select count(*)  from magazine_tb where status= ?) offset ? )", arrayOfObject);
    return localArrayList;
  }

  public List<String> delMagazineExceptLatelyOne()
  {
    return delMagazineExceptLately(1);
  }

  public List<String> delMagazineExceptLatelyThree()
  {
    return delMagazineExceptLately(3);
  }

  public int getMagazineStatus(String paramString)
  {
    insertMagazine(paramString);
    Cursor localCursor = dbHelper.getWritableDatabase().rawQuery("select status from magazine_tb where url = ?", new String[] { paramString });
    boolean bool = localCursor.moveToNext();
    int i = 0;
    if (bool)
      i = localCursor.getInt(localCursor.getColumnIndex("status"));
    localCursor.close();
    return i;
  }

  public void insertMagazine(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = dbHelper.getWritableDatabase();
    Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*) from magazine_tb where url = ?", new String[] { paramString });
    localCursor.moveToFirst();
    if (localCursor.getInt(0) > 0)
      return;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(0);
    localSQLiteDatabase.execSQL("insert into magazine_tb(url,status,percent,time) values(?,?,0,datetime())", arrayOfObject);
  }

  public void updateMagazineStatus(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = dbHelper.getWritableDatabase();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    arrayOfObject[1] = paramString;
    localSQLiteDatabase.execSQL("update magazine_tb set status=? where url = ?", arrayOfObject);
  }
}