package com.jim.magazine.test;

import com.jim.magazine.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
/**
 * 1. 使用TabHost和ViewPager组合实现一个可以滑动的tab
 * 2. bug：如果当前页为0，则ViewPager第一次不加载。
 * 
 * @author swordy
 * @email mryangjian@live.com
 * @since Jan 20, 2014
 * @version 1.0
 */
public class TabhostViewPager extends FragmentActivity
{
    private static final String TAG = "AndroidDemos.SlideTabs1";
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String[] addresses = { "first", "second", "third","four" };
    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.test_tabhost_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator("我的书架")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("two").setIndicator("文章")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("three").setIndicator("资讯")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("four").setIndicator("帖子")
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
        	return new GridFragment().create(addresses[position]);
        }
        @Override
        public int getCount()
        {
            return addresses.length;
        }
    }
    /*
    public static class MyFragment extends Fragment
    {
        public static MyFragment create(String address)
        {
            //ELog.i(TAG, "@--> MyFragment.create()");
            MyFragment f = new MyFragment();
            Bundle b = new Bundle();
            b.putString("address", address);
            f.setArguments(b);
            return f;
        }
        @SuppressLint("NewApi")
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            Random r = new Random(System.currentTimeMillis());
            Bundle b = getArguments();
            View v = inflater.inflate(R.layout.fragment_viewpager1_layout1, null);
            v.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
            TextView txvAddress = (TextView) v.findViewById(R.id.textView1);
            txvAddress.setTextColor(r.nextInt() >> 8 | 0xFF << 24);
            txvAddress.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
            txvAddress.setText(b.getString("address", ""));
            return v;
        }
    }
    */
}
