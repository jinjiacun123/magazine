package com.jim.magazine;

import com.jim.magazine.fragment.AppManager;
import com.jim.magazine.view.TitleBarView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class VideoActivity extends Activity {
	
	private AppManager manager;
	private TitleBarView mTitleBarView;
	
	 @Override
	    protected void onCreate(Bundle arg0)
	    {
	        super.onCreate(arg0);
	        setContentView(R.layout.activity_video);
	        manager = AppManager.getInstance();
			manager.addActivity(VideoActivity.this);
	        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
			init();
	    }
	 
	 private void init(){//GONE-隐藏,VISIBLE-显示
			mTitleBarView.setCommonTitle(View.VISIBLE,
					                                                       View.VISIBLE, 
					                                                       View.VISIBLE,
					                                                       View.GONE);
			mTitleBarView.setBtnLeft(R.drawable.left_2_b, 0);
			mTitleBarView.setBtnLeftImg(R.drawable.left_2_b);
			mTitleBarView.setTitleText(R.string.brand);
			mTitleBarView.controlBack(manager, VideoActivity.this);
			//mTitleBarView.controlSlidingMenu(MainActivity.menu);
			mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
					//mCanversLayout.setVisibility(View.VISIBLE);
				}
			});
		}
}
