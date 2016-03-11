package com.jim.magazine.view;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jim.magazine.MainActivity;
import com.jim.magazine.R;
import com.jim.magazine.fragment.AppManager;
import com.jim.magazine.help.SystemMethod;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBarView extends RelativeLayout {

	private static final String TAG = "TitleBarView";
	private Context mContext;
	private ImageButton btnLeft;
	private Button btnRight;
	private Button btn_titleLeft;
	private Button btn_titleRight;
	private TextView tv_center;
	private LinearLayout common_constact;
	public TitleBarView(Context context){
		super(context);
		mContext=context;
		initView();
	}
	
	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btnLeft=(ImageButton) findViewById(R.id.title_btn_left);
		btnRight=(Button) findViewById(R.id.title_btn_right);
		
		tv_center=(TextView) findViewById(R.id.title_txt);
		common_constact=(LinearLayout) findViewById(R.id.common_constact);		
	}
	
	public void setCommonTitle(int LeftVisibility,int centerVisibility,int center1Visibilter,int rightVisibility){
		btnLeft.setVisibility(LeftVisibility);
		btnRight.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);
		common_constact.setVisibility(center1Visibilter);	
	}
	
	//设置activity返回
	public void controlBack(final AppManager manager, final Activity activity)
	{
		btnLeft.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0)
			{
				// 返回
				manager.killActivity(activity);
			}
		});
	}
	
	//打开(或关闭)slidingmenu
	public void controlSlidingMenu(final SlidingMenu my_menu)
	{
		//设置左菜单控制滑动菜单打开和关闭
				btnLeft.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						if(MainActivity.menu_is_show)
						{
							my_menu.showContent(true);
							MainActivity.menu_is_show = false;	
						}
						else
						{
							my_menu.showMenu(true);
							MainActivity.menu_is_show = true;
						}
					}
					
				});
	}
	
	public void setBtnLeftImg(int drawable)
	{
		btnLeft.setImageResource(drawable);
	}
	
	public void setBtnLeft(int icon,int txtRes){
		Drawable img=mContext.getResources().getDrawable(icon);
		int height=SystemMethod.dip2px(mContext, 20);
		int width=img.getIntrinsicWidth()*height/img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);

		//btnLeft.setCompoundDrawables(img, null, null, null);
	}
	
	public void setBtnLeft(int txtRes){
		//btnLeft.setText(txtRes);
	}
	
	
	public void setBtnRight(int icon){
		Drawable img=mContext.getResources().getDrawable(icon);
		int height=SystemMethod.dip2px(mContext, 30);
		int width=img.getIntrinsicWidth()*height/img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnRight.setCompoundDrawables(img, null, null, null);
	}
	
	public void setTitleLeft(int resId){
		btn_titleLeft.setText(resId);
	}
	
	public void setTitleRight(int resId){
		btn_titleRight.setText(resId);
	}
	
	@SuppressLint("NewApi")
	public void setPopWindow(PopupWindow mPopWindow,TitleBarView titleBaarView){
			mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E9E9E9")));
			mPopWindow.showAsDropDown(titleBaarView, 0,-15);
			mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
			mPopWindow.setFocusable(true);
			mPopWindow.setOutsideTouchable(true);
			mPopWindow.update();
			
			setBtnRight(R.drawable.skin_conversation_title_right_btn_selected);
		}
	
	public void setTitleText(int txtRes){
		tv_center.setText(txtRes);
	}
	
	public void setBtnLeftOnclickListener(OnClickListener listener){
		btnLeft.setOnClickListener(listener);
	}
	
	public void setBtnRightOnclickListener(OnClickListener listener){
		btnRight.setOnClickListener(listener);
	}
	
	public Button getTitleLeft(){
		return btn_titleLeft;
	}
	
	public Button getTitleRight(){
		return btn_titleRight;
	}
	
	public void destoryView(){
		//btnLeft.setText(null);
		btnRight.setText(null);
		tv_center.setText(null);
	}

}
