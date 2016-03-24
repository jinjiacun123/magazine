package com.example.jim.bookshelf.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.example.jim.bookshelf.bean.Magazine;
import com.example.jim.bookshelf.multidownloader.DownloadManager;
import com.example.jim.bookshelf.multidownloader.DownloadTask;
import com.example.jim.bookshelf.multidownloader.IDownloadUpdate;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * Created by jim on 16-3-19.
 */
public class MagazineDownloadService extends Service
{
    public static DownloadManager downloadManager;
    private IDownloadUpdate iDownloadUpdate = new IDownloadUpdate()
    {
        public void onUpdate(DownloadTask paramAnonymousDownloadTask)
        {
            MagazineDownloadService.this.doDownloadTask(paramAnonymousDownloadTask);
        }
    };
    private int mPosition;
    private NotificationManager notificationManager;

    private void doDownloadTask(DownloadTask paramDownloadTask)
    {
        if (paramDownloadTask.getTaskState() == 5)
        {
            unZipFile(paramDownloadTask);
            return;
        }
        Magazine localMagazine = new Magazine();
        localMagazine.setDownloadTask(paramDownloadTask);
        sendBroadCast(localMagazine);
    }

    private void doMagazine(Magazine paramMagazine)
    {
        if (paramMagazine.getCurrentOperateState() == 0)
            downloadManager.doTask(paramMagazine.getDownloadTask());
    }

    private void initDoMagazine()
    {
        /*Iterator localIterator = MagazineApplication.sMagazineMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
            Magazine localMagazine = (Magazine)((Map.Entry)localIterator.next()).getValue();
            if ((localMagazine.getCurrentOperateState() != 0) && (localMagazine.getCurrentOperateState() != 2))
            {
                unZipFile(localMagazine.getDownloadTask());
            }
            else
            {
                localMagazine.getDownloadTask().setDownloadManager(downloadManager);
                downloadManager.initTask(localMagazine.getDownloadTask());
            }
        }*/
    }

    private void sendBroadCast(Magazine paramMagazine)
    {
        Intent localIntent = new Intent();
        Bundle localBundle = new Bundle();
        paramMagazine.setPosition(this.mPosition);
        localBundle.putSerializable("notifyMagazine", paramMagazine);
        localIntent.putExtras(localBundle);
        localIntent.setAction("cn.com.pcgroup.magazine.magazinereceiver");
        sendBroadcast(localIntent);
    }

    private void sendUnzipNotify(Magazine paramMagazine)
    {
        /*if (MagazineApplication.sBackProcess)
        {
            this.notificationManager = ((NotificationManager)getSystemService("notification"));
            Notification localNotification = new NotificationCompat.Builder(this).setTicker("家居杂志:下载完成").setContentTitle("家居杂志").setContentText(paramMagazine.getIssue() + "已下载完成").setSmallIcon(2130837659).build();
            this.notificationManager.notify(2, localNotification);
        }*/
    }

    private void unZipFile(final DownloadTask paramDownloadTask)
    {
        /*final Magazine localMagazine = (Magazine)MagazineApplication.sMagazineMap.get(paramDownloadTask.getUrl());
        localMagazine.setDownloadTask(paramDownloadTask);
        localMagazine.setCurrentOperateState(1);
        MagazineDbService.getInstence(this).updateMagazineStatus(paramDownloadTask.getUrl(), 1);
        sendBroadCast(localMagazine);
        ZipUtil.unzipFile(paramDownloadTask.getFilePath().getAbsolutePath(), localMagazine.getDirPath(), new Handler(getMainLooper())
        {
            public void handleMessage(Message paramAnonymousMessage)
            {
                switch (paramAnonymousMessage.what)
                {
                    default:
                        return;
                    case 0:
                        MagazineDownloadService.this.sendUnzipNotify(localMagazine);
                        localMagazine.setCurrentOperateState(2);
                        MagazineDbService.getInstence(MagazineDownloadService.this).updateMagazineStatus(paramDownloadTask.getUrl(), 2);
                        MagazineDownloadService.this.sendBroadCast(localMagazine);
                        return;
                    case 1:
                        localMagazine.setCurrentOperateState(3);
                        MagazineDbService.getInstence(MagazineDownloadService.this).updateMagazineStatus(paramDownloadTask.getUrl(), 3);
                        MagazineDownloadService.this.sendBroadCast(localMagazine);
                        return;
                    case 2:
                }
                localMagazine.setCurrentOperateState(4);
                MagazineDbService.getInstence(MagazineDownloadService.this).updateMagazineStatus(paramDownloadTask.getUrl(), 4);
                MagazineDownloadService.this.sendBroadCast(localMagazine);
            }
        });*/
    }

    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        downloadManager = DownloadManager.getDownloadManager(this, "magazine", this.iDownloadUpdate);
        downloadManager.setDownloadMode(1);
        downloadManager.setNotifyMode(1, 1);
    }

    public void onDestroy()
    {
        super.onDestroy();
        downloadManager.shutDown();
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        super.onStartCommand(paramIntent, paramInt1, paramInt2);
        Bundle localBundle = paramIntent.getExtras();
        if (localBundle.getInt("type") == 0)
            initDoMagazine();
        /*while (true)
        {
            String str1 = localBundle.getString("id");
            String str2 = localBundle.getString("taskUrl");
            File localFile = (File)localBundle.getSerializable("filePath");
            this.mPosition = localBundle.getInt("position");
            DownloadTask localDownloadTask = downloadManager.buildDownloadTask(str1, str2, localFile);
            Magazine localMagazine = (Magazine)MagazineApplication.sMagazineMap.get(localDownloadTask.getUrl());
            localMagazine.setDownloadTask(localDownloadTask);
            doMagazine(localMagazine);
            return 2;
        }*/
        return 0;
    }
}
