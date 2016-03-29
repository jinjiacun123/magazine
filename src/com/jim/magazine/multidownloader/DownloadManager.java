package com.jim.magazine.multidownloader;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.util.Log;

public class DownloadManager {
	public static final int MODE_DOWNLOAD_AUTO = 0;
	  public static final int MODE_DOWNLOAD_HAND = 1;
	  public static final int MODE_NOTIFY_PERCENT = 0;
	  public static final int MODE_NOTIFY_TIME = 1;
	  private static Map<String, DownloadManager> downloadManagerMap = new HashMap();
	  private DownloadLogManager downloadLogManager;
	  private int downloadMode = 1;
	  private ExecutorService downloadServer;
	  private Map<DownloadTask, Future> downloadTaskMap;
	  private IDownloadUpdate downloadUpdate;
	  private int errorRetryNum = 0;
	  private String id;
	  private int notifyFrequency = 1;
	  private int notifyMode = 1;

	  private DownloadManager(Context paramContext, String paramString, IDownloadUpdate paramIDownloadUpdate)
	  {
	    this.id = paramString;
	    this.downloadUpdate = paramIDownloadUpdate;
	    this.downloadServer = Executors.newFixedThreadPool(2);
	    this.downloadLogManager = DownloadLogManager.getInstance(paramContext, this);
	    this.downloadTaskMap = Collections.synchronizedMap(new HashMap());
	  }

	  private DownloadTask addTask(DownloadTask paramDownloadTask)
	  {
	    paramDownloadTask.setTaskState(1);
	    onUpdate(paramDownloadTask);
	    if (this.downloadServer.isShutdown())
	      this.downloadServer = Executors.newFixedThreadPool(2);
	    Future localFuture = this.downloadServer.submit(paramDownloadTask);
	    this.downloadTaskMap.put(paramDownloadTask, localFuture);
	    return null;
	  }

	  private boolean continueTask(DownloadTask paramDownloadTask)
	  {
	    addTask(paramDownloadTask);
	    return false;
	  }

	  public static DownloadManager getDownloadManager(Context paramContext, String paramString, IDownloadUpdate paramIDownloadUpdate)
	  {
	    if (downloadManagerMap.containsKey(paramString))
	    {
	      DownloadManager localDownloadManager2 = downloadManagerMap.get(paramString);
	      if (paramIDownloadUpdate != null)
	        localDownloadManager2.setDownloadUpdate(paramIDownloadUpdate);
	      return localDownloadManager2;
	    }
	    DownloadManager localDownloadManager1 = new DownloadManager(paramContext, paramString, paramIDownloadUpdate);
	    downloadManagerMap.put(paramString, localDownloadManager1);
	    return localDownloadManager1;
	  }

	  private DownloadTask getTask(String paramString)
	  {
	    return null;
	  }

	  private boolean pauseTask(DownloadTask paramDownloadTask)
	  {
	    paramDownloadTask.setTaskState(4);
	    return false;
	  }

	  private boolean restartTask(DownloadTask paramDownloadTask)
	  {
	    addTask(paramDownloadTask);
	    return false;
	  }

	  public DownloadTask buildDownloadTask(String paramString1, String paramString2, File paramFile)
	  {
	    Iterator localIterator = this.downloadTaskMap.entrySet().iterator();
	    while (localIterator.hasNext())
	    {
	      DownloadTask localDownloadTask2 = (DownloadTask)((Map.Entry)localIterator.next()).getKey();
	      if (paramString2.equals(localDownloadTask2.getUrl()))
	      {
	        localDownloadTask2.setDownloadManager(this);
	        localDownloadTask2.setDownloadLogManager(getDownloadLogManager());
	        return localDownloadTask2;
	      }
	    }
	    DownloadTask localDownloadTask1 = new DownloadTask(this, paramString1, paramString2, paramFile);
	    this.downloadTaskMap.put(localDownloadTask1, null);
	    return localDownloadTask1;
	  }

	  public void deleteTask(DownloadTask paramDownloadTask, boolean paramBoolean)
	  {
	    this.downloadLogManager.deleteTaskLog(paramDownloadTask);
	    if (paramBoolean);
	  }

