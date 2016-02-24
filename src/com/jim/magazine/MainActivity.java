package com.jim.magazine;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends SlidingFragmentActivity {

	protected static final String TAG = "MainActivity";
	private Context mContext;
	private ImageButton mHome,mConstact,mDeynaimic,mSetting;
	private View mPopView;
	private View currentButton;
	
	private TextView app_cancle;
	private TextView app_exit;
	private TextView app_change;
	
	private int mLevel=1;
	private PopupWindow mPopupWindow;
	private LinearLayout buttomBarGroup;
	
	ListView listView ;
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
        
        String[] values = new String[] { "Android List View", 
                                         "Adapter implementation",                                         
                                        };
       

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter); 
	}
	
	private void findView(){
		mPopView=LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
		buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
		mHome=(ImageButton) findViewById(R.id.buttom_news);
		
		app_cancle=(TextView) mPopView.findViewById(R.id.app_cancle);
		app_change=(TextView) mPopView.findViewById(R.id.app_change_user);
		app_exit=(TextView) mPopView.findViewById(R.id.app_exit);
	}
	
	private void init(){
		mHome.setOnClickListener(newsOnClickListener);
		
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
	
	private OnClickListener newsOnClickListener=new OnClickListener() {
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
	
	private void setButton(View v){
		if(currentButton!=null&&currentButton.getId()!=v.getId()){
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton=v;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
			mPopupWindow.showAtLocation(buttomBarGroup, Gravity.BOTTOM, 0, 0);
			mPopupWindow.setAnimationStyle(R.style.app_pop);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.update();
		}
		return super.onKeyDown(keyCode, event);
		
	}

}
