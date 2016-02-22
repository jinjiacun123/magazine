package com.jim.magazine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 引导页
 * 
 * @author Administrator
 * 
 */
public class WelcomeActivity extends Activity {
	// 引导页集合
	private int[] images = { R.drawable.welcome_1, 
			                 R.drawable.welcome_2,
			                 R.drawable.welcome_3};
	private AppManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(WelcomeActivity.this);
	}

	private void initView() {
		// 引导页控件
		final ViewPager vp_content = (ViewPager) findViewById(R.id.vp_content);
		vp_content.setAdapter(new MyAdapter());
	}
	
	/*
	private void SetShare() {
		try {
			int versionCode = VersionCodeUtil
					.getVersionCode(getApplicationContext());
			// 是否加载引导页状态
			getSharedPreferences("Gudie_type", Activity.MODE_PRIVATE).edit()
					.putInt("versionCode", versionCode).commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(WelcomeActivity.this,
					R.layout.activity_guide_list_item, null);
			assert view != null;
			ImageView iv_skip = (ImageView) view.findViewById(R.id.iv_skip);
			ImageView iv_goods = (ImageView) view.findViewById(R.id.iv_goods);
			iv_goods.setImageResource(images[position]);
			container.addView(view, 0);
			if (position == images.length - 1) {
				iv_skip.setVisibility(View.GONE);
				iv_goods.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//SetShare();
						
						//if (Util.getLoginType(WelcomeActivity.this)) 
						//{
							Intent intent = new Intent(WelcomeActivity.this,
									MainActivity.class);
							startActivity(intent);
						/*}
						else 
						{
							Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
							startActivity(intent);
							//Util.UpdateRegisterType(WelcomeActivity.this, true);
						}
						*/
						manager.killActivity(WelcomeActivity.this);
					}
				});
			}else{
				//点击跳过
				iv_skip.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//SetShare();
						
						/*if (Util.getLoginType(WelcomeActivity.this)) {
							Intent intent = new Intent(WelcomeActivity.this,
									MainActivity.class);
							startActivity(intent);
						}else {
						*/
							Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
							startActivity(intent);
							//Util.UpdateRegisterType(WelcomeActivity.this, true);
						//}
						manager.killActivity(WelcomeActivity.this);
					}
				});
			}
			return view;
		}
	}

}
