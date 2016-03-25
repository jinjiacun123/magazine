package com.example.jim.bookshelf.config;

import android.os.Environment;
import java.io.File;
/**
 * Created by jim on 16-3-24.
 */
public class Env
{
    public static int ImageWidth;
    public static String appDirName;
    public static String appDirPath;
    public static String cameraImagePath;
    public static String downloadApkPath;
    public static boolean isFirstIn;
    public static String screenPicPath;
    public static String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String unzipFilePath;
    public static String zipFilePath;

    static
    {
        appDirName = "bookshelf";
        appDirPath = sdCardPath + "/" + appDirName + "/";
        zipFilePath = appDirPath + "zipFiles/";
        unzipFilePath = appDirPath + "unZipFiles/";
        screenPicPath = appDirPath + "screenpicfiles/";
        downloadApkPath = appDirPath + "apk/";
        cameraImagePath = appDirPath + "cameraImage/";
        File localFile1 = new File(cameraImagePath);
        if (!localFile1.exists())
            localFile1.mkdirs();
        File localFile2 = new File(zipFilePath);
        if (!localFile2.exists())
            localFile2.mkdirs();
        File localFile3 = new File(unzipFilePath);
        if (!localFile3.exists())
            localFile3.mkdirs();
        File localFile4 = new File(screenPicPath);
        if (!localFile4.exists())
            localFile4.mkdirs();
        File localFile5 = new File(downloadApkPath);
        if (!localFile5.exists())
            localFile5.mkdir();
    }
}
