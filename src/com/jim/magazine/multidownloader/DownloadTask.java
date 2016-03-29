package com.jim.magazine.multidownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

import com.jim.magazine.help.NumUtils;

public class DownloadTask implements
Callable<String>, Serializable, Cloneable {
	private static final int DEFAULT_MAX_CONNECTIONS = 10;
	  private static final int DEFAULT_MAX_RETRIES = 5;
	  private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	  private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
	  public static final int TASK_BEGIN = 2;
	  public static final int TASK_FAIL = 6;
	  public static final int TASK_NULL = 0;
	  public static final int TASK_PAUSE = 4;
	  public static final int TASK_PAUSE_IN = 7;
	  public static final int TASK_RUNNING = 3;
	  public static final int TASK_SUCCESS = 5;
	  public static final int TASK_WAIT = 1;
	  private static final String VERSION = "1.0.0";
	  private DownloadLogManager downloadLogManager;
	  private DownloadManager downloadManager;
	  private String downloadTime;
	  private String downloaderId;
	  private long fileDownSize;
	  private File filePath;
	  private long fileTotalSize;
	  private String id;
	  private long lastDownloadSize;
	  private long lastTime;
	  private String name;
	  private int overplusTimeByMin;
	  private int overplusTimeBySec;
	  private int percent = 0;
	  private int speedByKb;
	  private float speedByMb;
	  private int taskState = 0;
	  private File unZipFilePath;
	  private String url;

	  public DownloadTask(DownloadManager paramDownloadManager, String paramString1, String paramString2, File paramFile)
	  {
	    this.downloadManager = paramDownloadManager;
	    this.downloadLogManager = paramDownloadManager.getDownloadLogManager();
	    this.id = paramString1;
	    this.url = paramString2;
	    this.filePath = paramFile;
	    this.taskState = this.downloadLogManager.getTaskState(this);
	    this.fileDownSize = this.downloadLogManager.getTaskProgress(this);
	    this.fileTotalSize = this.downloadLogManager.getTaskTotalSize(this);
	    this.percent = this.downloadLogManager.getTaskDownloadPercent(this);
	  }

	private HttpClient getHttpClient() {
		BasicHttpParams localBasicHttpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(localBasicHttpParams, 10000L);
		ConnManagerParams.setMaxConnectionsPerRoute(localBasicHttpParams,
				new ConnPerRouteBean(10));
		ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 10);
		HttpConnectionParams.setSoTimeout(localBasicHttpParams, 10000);
		HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
		HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
		HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 8192);
		HttpProtocolParams.setVersion(localBasicHttpParams,
				HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(localBasicHttpParams, String.format(
				"android-async-http/%s (http://loopj.com/android-async-http)",
				new Object[] { "1.0.0" }));
		SchemeRegistry localSchemeRegistry = new SchemeRegistry();
		localSchemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		localSchemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		return new DefaultHttpClient(new ThreadSafeClientConnManager(
				localBasicHttpParams, localSchemeRegistry),
				localBasicHttpParams);
	}

	  @Override
	public String call()
	  {
		HttpGet localHttpGet = new HttpGet(this.url);
		if (this.downloadManager != null)
			this.downloadLogManager = this.downloadManager
					.getDownloadLogManager();

		this.downloadLogManager.createTaskLog(this);
		localHttpGet.addHeader("RANGE", "bytes=" + this.fileDownSize + "-");
		InputStream localInputStream = null;
		RandomAccessFile localRandomAccessFile=null;
		try {
			localRandomAccessFile = new RandomAccessFile(
					getFilePath(), "rwd");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			localRandomAccessFile.seek(this.fileDownSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    while (true)
	    {
	      try
	      {
	        HttpEntity localHttpEntity = getHttpClient().execute(localHttpGet).getEntity();
	        boolean bool = this.fileTotalSize < 0L;
	        localInputStream = null;
				if (!bool) {
					this.fileTotalSize = localHttpEntity.getContentLength();
					setFileTotalSize(this.fileTotalSize);
					this.downloadLogManager.updateTotalSize(this);
				}
	        localInputStream = null;
	        if (localHttpEntity == null)
	        	break;
	        setTaskState(2);
	        this.downloadManager.onUpdate(this);
	        this.downloadLogManager.updateTaskState(this);
	        localInputStream = localHttpEntity.getContent();
	        byte[] arrayOfByte = new byte[10240];
	        setTaskState(3);
	        this.downloadManager.onUpdate(this);
	        this.downloadLogManager.updateTaskState(this);
	        if (this.taskState == 4)
	          break;
	        int i = localInputStream.read(arrayOfByte);
	        if (i == -1)
	          break;
	        localRandomAccessFile.write(arrayOfByte, 0, i);
	        this.fileDownSize += i;
	        this.downloadLogManager.updateTaskProgress(this);
				if (this.downloadManager.getNotifyMode() == 0) {
					int k = NumUtils.computePercent(this.fileDownSize,
							this.fileTotalSize);
					if (k - this.percent >= this.downloadManager
							.getNotifyFrequency()) {
						this.percent = k;
						if (this.lastTime == 0L)
							this.lastTime = System.currentTimeMillis();
					} else {
						Thread.sleep(1L);
						continue;
					}
				}
	      }
		 catch (InterruptedException localInterruptedException) {
						// return null;
						int m = (int) (System.currentTimeMillis() - this.lastTime);
						this.speedByKb = ((int) ((this.fileDownSize - this.lastDownloadSize) / m));
						this.speedByMb = ((this.fileDownSize - this.lastDownloadSize) / m / 1024.0F);
						this.speedByMb = new BigDecimal(this.speedByMb).setScale(2, 4)
								.floatValue();
						if (this.speedByKb != 0) {
							this.overplusTimeBySec = ((int) (this.fileTotalSize - this.fileDownSize)
									/ this.speedByKb / 1024);
							this.overplusTimeByMin = (this.overplusTimeBySec / 60);
						}
						this.lastTime = System.currentTimeMillis();
						this.lastDownloadSize = this.fileDownSize;
						this.downloadManager.onUpdate(this);
						continue;
					}
		 catch (Exception localException) {
						localException.printStackTrace();
						setTaskState(6);
						this.downloadLogManager.updateTaskState(this);
						this.downloadManager.onUpdate(this);
						try {
							localInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							localRandomAccessFile.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (this.taskState != 4)
							continue;
						this.downloadLogManager.updateTaskState(this);
						this.downloadManager.onUpdate(this);
						// continue;
						this.percent = NumUtils.computePercent(this.fileDownSize,
								this.fileTotalSize);
						if (this.lastTime == 0L) {
							this.lastTime = System.currentTimeMillis();
							continue;
						}
					}
		 finally {
						try {
							localInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							localRandomAccessFile.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (this.taskState == 4) {
							this.downloadLogManager.updateTaskState(this);
							this.downloadManager.onUpdate(this);
						}
					}
	      
	      	int j = (int)(System.currentTimeMillis() - this.lastTime);
			if ((j - 1000) / 1000 >= this.downloadManager.getNotifyFrequency()) {
				this.speedByKb = ((int) ((this.fileDownSize - this.lastDownloadSize) / j));
				this.speedByMb = ((this.fileDownSize - this.lastDownloadSize) / j / 1024.0F);
				this.speedByMb = new BigDecimal(this.speedByMb).setScale(2, 4)
						.floatValue();
				if (this.speedByKb != 0) {
					this.overplusTimeBySec = ((int) (this.fileTotalSize - this.fileDownSize)
							/ this.speedByKb / 1024);
					this.overplusTimeByMin = (this.overplusTimeBySec / 60);
				}
				this.lastTime = System.currentTimeMillis();
				this.lastDownloadSize = this.fileDownSize;
				this.downloadManager.onUpdate(this);
			}
	    }
		if (this.taskState == 4) {
			this.downloadLogManager.updateTaskState(this);
			this.downloadManager.onUpdate(this);
		}
		while (true) {
			label842:
				try {
					localInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					localRandomAccessFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (this.taskState != 4)
				break;
			this.downloadLogManager.updateTaskState(this);
			this.downloadManager.onUpdate(this);
			// break;
			setTaskState(5);
			this.downloadLogManager.updateTaskState(this);
			this.downloadManager.onUpdate(this);
		}
		return downloadTime;
	  }

	  @Override
	protected Object clone()
	    throws CloneNotSupportedException
	  {
	    return super.clone();
	  }

	  public String getDownloadTime()
	  {
	    return this.downloadTime;
	  }

	  public String getDownloaderId()
	  {
	    return this.downloaderId;
	  }

	  public long getFileDownSize()
	  {
	    return this.fileDownSize;
	  }

	  public File getFilePath()
	  {
	    return this.filePath;
	  }

	  public long getFileTotalSize()
	  {
	    return this.fileTotalSize;
	  }

	  public String getId()
	  {
	    return this.id;
	  }

	  public long getLastDownloadSize()
	  {
	    return this.lastDownloadSize;
	  }

	  public long getLastTime()
	  {
	    return this.lastTime;
	  }

	  public String getName()
	  {
	    return this.name;
	  }

	  public int getOverplusTimeByMin()
	  {
	    return this.overplusTimeByMin;
	  }

	  public int getOverplusTimeBySec()
	  {
	    return this.overplusTimeBySec;
	  }

	  public int getPercent()
	  {
	    return this.percent;
	  }

	  public int getSpeedByKb()
	  {
	    return this.speedByKb;
	  }

	  public float getSpeedByMb()
	  {
	    return this.speedByMb;
	  }

	  public int getTaskState()
	  {
	    return this.taskState;
	  }

	  public File getUnZipFilePath()
	  {
	    return this.unZipFilePath;
	  }

	  public String getUrl()
	  {
	    return this.url;
	  }

	  public void setDownloadLogManager(DownloadLogManager paramDownloadLogManager)
	  {
	    this.downloadLogManager = paramDownloadLogManager;
	  }

	  public void setDownloadManager(DownloadManager paramDownloadManager)
	  {
	    this.downloadManager = paramDownloadManager;
	  }

	  public void setDownloadTime(String paramString)
	  {
	    this.downloadTime = paramString;
	  }

	  public void setDownloaderId(String paramString)
	  {
	    this.downloaderId = paramString;
	  }

	  public void setFileDownSize(long paramLong)
	  {
	    this.fileDownSize = paramLong;
	  }

	  public void setFilePath(File paramFile)
	  {
	    this.filePath = paramFile;
	  }

	  public void setFileTotalSize(long paramLong)
	  {
	    this.fileTotalSize = paramLong;
	  }

	  public void setId(String paramString)
	  {
	    this.id = paramString;
	  }

	  public void setLastDownloadSize(long paramLong)
	  {
	    this.lastDownloadSize = paramLong;
	  }

	  public void setLastTime(long paramLong)
	  {
	    this.lastTime = paramLong;
	  }

	  public void setName(String paramString)
	  {
	    this.name = paramString;
	  }

	  public void setOverplusTimeByMin(int paramInt)
	  {
	    this.overplusTimeByMin = paramInt;
	  }

	  public void setOverplusTimeBySec(int paramInt)
	  {
	    this.overplusTimeBySec = paramInt;
	  }

	  public void setPercent(int paramInt)
	  {
	    this.percent = paramInt;
	  }

	  public void setSpeedByKb(int paramInt)
	  {
	    this.speedByKb = paramInt;
	  }

	  public void setSpeedByMb(float paramFloat)
	  {
	    this.speedByMb = paramFloat;
	  }

	  public void setTaskState(int paramInt)
	  {
	    this.taskState = paramInt;
	  }

	  public void setUnZipFilePath(File paramFile)
	  {
	    this.unZipFilePath = paramFile;
	  }

	  public void setUrl(String paramString)
	  {
	    this.url = paramString;
	  }
}
