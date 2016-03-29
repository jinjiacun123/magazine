package com.jim.magazine;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public abstract class CommonApplication extends Application
{
  protected float density;
  private boolean initDirMode = true;
  protected String installChannel;
  protected String packageName;
  protected String schema;
  protected int screenHeight;
  protected int screenWidth;
  protected String sdName;
  protected int statusBarHeight;
  protected int versionCode;
  protected String versionName;

  private void getAppVersionInfor()
  {
    try
    {
      PackageInfo localPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 16384);
      this.packageName = localPackageInfo.packageName;
      this.versionCode = localPackageInfo.versionCode;
      this.versionName = localPackageInfo.versionName;
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
  }

  private void getInstallChannel()
  {
    try
    {
      this.installChannel = ((String)getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.get("MOFANG_CHANNEL"));
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
  }

  private int getStatusBarHeight()
  {
    try
    {
      Class localClass = Class.forName("com.android.internal.R$dimen");
      Object localObject = localClass.newInstance();
      int i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      int j = getResources().getDimensionPixelSize(i);
      return j;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  private void initDisplayMetrics()
  {
    int i = ((WindowManager)getApplicationContext().getSystemService("window")).getDefaultDisplay().getRotation();
    DisplayMetrics localDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();
    int j;
    if (i == 0)
    {
      j = localDisplayMetrics.widthPixels;
      this.screenWidth = j;
    }
    else
    {
    	for (int k = localDisplayMetrics.heightPixels; ; k = localDisplayMetrics.heightPixels)
    	{
	    		this.screenHeight = k;
	    		this.density = localDisplayMetrics.density;
	    		j = localDisplayMetrics.heightPixels;
	    		break;
    	}
    }
  }

  @Override
public void onCreate()
  {
    super.onCreate();
    getAppVersionInfor();
    getInstallChannel();
    initDisplayMetrics();
    this.statusBarHeight = getStatusBarHeight();
  }

  @Override
public void onLowMemory()
  {
    super.onLowMemory();
  }

  @Override
public void onTerminate()
  {
    super.onTerminate();
  }

  protected void setSchema(String paramString)
  {
    this.schema = paramString;
  }

  protected void setSdName(String paramString)
  {
    this.sdName = paramString;
  }

  protected void unableInitDir()
  {
    this.initDirMode = false;
  }
}