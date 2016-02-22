package com.jim.magazine;

import java.util.Timer;
import java.util.TimerTask;

import com.jim.magazine.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

/**
 * 主页的activity
 * 主要是一个fragmentTabhost
 * 
 * @author jim 
 */
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private FragmentTabHost ftabhost;// 定义一个fragmenttabhost
	private String[] tabtag = { "home_page",
			                    "bookself", 
			                    "group", 
			                    "mine", };// 给tab取得别名
	private String[] tabtext = { "首页",
			                     "书架" ,
			                     "圈子", 
			                     "我绘" };// 选项卡里面设置的名字
	private static Class[] cla = { HomeFragment.class,
		                           BookselfFragment.class,
		                           GroupFragment.class,
		                           MineFragment.class };
	// 没有被选中的图片的id
	private int[] imageid = { R.drawable.homepage_a,
			                  R.drawable.homepage_a, 
			                  R.drawable.exosure_a,
			                  R.drawable.mine_a };
	private LayoutInflater inflater;// 布局解析器
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private String main_type = "";
	
	//推送
	private String mDeviceID;
	private long user_id;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
 
		initDate();
	}
		
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg){
			switch(msg.what){
			case 0:
				String result = msg.obj.toString();
				/*
				final PushInfo pushInfo = JsonUtil.getPushInfo(result);
				if(pushInfo != null){
					switch(pushInfo.getIs_sucess()){
					case 0:
						Log.i("push", "success" + pushInfo.getIs_sucess());
						break;
					case -1:
						Log.i("push", "failure" + pushInfo.getIs_sucess());
						break;
					}
				}
				*/
			}
		}
	};
	

	private void initDate() {
		inflater = LayoutInflater.from(MainActivity.this);
		ftabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
		FragmentManager t = getSupportFragmentManager();
		//ftabhost.setup(this, getSupportFragmentManager(), com.jim.magazine.R.id.realtabcontent);
		int my_id = R.id.realtabcontent;
//		ftabhost.setup(this, getSupportFragmentManager());
		ftabhost.setup(this, getSupportFragmentManager(), my_id);
		//ftabhost.setup(getActivity(), getChildFragmentManager(), my_id);
		preferences = this.getSharedPreferences("Statuse",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		
		
		for (int i = 0; i < tabtext.length; i++) {
			ftabhost.addTab(
					ftabhost.newTabSpec(this.tabtag[i]).setIndicator(getView(i)),
					MainActivity.cla[i], null);
		}
	}

	private View getView(int i) {
		View view = inflater.inflate(R.layout.activity_main_tabview, null);
		TextView tabtext = (TextView) view.findViewById(R.id.tvtext);
		ImageView tabicon = (ImageView) view.findViewById(R.id.tabicon);
		tabtext.setText(this.tabtext[i]);
		switch (i) {
		case 0:
			tabicon.setBackgroundResource(R.drawable.selector_tab_homepage);
			break;
		case 1:
			tabicon.setBackgroundResource(R.drawable.selector_tab_rankinglist);
			break;
		case 2:
			tabicon.setBackgroundResource(R.drawable.selector_tab_exosure);
			break;
		case 3:
			tabicon.setBackgroundResource(R.drawable.selector_tab_mine);
			break;
		}
		return view;
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}
   
	/**
	 * 双击退出函数
	 */
	
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
	}	
}
