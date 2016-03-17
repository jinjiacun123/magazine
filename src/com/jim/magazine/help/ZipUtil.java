package com.jim.magazine.help;

import android.os.Handler;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipUtil
{
  public static final int UNZIP_DONE = 0;
  public static final int UNZIP_FAIL = 1;
  public static final int UNZIP_FILE_NOT_VALID = 2;

  public static void unzipFile(String filePath, final String destPath, final Handler handler)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          ZipFile localZipFile = new ZipFile(destPath);
          if (!localZipFile.isValidZipFile())
          {
            if (handler != null)
            	handler.sendEmptyMessage(2);
          }
          else
          {
            localZipFile.extractAll(destPath);
            if (handler != null)
            {
            	handler.sendEmptyMessage(0);
              return;
            }
          }
        }
        catch (ZipException localZipException)
        {
          localZipException.printStackTrace();
          if (handler != null)
        	  handler.sendEmptyMessage(1);
        }
      }
    }).start();
  }
}