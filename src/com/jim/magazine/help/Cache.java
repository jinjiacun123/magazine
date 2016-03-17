package com.jim.magazine.help;

import android.database.Cursor;
import java.io.File;
import java.text.ParseException;

public class Cache {
	public static int memoryCacheSize = 2097152;
	private long expire;
	private File file;
	private long id;
	private String key;
	private long size;
	private int status;
	private long time;

	public long getExpire() {
		return this.expire;
	}

	public File getFile() {
		return this.file;
	}

	public long getId() {
		return this.id;
	}

	public String getKey() {
		return this.key;
	}

	public long getSize() {
		return this.size;
	}

	public int getStatus() {
		return this.status;
	}

	public long getTime() {
		return this.time;
	}

	public void parse(Cursor paramCursor) throws ParseException {
		setId(paramCursor.getLong(paramCursor.getColumnIndex("id")));
		if (paramCursor.getString(paramCursor.getColumnIndex("key")) != null)
			setKey(paramCursor.getString(paramCursor.getColumnIndex("key")));
		if (paramCursor.getString(paramCursor.getColumnIndex("file")) != null)
			setFile(new File(paramCursor.getString(paramCursor
					.getColumnIndex("file"))));
		if (paramCursor.getString(paramCursor.getColumnIndex("size")) != null)
			setSize(paramCursor.getLong(paramCursor.getColumnIndex("size")));
		setStatus(paramCursor.getInt(paramCursor.getColumnIndex("status")));
		setTime(paramCursor.getLong(paramCursor.getColumnIndex("time")));
		setExpire(paramCursor.getLong(paramCursor.getColumnIndex("expire")));
	}

	public void setExpire(long paramLong) {
		this.expire = paramLong;
	}

	public void setFile(File paramFile) {
		this.file = paramFile;
	}

	public void setId(long paramLong) {
		this.id = paramLong;
	}

	public void setKey(String paramString) {
		this.key = paramString;
	}

	public void setSize(long paramLong) {
		this.size = paramLong;
	}

	public void setStatus(int paramInt) {
		this.status = paramInt;
	}

	public void setTime(long paramLong) {
		this.time = paramLong;
	}
}