	  public void doTask(DownloadTask paramDownloadTask)
	  {
	    Log.w("wjdlike", paramDownloadTask.getTaskState() + "");
	    if (paramDownloadTask.getTaskState() == 0)
	    	addTask(paramDownloadTask);
	    
	    do
	    {
	      //return;
	      if (paramDownloadTask.getTaskState() == 3)
	      {
	        pauseTask(paramDownloadTask);
	        return;
	      }
	      if (paramDownloadTask.getTaskState() == 4)
	      {
	        continueTask(paramDownloadTask);
	        return;
	      }
	    }
	    while (paramDownloadTask.getTaskState() != 6);
	    
	    restartTask(paramDownloadTask);
	  }

	  public DownloadLogManager getDownloadLogManager()
	  {
	    return this.downloadLogManager;
	  }

	  public int getDownloadMode()
	  {
	    return this.downloadMode;
	  }

	  public int getNotifyFrequency()
	  {
	    return this.notifyFrequency;
	  }

	  public int getNotifyMode()
	  {
	    return this.notifyMode;
	  }

	  public void initTask()
	  {
	    if (this.downloadMode == 0)
	    {
	      List localList2 = this.downloadLogManager.getRunningTask();
	      for (int j = 0; j < localList2.size(); j++)
	        initTask((DownloadTask)localList2.get(j));
	    }
	    List localList1 = this.downloadLogManager.getRunningTask();
	    for (int i = 0; i < localList1.size(); i++)
	      onUpdate((DownloadTask)localList1.get(i));
	  }

	  public void initTask(DownloadTask paramDownloadTask)
	  {
	    if (this.downloadMode == 0)
	      if ((paramDownloadTask.getTaskState() == 2) || (paramDownloadTask.getTaskState() == 3) || (paramDownloadTask.getTaskState() == 1))
	        addTask(paramDownloadTask);
	    while ((paramDownloadTask.getTaskState() != 2) && (paramDownloadTask.getTaskState() != 3) && (paramDownloadTask.getTaskState() != 1))
	      return;
	    paramDownloadTask.setTaskState(4);
	    this.downloadLogManager.updateTaskState(paramDownloadTask);
	    onUpdate(paramDownloadTask);
	  }

	  public boolean isExsit(String paramString)
	  {
	    Log.w("wjdlike", "准备下载:" + paramString);
	    Iterator localIterator = this.downloadTaskMap.entrySet().iterator();
	    while (localIterator.hasNext())
	      if (paramString.equals(((DownloadTask)((Map.Entry)localIterator.next()).getKey()).getUrl()))
	        return true;
	    return false;
	  }

	  protected void onUpdate(DownloadTask paramDownloadTask)
	  {
	    if ((paramDownloadTask.getTaskState() == 4) || (paramDownloadTask.getTaskState() == 6) || (paramDownloadTask.getTaskState() == 5))
	    {
	      Log.w("wjdlike", "下载完成:" + paramDownloadTask.getUrl());
	      this.downloadTaskMap.remove(paramDownloadTask);
	    }
	    try
	    {
	      if (this.downloadUpdate != null)
	      {
	        DownloadTask localDownloadTask = (DownloadTask)paramDownloadTask.clone();
	        localDownloadTask.setDownloadManager(null);
	        localDownloadTask.setDownloadLogManager(null);
	        this.downloadUpdate.onUpdate(localDownloadTask);
	        return;
	      }
	      Log.w("wjdlike", "downloadUpdate为NULL");
	      return;
	    }
	    catch (CloneNotSupportedException localCloneNotSupportedException)
	    {
	      localCloneNotSupportedException.printStackTrace();
	    }
	  }

	  public void setDownloadMode(int paramInt)
	  {
	    this.downloadMode = paramInt;
	  }

	  public void setDownloadUpdate(IDownloadUpdate paramIDownloadUpdate)
	  {
	    this.downloadUpdate = paramIDownloadUpdate;
	  }

	  public void setErrorRetryNum(int paramInt)
	  {
	    this.errorRetryNum = paramInt;
	  }

	  public void setNotifyMode(int paramInt1, int paramInt2)
	  {
	    if ((paramInt1 == 0) || (paramInt1 == 1))
	    {
	      this.notifyMode = paramInt1;
	      if ((paramInt2 >= 0) || (paramInt2 <= 10))
	        this.notifyFrequency = paramInt2;
	    }
	  }

	  public void shutDown()
	  {
	    this.downloadServer.shutdownNow();
	  }
}
