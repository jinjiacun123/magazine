package com.example.jim.bookshelf;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
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

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");
        Context context = getApplicationContext();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
      //          .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
   //             .writeDebugLogs() // Remove for release app
                .build();//开始构建
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);//全局初始化此配置
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
