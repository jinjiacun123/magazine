package com.jim.magazine.fragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jim.magazine.MainActivity;
import com.jim.magazine.R;
import com.jim.magazine.adapter.HomeArticleAdapter;
import com.jim.magazine.adapter.HomeImgAdapter;
import com.jim.magazine.adapter.ListViewAdapter;
import com.jim.magazine.entity.HomeArticle;
import com.jim.magazine.entity.ImageEntity;
import com.jim.magazine.help.Request;
import com.jim.magazine.view.TitleBarView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页
 * 
 * @author jim
 * 
 */
public class HomeFragment extends Fragment implements OnPageChangeListener{

	private View view;// 缓存Fragment view
	private PackageManager manager;// 包管理器
	private PackageInfo packageinfo;// 当前项目的包
	private SharedPreferences preferences;
	private boolean login_type = false;
	private ConnectivityManager manager2;// 获取网咯的管理器
	private long id;
	private LinearLayout layout;
	
	private View mBaseView;
	private TitleBarView mTitleBarView;
	
	public static int chg_times=-1; //当前切换次数

	private HomeArticleAdapter home_article_adapter; 
	
	private String[] image_url= {
	"http://192.168.1.131/yms_api/Public/media/ad/item01.jpg",
	"http://192.168.1.131/yms_api/Public/media/ad/item02.jpg",
	"http://192.168.1.131/yms_api/Public/media/ad/item03.jpg",
	"http://192.168.1.131/yms_api/Public/media/ad/item04.jpg",
	"http://192.168.1.131/yms_api/Public/media/ad/item05.jpg"
};
	
	
	private ArrayList<HomeArticle> article_array = new ArrayList<HomeArticle>();
	
	private Handler handler = new Handler() {
		
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					String result1 = msg.obj.toString();
					if ("null".equals(result1)) {
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
		if (mBaseView == null) {
			mBaseView = inflater.inflate(R.layout.activity_home, null);
		}
		
		ViewGroup parent = (ViewGroup) mBaseView.getParent();
		if (parent != null) {
			parent.removeView(mBaseView);
		}
		
		
		//mBaseView = inflater.inflate(R.layout.activity_home, null);
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
	/*	btn_chg_a = (ImageView)mBaseView.findViewById(R.id.btn_chg_a);
		btn_chg_b = (ImageView)mBaseView.findViewById(R.id.btn_chg_b);
		btn_chg_c = (ImageView)mBaseView.findViewById(R.id.btn_chg_c);
		*/
		
		
		//初始化文章数据
		HomeArticle home_article; 
		home_article= new HomeArticle();
		home_article.setTitle("");
		home_article.setContent("");
		home_article.setP1(0);
		home_article.setP2(0);
		article_array.add(home_article);
		home_article= new HomeArticle();
		home_article.setTitle("1月26日");
		home_article.setContent("一绘视频|发现身边被忽略的美");
		home_article.setP1(R.drawable.p1);
		home_article.setP2(R.drawable.p2);
		article_array.add(home_article);
		home_article= new HomeArticle();
		home_article.setTitle("1月25日");
		home_article.setContent("精选|爱她就为她打造一座爱的城堡");
		home_article.setP1(R.drawable.p1_1);
		home_article.setP2(R.drawable.p2_1);
		article_array.add(home_article);
		
		init();
		
		home_article_adapter = new HomeArticleAdapter(getActivity(), article_array, image_url); 
		ListView listView = (ListView)mBaseView.findViewById(R.id.home_magazine_list);
		//listView.setListAdapter(raAdapter);
		listView.setAdapter(home_article_adapter);
		//new DownPictureThread(image_url).start();
		return mBaseView;
	}
	
	private void init(){//GONE-隐藏,VISIBLE-显示
		mTitleBarView.setCommonTitle(View.VISIBLE,
				                                                       View.VISIBLE, 
				                                                       View.VISIBLE,
				                                                       View.VISIBLE);
		mTitleBarView.setBtnLeft(R.drawable.home_city_green, 0);
		mTitleBarView.setBtnLeftImg(R.drawable.home_city_green);
		mTitleBarView.setTitleText(R.string.brand);
		mTitleBarView.setBtnRight(R.drawable.cart);
		mTitleBarView.controlSlidingMenu(MainActivity.menu);
		
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				//mCanversLayout.setVisibility(View.VISIBLE);
			}
		});
		
/*		//滑动事件
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
			
		});*/
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
	

		

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			//setImageBackground(arg0 % mImageViews.length);
		}
		
		private void setImageBackground(int selectItems){
			
		/*	for(int i=0; i<tips.length; i++){
				if(i == selectItems){
					tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
				}else{
					tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
			}*/
			
		}
}
