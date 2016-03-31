package com.jim.magazine.help;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;


public class Util {	
	//登陆是否跳转注册页状态
	public static boolean getRegisterType(Context context){
		return context.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE).getBoolean("type", false);
	}
	
	//修改状态
	public static boolean UpdateRegisterType(Context context,boolean type){
		return context.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE).edit().putBoolean("type", type).commit();
	}
	
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
}
