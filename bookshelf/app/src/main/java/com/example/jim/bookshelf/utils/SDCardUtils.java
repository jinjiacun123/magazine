package com.example.jim.bookshelf.utils;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;
/**
 * Created by jim on 16-3-19.
 */
public class SDCardUtils
{
    public static boolean ExistSDCard()
    {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static int getSDAllSize()
    {
        StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (int)(localStatFs.getBlockSize() * localStatFs.getBlockCount() / 1024L / 1024L);
    }

    public static int getSDFreeSize()
    {
        StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (int)(localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1024L / 1024L);
    }
}
