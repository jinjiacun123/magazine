package com.jim.magazine;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jim.magazine.fragment.BookselfFragment;
import com.jim.magazine.fragment.CircleFragment;
import com.jim.magazine.fragment.HomeFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class MainActivity extends SlidingFragmentActivity {

	protected static final String TAG = "MainActivity";
	private Context mContext;
	private ImageButton mHome,mBook,mCircle,mMine;
	private View mPopView;
	private View currentButton;
	
	private TextView app_cancle;
	private TextView app_exit;
	private TextView app_change;
	
	private int mLevel=1;
	private PopupWindow mPopupWindow;
	private LinearLayout buttomBarGroup;
	
	ListView listView ;
	
	static final String[] menu_item_names = new String[]{
		"精品推荐",
		"一绘视频",
		"我的书架",
		"精品书店",
		"品牌驿站",
		"关于我们",
	}; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		mContext=this;
		
		findView();
		init();
				
		setBehindContentView(R.layout.leftmenu);
		// configure the SlidingMenu
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		
        listView = (ListView) findViewById(R.id.navigate_list);

       
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          R.layout.menu_item,  R.id.text1,  menu_item_names);
      

        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    Toast.makeText(getApplicationContext(),
				((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
        
	}
	
	private void findView(){
		mPopView=LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
		buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
		mHome   = (ImageButton) findViewById(R.id.buttom_home);//主页
		mBook   = (ImageButton) findViewById(R.id.buttom_book);//书架
		mCircle = (ImageButton) findViewById(R.id.buttom_cicle);//圈子
		mMine   = (ImageButton) findViewById(R.id.buttom_mine);//我绘		
		
		app_cancle=(TextView) mPopView.findViewById(R.id.app_cancle);
		app_change=(TextView) mPopView.findViewById(R.id.app_change_user);
		app_exit=(TextView) mPopView.findViewById(R.id.app_exit);
	}
	
	private void init(){
		mHome.setOnClickListener(homeOnClickListener);
		mBook.setOnClickListener(bookOnClickListener);
		mCircle.setOnClickListener(circleOnClickListener);
		mMine.setOnClickListener(mineOnClickListener);
		
		mHome.performClick();
		
		mPopupWindow=new PopupWindow(mPopView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		
		app_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		
		app_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				((Activity)mContext).overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				finish();
			}
		});
		
		app_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	//主页
	private OnClickListener homeOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			HomeFragment newsFatherFragment= new HomeFragment();
			ft.replace(R.id.fl_content, newsFatherFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
		}
	};
	
	//书架
	private OnClickListener bookOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			BookselfFragment bookselfFragment= new BookselfFragment();
			ft.replace(R.id.fl_content, bookselfFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
		}
	};
	
	//圈子
	private OnClickListener circleOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			CircleFragment circleFragment= new CircleFragment();
			ft.replace(R.id.fl_content, circleFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
		}
	};
	
	//我绘
	private OnClickListener mineOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			MineFragment mineFragment= new MineFragment();
			ft.replace(R.id.fl_content, mineFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
		}
	};
	
	private void setButton(View v){
		if(currentButton!=null&&currentButton.getId()!=v.getId()){
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton=v;
	}

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
			mPopupWindow.showAtLocation(buttomBarGroup, Gravity.BOTTOM, 0, 0);
			mPopupWindow.setAnimationStyle(R.style.app_pop);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.update();
		}
		return super.onKeyDown(keyCode, event);
		
	}*/

}
