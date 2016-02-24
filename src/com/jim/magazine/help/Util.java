package com.jim.magazine.help;

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
}
