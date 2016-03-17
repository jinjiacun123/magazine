package com.jim.magazine;

import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.jim.magazine.bean.BeanMagazine;
import com.jim.magazine.help.DBHelper;
import com.jim.magazine.help.StorageUtils;

public class MagazineApplication extends CommonApplication
{
  public static boolean sBackProcess = true;
  public static LinkedHashMap<String, BeanMagazine> sMagazineMap = new LinkedHashMap();
  public static ValueCallback<Uri> sUploadMessage;

  private void initImageLoader()
  {
    StorageUtils.getCacheDirectory(this);
  }

  private void initdb()
  {
    new DBHelper(this, 400, "Magazine.db", "", "");
  }

  public void onCreate()
  {
    super.onCreate();
    initdb();
    initImageLoader();
  }
}