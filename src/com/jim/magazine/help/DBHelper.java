package com.jim.magazine.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String CACHE_TABLE = "app_cache";
	private static final String TAG = DBHelper.class.getSimpleName();
	private static String dbName = "app.db";
	private static String initSQL = "CREATE TABLE IF NOT EXISTS app_cache (    id INTEGER PRIMARY KEY,     key NVARCHAR(255),     file NVARCHAR(255),     size NUMERIC,     status INTEGER,     time NUMERIC,     expire NUMERIC);";
	private static String upgradeSQL = initSQL;

	public DBHelper(Context paramContext, int paramInt, String paramString1,
			String paramString2, String paramString3) {
		super(paramContext, paramString1, null, paramInt);
		if ((paramString2 != null) && (!paramString2.trim().equals(""))) {
			initSQL += paramString2;
			upgradeSQL += paramString2;
		}
		if ((paramString3 != null) && (!paramString3.trim().equals("")))
			upgradeSQL += paramString3;
	}

	@Override
	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		Log.i(TAG, "Initialize database");
		String[] arrayOfString = initSQL.split(";");
		int i = arrayOfString.length;
		for (int j = 0;; j++) {
			if (j >= i)
				return;
			String str = arrayOfString[j];
			Log.i(TAG, "execSQL: " + str + ";");
			paramSQLiteDatabase.execSQL(str + ";");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		Log.i(TAG, "Upgrade database");
		String[] arrayOfString = upgradeSQL.split(";");
		int i = arrayOfString.length;
		for (int j = 0;; j++) {
			if (j >= i) {
				onCreate(paramSQLiteDatabase);
				return;
			}
			String str = arrayOfString[j];
			Log.i(TAG, "execSQL: " + str + ";");
			paramSQLiteDatabase.execSQL(str + ";");
		}
	}
}