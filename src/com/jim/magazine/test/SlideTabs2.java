package com.jim.magazine.test;

import java.util.Random;
import com.jim.magazine.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 有错误
 * 1. 使用ActionBar和ViewPager实现可以滑动的Tab
 * 
 * @author swordy
 * @email mryangjian@live.com
 * @since Jan 20, 2014
 * @version 1.0
 */
public class SlideTabs2 extends FragmentActivity
{
    private static final String TAG = "AndroidDemos.SlideTabs2";
    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private MyPagerAdapter mPagerAdapter;
    private String[] addresses = { "first", "second", "third" };
    private Tab[] mTabs = new Tab[addresses.length];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment_viewpager1);
        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        for (int i = 0; i != addresses.length; i++)
        {
            mTabs[i] = mActionBar.newTab().setText(addresses[i]).setTabListener(mTabListener);
            mActionBar.addTab(mTabs[i]);
        }
    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0)
        {
            mActionBar.setSelectedNavigationItem(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
    };
    private TabListener mTabListener = new TabListener() {
        @Override
        public void onTabSelected(Tab tab, android.app.FragmentTransaction ft)
        {
            if (tab == mTabs[0])
            {
                mViewPager.setCurrentItem(0);
            } else if (tab == mTabs[1])
            {
                mViewPager.setCurrentItem(1);
            } else if (tab == mTabs[2])
            {
                mViewPager.setCurrentItem(2);
            }

        }
        @Override
        public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
        {
            // TODO Auto-generated method stub

        }
        @Override
        public void onTabReselected(Tab tab, android.app.FragmentTransaction ft)
        {
            // TODO Auto-generated method stub

        }
    };
    public class MyPagerAdapter extends FragmentPagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            return MyFragment.create(addresses[position]);
        }
        @Override
        public int getCount()
        {
            return addresses.length;
        }
    }
    public static class MyFragment extends Fragment
    {
        public static MyFragment create(String address)
        {
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
}
