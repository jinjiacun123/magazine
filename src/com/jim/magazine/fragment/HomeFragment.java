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
import com.jim.magazine.adapter.ListViewAdapter;
import com.jim.magazine.entity.HomeArticle;
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
	/*private ImageView btn_chg_c;//杂志滑动切换按钮
	private ImageView btn_chg_b;
	private ImageView btn_chg_a;*/
	
	private View mBaseView;
	private TitleBarView mTitleBarView;
	
	public static int chg_times=-1; //当前切换次数
	
	String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	LayoutInflater layoutInflater; 
	private HomeAdapter raAdapter; 
	
	private ViewPager viewPager;
	private String[] image_url = {
			"http://192.168.1.131/yms_api/Public/media/ad/item01.jpg",
			"http://192.168.1.131/yms_api/Public/media/ad/item02.jpg",
			"http://192.168.1.131/yms_api/Public/media/ad/item03.jpg",
			"http://192.168.1.131/yms_api/Public/media/ad/item04.jpg",
			"http://192.168.1.131/yms_api/Public/media/ad/item05.jpg"
	};
	//private int[] imgIdArray ;
	private ArrayList<ImageEntity> imgIdArray = new ArrayList<ImageEntity>();
	//private Map imgIdArray=new HashMap();
	private ImageView[] mImageViews;
	
	private ArrayList<HomeArticle> article_array = new ArrayList<HomeArticle>();
	
	    //title
		private String[] title={
				"",
				"1月26日",
				"1月25日"
		};
		//content
		private String[] content={
				"",
				
		};
		//p1
		private int[] p1={
				0,
			
		};
		
		//p2
		private int[] p2={
				0,
			R.drawable.p2,
			R.drawable.p2_1
		};

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
		
		raAdapter = new HomeAdapter(getActivity()); 
		ListView listView = (ListView)mBaseView.findViewById(R.id.home_magazine_list);
		//listView.setListAdapter(raAdapter);
		listView.setAdapter(raAdapter);
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
	
	//下拉杂志列表
	//自定义一个Adapter继承BaseAdapter，要重写getCount(),getItem(),getItemId(),getView()四种方法 
		 private class HomeAdapter extends BaseAdapter 
		 { 
			  	private Context context; 
			  	//构造函数 
				  public HomeAdapter(Context context) 
				  { 
					   this.context = context; 
					   layoutInflater = (LayoutInflater) context 
					       .getSystemService(inflater); 
				  } 
		   
				  //@Override 
				  @Override
				public int getCount() 
				  {  
					  return article_array.size(); 
				  } 
		   
				   // @Override 
				  @Override
				public Object getItem(int position) 
				  { 
					  return article_array.get(position); 
				  } 
		   
				   // @Override 
				  @Override
				public long getItemId(int position) 
				  { 
					  return position; 
				  } 
		   
				   // @Override 
				  @Override
				public View getView(int position, View convertView, ViewGroup parent) 
				  {  
					  LinearLayout linearLayout = null;
					  if(position == 0)
					  {
						  //对listview布局 
						  linearLayout  = (LinearLayout) layoutInflater.inflate( 
						       R.layout.home_list_item_first, null);
						  
							//广告左滑
							viewPager = (ViewPager) linearLayout.findViewById(R.id.home_ad);
							
							//下载图片
							/*imgIdArray = new int[]{
									R.drawable.item01, R.drawable.item02, R.drawable.item03, R.drawable.item04,
									R.drawable.item05,R.drawable.item06, R.drawable.item07, R.drawable.item08
							};*/
							for (int i = 0; i < 5; i++) {
								ImageEntity b = new ImageEntity();
								b.setImage(BitmapFactory.decodeResource(getResources(),
										R.drawable.ic_launcher));

								imgIdArray.add(b);
							}
							
							mImageViews = new ImageView[imgIdArray.size()];
							for(int i=0; i<mImageViews.length; i++){
								ImageView imageView = new ImageView(getActivity());
								mImageViews[i] = imageView;
								//imageView.setBackgroundResource(imgIdArray.get(i));
								imageView.setImageBitmap((Bitmap)imgIdArray.get(i).getImage());
							}
						  MyAdapter my_adapter = new MyAdapter(getActivity(), imgIdArray); 
							
							viewPager.setAdapter(my_adapter);
							viewPager.setOnPageChangeListener(null);
							viewPager.setCurrentItem((mImageViews.length) * 100);
							new ImageLoadTask(getActivity(), my_adapter).execute(image_url);
					  }
					  else
					  {
						  //对listview布局 
						    linearLayout = (LinearLayout) layoutInflater.inflate( 
						       R.layout.view_home_list_item, null);
						   
		                   ImageView home_list_item_refresh   = (ImageView)linearLayout.findViewById(R.id.home_list_item_refresh);
						   TextView     home_list_item_title          = (TextView)linearLayout.findViewById(R.id.home_list_item_title);
						   ImageView home_list_item_edit          = (ImageView)linearLayout.findViewById(R.id.home_list_item_edit);
						   ImageView home_list_item_p_1           = (ImageView)linearLayout.findViewById(R.id.home_list_item_p_1);
						  TextView home_list_item_content      = (TextView)linearLayout.findViewById(R.id.home_list_item_content);
						  ImageView home_list_item_p_2           = (ImageView)linearLayout.findViewById(R.id.home_list_item_p_2);
						   
						   home_list_item_refresh.setVisibility(View.GONE);
						   home_list_item_edit.setVisibility(View.GONE);
						   
						  if(1 == position)
						   {
							   home_list_item_refresh.setVisibility(View.VISIBLE);
							   home_list_item_edit.setVisibility(View.VISIBLE);
							   home_list_item_refresh.setImageResource(R.drawable.home_btn_fresh);
							   home_list_item_edit.setImageResource(R.drawable.home_edit);
						   }
						   
						   home_list_item_title.setText(article_array.get(position).getTitle());
						   home_list_item_content.setText(article_array.get(position).getContent());
						   home_list_item_p_1.setImageResource(article_array.get(position).getP1());
						   home_list_item_p_2.setImageResource(article_array.get(position).getP2());
		                   
					  }
					  
					  return linearLayout;
				  } 
		 } 
		 
		 //广告图片左右切换
			public class MyAdapter extends PagerAdapter{
				
				private Context _context;
				private ArrayList<ImageEntity> _list;
				
				public MyAdapter(Context context, ArrayList<ImageEntity> imageList) {
					this._context = context;
					this._list = imageList;
				}
				
				@Override
				public int getCount() {
					Log.i("jim", String.valueOf(Integer.MAX_VALUE));
					//return Integer.MAX_VALUE;
					return 5;
				}

				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					return arg0 == arg1;
				}

				@Override
				public void destroyItem(View container, int position, Object object) {
					((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
					
				}

				public Object getItem(int position) {
					return _list.get(position);
				}
			
				@Override
				public Object instantiateItem(View container, int position) {
					((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
					return mImageViews[position % mImageViews.length];
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
		
		public class ImageLoadTask extends AsyncTask<String, Void, Void> {
			private MyAdapter adapter;

			// ��ʼ��
			public ImageLoadTask(Context context, MyAdapter adapter) {
				this.adapter = adapter;
			}

			@Override
			protected Void doInBackground(String... params) {
				String url = params[0];
				String p2 = params[1];
				for (int i = 0; i < adapter.getCount(); i++) {
					ImageEntity bean = (ImageEntity) adapter.getItem(i);
					Bitmap bitmap = BitmapFactory.decodeStream(Request
							.HandlerData(params[i]));
					bean.setImage(bitmap);
					publishProgress(); 
				}

				return null;
			}

			public void onProgressUpdate(Void... voids) {
				if (isCancelled())
					return;
				adapter.notifyDataSetChanged();
			}
		}

		public class ImageEntity {
			private Bitmap image;

			public Bitmap getImage() {
				return image;
			}

			public void setImage(Bitmap image) {
				this.image = image;
			}

		}
}
