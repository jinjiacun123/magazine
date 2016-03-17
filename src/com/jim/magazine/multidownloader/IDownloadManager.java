package com.jim.magazine.multidownloader;

public abstract interface IDownloadManager
{
  public abstract DownloadTask addTask(DownloadTask paramDownloadTask);

  public abstract boolean continueTask(DownloadTask paramDownloadTask);

  public abstract void deleteTask(DownloadTask paramDownloadTask, boolean paramBoolean);

  public abstract DownloadTask getTask(String paramString);

  public abstract boolean pauseTask(DownloadTask paramDownloadTask);

  public abstract boolean restartTask(DownloadTask paramDownloadTask);
}
