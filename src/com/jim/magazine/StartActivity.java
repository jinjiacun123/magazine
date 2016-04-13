package com.jim.magazine;

import com.jim.magazine.R;
import com.jim.magazine.fragment.AppManager;
import com.jim.magazine.test.GetMagazine;
import com.jim.magazine.test.Animation;
import com.jim.magazine.test.GetRemote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 启动页
 * 
 * author:jim
 * 
 * 
 * */

public class StartActivity extends Activity{
	private AppManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startpage);
		manager = AppManager.getInstance();
		manager.addActivity(StartActivity.this);
		new MyThread().start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				
				//引导到登录界面
				/*
				Intent intent=new Intent(StartActivity.this,LoginActivity.class);
				startActivity(intent);
				*/
				
				//临时测试
				int type = 2;
				switch(type)
				{
					case 0://到注册
					{	
						Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
						startActivity(intent);
					}
					break;
					case 1://到登录
					{
						Intent intent=new Intent(StartActivity.this,LoginActivity.class);
						startActivity(intent);
					}
					break;
					case 2://主页
					{
						Intent intent=new Intent(StartActivity.this,MainActivity.class);
						startActivity(intent);
					}
					break;
					case 3://滑动菜单
					{
						Intent intent=new Intent(StartActivity.this,SlidingActivity.class);
						startActivity(intent);
					}
					break;
					case 4://欢迎界面
					{
						Intent intent=new Intent(StartActivity.this,WelcomeActivity.class);
						startActivity(intent);
					}
					break;
					case 5:
					{
						Intent intent=new Intent(StartActivity.this,MainActivity.class);
						startActivity(intent);
					}
					break;
					case 6://视频
					{
						Intent intent=new Intent(StartActivity.this,VideoActivity.class);
						startActivity(intent);
					}
					break;
					case 7://杂志下载
					{
						Intent intent=new Intent(StartActivity.this, GetMagazine.class);
						startActivity(intent);
					}
						break;
					case 8://动画效果
					{
						Intent intent=new Intent(StartActivity.this, Animation.class);
						startActivity(intent);
					}
						break;
					case 9://下拉刷新
					{
						Intent intent=new Intent(StartActivity.this, TestActivity.class);
						startActivity(intent);
					}
						break;	
					case 10://设置
					{
						Intent intent=new Intent(StartActivity.this, SettingActivity.class);
						startActivity(intent);
					}
					break;
					case 11://关于我们
					{
						Intent intent=new Intent(StartActivity.this, AboutActivity.class);
						startActivity(intent);
					}
					break;
					case 12://测试获取远程图片
					{
						Intent intent=new Intent(StartActivity.this, GetRemote.class);
						startActivity(intent);
					}
					break;
					default:
					break;
				}
				manager.killActivity(StartActivity.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.run();
		}
	}	
}
