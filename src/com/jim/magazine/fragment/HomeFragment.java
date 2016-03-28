package com.jim.magazine.fragment;

import com.jim.magazine.MainActivity;
import com.jim.magazine.R;
import com.jim.magazine.view.TitleBarView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 首页
 * 
 * @author jim
 * 
 */
public class HomeFragment extends Fragment {

	private View view;// 缓存Fragment view
	private PackageManager manager;// 包管理器
	private PackageInfo packageinfo;// 当前项目的包
	private SharedPreferences preferences;
	private boolean login_type = false;
	private ConnectivityManager manager2;// 获取网咯的管理器
	private long id;
	private LinearLayout layout;
	private ImageView btn_chg_c;//杂志滑动切换按钮
	private ImageView btn_chg_b;
	private ImageView btn_chg_a;
	
	private View mBaseView;
	private TitleBarView mTitleBarView;
	
	public static int chg_times=-1; //当前切换次数

	private Handler handler = new Handler() {
		
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String result = msg.obj.toString();
				if ("null".equals(result)) {
					Toast.makeText(getActivity(), "无信号",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "有信号",                                       
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			                 ViewGroup container,
			                 Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_home, null);
		}
		/*
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		*/
		mBaseView = inflater.inflate(R.layout.activity_home, null);
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
		btn_chg_a = (ImageView)mBaseView.findViewById(R.id.btn_chg_a);
		btn_chg_b = (ImageView)mBaseView.findViewById(R.id.btn_chg_b);
		btn_chg_c = (ImageView)mBaseView.findViewById(R.id.btn_chg_c);
		init();
		return mBaseView;
	}
	
	private void init(){//GONE-隐藏,VISIBLE-显示
		mTitleBarView.setCommonTitle(View.VISIBLE,
				                                                       View.VISIBLE, 
				                                                       View.VISIBLE,
				                                                       View.VISIBLE);
		//mTitleBarView.setBtnLeft(R.drawable.home_city_green, 0);
		mTitleBarView.setBtnLeftImg(R.drawable.home_city_green);
		mTitleBarView.setTitleText(R.string.brand);
		mTitleBarView.setBtnRight(R.drawable.cart);
		mTitleBarView.controlSlidingMenu(MainActivity.menu);
		/*
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				//mCanversLayout.setVisibility(View.VISIBLE);
			}
		});
		*/
		
		//滑动事件
		btn_chg_c.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float mPosX=(float) 0.0;
				float mPosY=(float) 0.0;
				float mCurrentPosX;
				float mCurrentPosY;
				switch (event.getAction()) {
					// 移动
				    case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
					{
						mCurrentPosX = event.getX();
						mCurrentPosY = event.getY();

						//if (mCurrentPosX - mPosX > 0 && Math.abs(mCurrentPosY - mPosY) < 10)
						//{
							chg_times++;
							chg_times = chg_times%3;
							switch(chg_times)
							{
							case 0:
							{
								btn_chg_a.setImageResource(R.drawable.home_btn_a); 
								btn_chg_b.setImageResource(R.drawable.home_btn_b);
								btn_chg_c.setImageResource(R.drawable.home_btn_c);
							}
								break;
							case 1:
							{
								btn_chg_a.setImageResource(R.drawable.home_btn_c); 
								btn_chg_b.setImageResource(R.drawable.home_btn_a);
								btn_chg_c.setImageResource(R.drawable.home_btn_b);
							}
							break;
							case 2:
							{
								btn_chg_a.setImageResource(R.drawable.home_btn_b); 
								btn_chg_b.setImageResource(R.drawable.home_btn_c);
								btn_chg_c.setImageResource(R.drawable.home_btn_a);
							}
								break;
							}
						}
					}
					//break;
				//}
				return false;
			}
			
		});
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	//===========
	private void setData(String name, String nature, String trade, int type) {
			Toast.makeText(getActivity(), "setData", 0).show();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	
	private void initView() {
		
	}

	// ��ȡ��ǰ�汾
	private void initVersion() {
		manager = getActivity().getPackageManager();
		try {
			packageinfo = manager.getPackageInfo(
					getActivity().getPackageName(), 0);
			String app_version = packageinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
