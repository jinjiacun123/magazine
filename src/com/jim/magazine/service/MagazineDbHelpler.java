package com.jim.magazine.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MagazineDbHelpler extends SQLiteOpenHelper
{
  private static final String MAGAZINE_DB = "magazine";
  public static final String MAGAZINE_TABLE = "magazine_tb";
  private static final int VERSION = 200;
  public static String initSQL = "CREATE TABLE IF NOT EXISTS magazine_tb (    id INTEGER PRIMARY KEY,     name NVARCHAR(255),     url NVARCHAR(255),     status INTEGER,     percent NVARCHAR(255),     time timestamp  );";
  public static String updateDataSQL = "update magazine_tb set status = 0 where status !=5 and status !=6 and status !=7 and status !=8 ;update magazine_tb set status = 1 where status = 5 ;update magazine_tb set status = 1 where status = 6 ;update magazine_tb set status = 2 where status = 7 ;update magazine_tb set status = 3 where status = 8 ;";

  public MagazineDbHelpler(Context paramContext)
  {
    super(paramContext, "magazine", null, 200);
  }

  @Override
public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL(initSQL);
  }

  @Override
public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    if (paramInt1 <= 200)
      for (String str : updateDataSQL.split(";"))
        paramSQLiteDatabase.execSQL(str + ";");
  }
}