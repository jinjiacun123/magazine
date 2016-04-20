package com.jim.magazine.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.jim.magazine.MainActivity;
import com.jim.magazine.R;
import com.jim.magazine.adapter.HomeArticleAdapter;
import com.jim.magazine.adapter.HomeImgAdapter;
import com.jim.magazine.bean.BeanBase.API_METHOD_INDEX;
import com.jim.magazine.bean.BeanHelp;
import com.jim.magazine.config.Config;
import com.jim.magazine.entity.HomeArticle;
import com.jim.magazine.entity.ImageEntity;
import com.jim.magazine.help.HttpPostThread;
import com.jim.magazine.help.ImageLoadTask;
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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 首页
 * 
 * @author jim
 * 
 */
public class HomeFragment extends Fragment implements OnPageChangeListener{	
	//--------------------------------------------网络数据:begin--------------------------------------------------------------
	///切换图片
	private String[] image_url = new String[5];
	//下拉文章
	private ArrayList<HomeArticle> article_array = new ArrayList<HomeArticle>();
	private void InitNetworkData()
	{	
		this.InitNetworkImg();
		InitNetworkArticleList();
	}
	////初始化切换图片url
	private void InitNetworkImg()
	{	
		BeanHelp bean_help = new BeanHelp(); 
		//发送请求
		Map<String,Object> my_request = new HashMap<String, Object>();
		List<NameValuePair> params = bean_help.CallApi(API_METHOD_INDEX.API_HELP_HOME_FIVE_IMG_URL, 
				                                       my_request);
		new HttpPostThread(params, handler, Config.HOME_IMG).start();
	}
	////初始化下拉文章列表
	private void InitNetworkArticleList()
	{
		BeanHelp bean_help = new BeanHelp(); 
		//发送请求
		Map<String,Object> my_request = new HashMap<String, Object>();
		List<NameValuePair> params = bean_help.CallApi(API_METHOD_INDEX.API_HELP_HOME_TEN_ARTICLE, 
				                                       my_request);
		new HttpPostThread(params, handler, Config.HOME_ARTICLE).start();
	}
	//--------------------------------------------网络数据:end--------------------------------------------------------------
	
	private View view;// 缓存Fragment view
	private PackageManager manager;// 包管理器
	private PackageInfo packageinfo;// 当前项目的包
	private SharedPreferences preferences;
	private boolean login_type = false;
	private ConnectivityManager manager2;// 获取网咯的管理器
	private long id;
	private LinearLayout layout;
	
	private ViewPager viewPager;
	private ImageView[] mImageViews;//切换图片显示控件
	private ArrayList<ImageEntity> image_entity_array = new ArrayList<ImageEntity>();//图片列表
	
	private View mBaseView;
	private TitleBarView mTitleBarView;	
	public static int chg_times=-1; //当前切换次数
	private HomeArticleAdapter home_article_adapter;

	private Handler handler = new Handler() {		
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case Config.HOME_IMG://首页切换图片
				{
					BeanHelp bean_help = new BeanHelp();
					image_url  = bean_help.ParseHomeImgResult((String)msg.obj);
					  
						//广告左滑
						viewPager = (ViewPager) mBaseView.findViewById(R.id.home_ad);
						
						//5张图片切换
						for (int i = 0; i < 5; i++) {
							ImageEntity b = new ImageEntity();
							b.setImage(BitmapFactory.decodeResource(getActivity().getResources(),
									R.drawable.ic_launcher));

							image_entity_array.add(b);
						}
						
						mImageViews = new ImageView[image_entity_array.size()];
						for(int i=0; i<mImageViews.length; i++){
							ImageView imageView = new ImageView(getActivity());
							mImageViews[i] = imageView;
							imageView.setImageBitmap(image_entity_array.get(i).getImage());
						}
					  HomeImgAdapter home_img_adapter = new HomeImgAdapter(getActivity(), image_entity_array, mImageViews); 
						
						viewPager.setAdapter(home_img_adapter);
						viewPager.setOnPageChangeListener(null);
						viewPager.setCurrentItem((mImageViews.length) * 100);
						new ImageLoadTask(getActivity(), home_img_adapter).execute(image_url);
				}
				break;				
				case Config.HOME_ARTICLE://首页文章列表
				{
					BeanHelp bean_help = new BeanHelp();
					ArrayList<HomeArticle> tmp_home_article =bean_help.ParseHomeArticleListResult((String)msg.obj);
					HomeArticle home_article; 
					for (int i = 0; i < tmp_home_article.size(); i++) {
						home_article = (HomeArticle)tmp_home_article.get(i);
						ImageEntity b;
						b= new ImageEntity();
						b.setImage(BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher));
						home_article.setP1_e(b);
						b= new ImageEntity();
						b.setImage(BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher));
						home_article.setP2_e(b);
						article_array.add(home_article);
					}
					
					home_article_adapter = new HomeArticleAdapter(getActivity(), article_array); 
					ListView listView = (ListView)mBaseView.findViewById(R.id.home_magazine_list);
					listView.setAdapter(home_article_adapter);
					new ImageLoadTask1(getActivity(), home_article_adapter).execute();
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
		InitNetworkData();
		
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);

		init();

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
	
	}
	
	public class ImageLoadTask1 extends AsyncTask<String, Void, Void> {
		private HomeArticleAdapter adapter;

		public ImageLoadTask1(Context context, HomeArticleAdapter adapter) {
			this.adapter = adapter;
		}

		
		@Override
		protected Void doInBackground(String... params) {
			HomeArticle bean = null;
			Bitmap bitmap = null;
			for (int i = 0; i < adapter.getCount(); i++) {
				bean = (HomeArticle) adapter.getItem(i);
				//p1
				bitmap = BitmapFactory.decodeStream(Request
						.HandlerData(bean.getP1()));
				bean.getP1_e().setImage(bitmap);
				//p2
				bitmap = BitmapFactory.decodeStream(Request
						.HandlerData(bean.getP2()));
				bean.getP2_e().setImage(bitmap);
				publishProgress(); 
			}
			
			return null;
		}

		@Override
		public void onProgressUpdate(Void... voids) {
			if (isCancelled())
				return;
			adapter.notifyDataSetChanged();
		}
	}
}
