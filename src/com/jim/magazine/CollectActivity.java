package com.jim.magazine;

import java.util.Random;

import com.jim.magazine.R;
import com.jim.magazine.fragment.BookselfFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
/**
 * 我的收藏
 * 
 * @author jim
 * @email jinjiacun@gmail.com
 * @version 1.0
 */
public class CollectActivity extends FragmentActivity
{
    private static final String TAG = "AndroidDemos.SlideTabs1";
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String[] addresses = { "book", "article", "message","card" };
    
    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.activity_collect);
        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("book").setIndicator("我的书架")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("article").setIndicator("文章")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("message").setIndicator("资讯")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("card").setIndicator("帖子")
                .setContent(R.id.viewPager1));
        TabWidget tabWidget = mTabHost.getTabWidget();
        int count = tabWidget.getChildCount();
        for (int i = 0; i != count; i++)
        {
            final int index = i;
            tabWidget.getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mTabHost.setCurrentTab(index);
                    mViewPager.setCurrentItem(index);
                }
            });
        }
        tabWidget.getChildAt(1).performClick();
        tabWidget.getChildAt(0).performClick();
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId)
            {
                //ELog.i(TAG, "@--> onTabChanged by tabId: " + tabId);
            }
        });
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0)
            {
                //ELog.i(TAG, "@--> onPageSelected: " + arg0);
                mTabHost.setCurrentTab(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }
            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
    }
    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            //ELog.i(TAG, "@--> getItem by position" + position);
            //ELog.i(TAG, "@--> getItem by position" + position);
            //return MyFragment.create(addresses[position]);
        	return new BookselfFragment().create(addresses[position]);
        }
        @Override
        public int getCount()
        {
            return addresses.length;
        }
    }   
}
