package com.jim.magazine.adapter;

import java.util.ArrayList;

import com.jim.magazine.entity.ImageEntity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

//广告图片左右切换
public class HomeImgAdapter extends PagerAdapter{
	private ImageView[] mImageViews;//切换图片显示控件
	private Context _context;
	private ArrayList<ImageEntity> _list;
	
	public HomeImgAdapter(Context context, ArrayList<ImageEntity> imageList, ImageView[] mImageViews) {
		this._context = context;
		this._list = imageList;
		this.mImageViews = mImageViews;
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
