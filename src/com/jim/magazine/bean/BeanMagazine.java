package com.jim.magazine.bean;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jim.magazine.multidownloader.DownloadTask;
import com.jim.magazine.service.MagazineDbService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanMagazine implements Serializable {
	public static final int OPERATE_STATE_NULL = 0;
	public static final int OPERATE_STATE_UNZIPING = 1;
	public static final int OPERATE_STATE_UNZIP_FAILED_SPACE = 3;
	public static final int OPERATE_STATE_UNZIP_FAILED_ZIP = 4;
	public static final int OPERATE_STATE_UNZIP_SUCCESS = 2;
	public static final int STATE_EDIT = 1;
	public static final int STATE_SHOW = 0;
	private List<BeanArticle> articleList;

	@Expose
	@SerializedName("base_url")
	private String baseUrl;

	@Expose
	@SerializedName("base_url_prefix")
	private String baseUrlPrefix;

	@Expose
	private String bestTopicUrl;

	@Expose
	private String bookDownloadStatus;

	@Expose
	private String cover;
	public int currentOperateState = 0;
	private int currentViewState = 0;

	@Expose
	private String deviceType;
	private String dirPath;

	@Expose
	@SerializedName("dir-ver")
	private String dirVer;
	private int downloadId;
	private DownloadTask downloadTask;
	private float download_length;
	private String download_percent;
	private float download_time;

	@Expose
	private String id;
	private boolean isDel;

	@Expose
	private String issue;

	@Expose
	private String magazine;

	@Expose
	private String md5;
	public Map<String, String> orderNum = new HashMap();
	private int position;

	@Expose
	private String publishTime;

	@Expose
	private String publisher;
	private String save_path;

	@Expose
	private String size;
	private int state;

	@Expose
	private String summary;

	@Expose
	private String thumb;
	private String time;
	private String unzipName;

	@Expose
	private String url;

	@Expose
	private String volume;
	private String zipPath;

	public BeanArticle getArticle(int paramInt) {
		if ((paramInt < 0) || (paramInt > -1 + this.articleList.size()))
			return null;
		return (BeanArticle) this.articleList.get(paramInt);
	}

	public List<BeanArticle> getArticleList() {
		return this.articleList;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public String getBaseUrlPrefix() {
		return this.baseUrlPrefix;
	}

	public String getBestTopicUrl() {
		return this.bestTopicUrl;
	}

	public String getBookDownloadStatus() {
		return this.bookDownloadStatus;
	}

	public String getCover() {
		return this.cover;
	}

	public int getCurrentOperateState() {
		return this.currentOperateState;
	}

	public int getCurrentOperateStateFromDb(Context paramContext) {
		return MagazineDbService.getInstence(paramContext).getMagazineStatus(
				this.downloadTask.getUrl());
	}

	public int getCurrentViewState() {
		return this.currentViewState;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public String getDirPath() {
		return this.dirPath;
	}

	public String getDirVer() {
		return this.dirVer;
	}

	public int getDownloadId() {
		return this.downloadId;
	}

	public DownloadTask getDownloadTask() {
		return this.downloadTask;
	}

	public float getDownload_length() {
		return this.download_length;
	}

	public String getDownload_percent() {
		return this.download_percent;
	}

	public float getDownload_time() {
		return this.download_time;
	}

	public String getId() {
		return this.id;
	}

	public String getIssue() {
		return this.issue;
	}

	public String getMagazine() {
		return this.magazine;
	}

	public String getMagazineFileName() {
		return this.url.substring(1 + this.url.lastIndexOf('/'));
	}

	public int getMagazine_size() {
		return Math.round(Float.parseFloat(this.size.replace("MB", "")));
	}

	public String getMd5() {
		return this.md5;
	}

	public Map<String, String> getOrderNum() {
		return this.orderNum;
	}

	public int getPosition() {
		return this.position;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public String getSave_path() {
		return this.save_path;
	}

	public String getSize() {
		return this.size;
	}

	public int getState() {
		return this.state;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getThumb() {
		return this.thumb;
	}

	public String getUnzipName() {
		return this.unzipName;
	}

	public String getUrl() {
		return this.url;
	}

	public String getVolume() {
		return this.volume;
	}

	public String getYear() {
		return this.publishTime.substring(0, 4);
	}

	public String getZipPath() {
		return this.zipPath;
	}

	public boolean isDel() {
		return this.isDel;
	}

	public void setArticleList(List<BeanArticle> paramList) {
		this.articleList = paramList;
	}

	public void setBaseUrl(String paramString) {
		this.baseUrl = paramString;
	}

	public void setBaseUrlPrefix(String paramString) {
		this.baseUrlPrefix = paramString;
	}

	public void setBestTopicUrl(String paramString) {
		this.bestTopicUrl = paramString;
	}

	public void setBookDownloadStatus(String paramString) {
		this.bookDownloadStatus = paramString;
	}

	public void setCover(String paramString) {
		this.cover = paramString;
	}

	public void setCurrentOperateState(int paramInt) {
		this.currentOperateState = paramInt;
	}

	public void setCurrentViewState(int paramInt) {
		this.currentViewState = paramInt;
	}

	public void setDel(boolean paramBoolean) {
		this.isDel = paramBoolean;
	}

	public void setDeviceType(String paramString) {
		this.deviceType = paramString;
	}

	public void setDirPath(String paramString) {
		this.dirPath = paramString;
	}

	public void setDirVer(String paramString) {
		this.dirVer = paramString;
	}

	public void setDownloadId(int paramInt) {
		this.downloadId = paramInt;
	}

	public void setDownloadTask(DownloadTask paramDownloadTask) {
		this.downloadTask = paramDownloadTask;
	}

	public void setDownload_length(float paramFloat) {
		this.download_length = paramFloat;
	}

	public void setDownload_percent(String paramString) {
		this.download_percent = paramString;
	}

	public void setDownload_time(float paramFloat) {
		this.download_time = paramFloat;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setIssue(String paramString) {
		this.issue = paramString;
	}

	public void setMagazine(String paramString) {
		this.magazine = paramString;
	}

	public void setMd5(String paramString) {
		this.md5 = paramString;
	}

	public void setOrderNum(Map<String, String> paramMap) {
		this.orderNum = paramMap;
	}

	public void setPosition(int paramInt) {
		this.position = paramInt;
	}

	public void setPublishTime(String paramString) {
		this.publishTime = paramString;
	}

	public void setPublisher(String paramString) {
		this.publisher = paramString;
	}

	public void setSave_path(String paramString) {
		this.save_path = paramString;
	}

	public void setSize(String paramString) {
		this.size = paramString;
	}

	public void setState(int paramInt) {
		this.state = paramInt;
	}

	public void setSummary(String paramString) {
		this.summary = paramString;
	}

	public void setThumb(String paramString) {
		this.thumb = paramString;
	}

	public void setTime(String paramString) {
		this.time = paramString;
	}

	public void setUnzipName(String paramString) {
		this.unzipName = paramString;
	}

	public void setUrl(String paramString) {
		this.url = paramString;
	}

	public void setVolume(String paramString) {
		this.volume = paramString;
	}

	public void setZipPath(String paramString) {
		this.zipPath = paramString;
	}
}