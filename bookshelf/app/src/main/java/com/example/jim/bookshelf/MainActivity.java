package com.example.jim.bookshelf;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jim.bookshelf.bean.Magazine;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import java.util.LinkedHashMap;
import java.util.List;


public class MainActivity extends BaseActivity
        implements AdapterView.OnItemClickListener
{
    private boolean isPositionClicked = false;
    private int mClickedPosition = 99;
    private int mCurrentPlatform = 0;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLeftDrawerListView;
    private LinearLayout mLinearDrawer;
    private Toolbar mToolbar;
    private List<NavigationDrawerItem> navigationItems;
    private String nickName;

    public static LinkedHashMap<String, Magazine> sMagazineMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setSupportActionBar(this.mToolbar);
        if (savedInstanceState == null) {
            Log.i("jim", "main");
            MainActivityFragment f1 = new MainActivityFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame, f1)
                    .commit();
        }


    }

    private void initViews()
    {
        this.mToolbar = ((Toolbar)findViewById(R.id.toolbar));
        this.mDrawerLayout = ((DrawerLayout)findViewById(R.id.drawerLayout));
    }

   @Override
   public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
   {
        /*
        this.mClickedPosition = paramInt;
        this.isPositionClicked = true;
        if (this.mDrawerLayout.isDrawerOpen(this.mLinearDrawer))
            this.mDrawerLayout.closeDrawer(this.mLinearDrawer);
        */
   }
}
