package com.example.jim.bookshelf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.view.ActionMode;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jim.bookshelf.adapter.BookShelfAdapter;
import com.example.jim.bookshelf.bean.Magazine;
import com.example.jim.bookshelf.bean.MagazineData;
import com.example.jim.bookshelf.config.Env;
import com.example.jim.bookshelf.misc.RebountItemAnimator;
import com.example.jim.bookshelf.multidownloader.DownloadManager;
import com.example.jim.bookshelf.multidownloader.DownloadTask;
import com.example.jim.bookshelf.service.MagazineDbService;
import com.example.jim.bookshelf.service.MagazineDownloadService;

//import com.daimajia.androidanimations.library.YoYo;
//import com.daimajia.androidanimations.library.YoYo.AnimationComposer;
import com.example.jim.bookshelf.ui.material.ProgressBarCircularIndetermininate;
import com.example.jim.bookshelf.utils.JsonUtil;
import com.example.jim.bookshelf.utils.NetworkUtils;
import com.example.jim.bookshelf.utils.SDCardUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements BookShelfAction, ActionMode.Callback
{

    public class MagazineReceiver extends BroadcastReceiver
    {
        public MagazineReceiver()
        {
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            Magazine localMagazine = (Magazine)paramIntent.getExtras().getSerializable("notifyMagazine");
            if (localMagazine != null)
            {
                MainActivityFragment.this.mBookShelfAdapter.setNotifyMagazine(localMagazine);
                MainActivityFragment.this.mBookShelfAdapter.notifyItemRangeChanged(0,
                        MainActivityFragment.this.mBookShelfAdapter.getItemCount());
            }
        }
    }
    /*
    public static final int GET_DATA_FAIL = 1;
    public static final int GET_DATA_SUCESS;

    private SweetAlertDialog delMagazineToast;
    private SweetAlertDialog delingDialog;

    private int sumY;
    */
    private static boolean isFirstIn = true;
    private MainActivity mActivity;
    public static ActionMode sActionMode;
    private BookShelfAdapter mBookShelfAdapter;
    public static MagazineData mMagazineData;
    public static ArrayList<Integer> sNeedUpdatePosition;
    private DownloadManager downloadManager;
    private MagazineReceiver receiver;
    private RecyclerView mBookShelfRv;
    private FrameLayout mBookShelfFl;
    private ImageView mNoNetHintIv;
    private ProgressBarCircularIndetermininate mProgressBarCircularIndetermininate;

    public MainActivityFragment() {
    }

    private Handler dataHandler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            switch (paramAnonymousMessage.what)
            {
                case 0://获取到数据
                {
                    //mProgressBarCircularIndetermininate.setVisibility(View.GONE);
                    mMagazineData = JsonUtil.getMagazineData((String) paramAnonymousMessage.obj);
                    MainActivity.sMagazineMap = new LinkedHashMap();
                    initMagazineState();
                    mBookShelfAdapter = new BookShelfAdapter(MainActivity.sMagazineMap, MainActivityFragment.this);
                    mBookShelfRv.setAdapter(mBookShelfAdapter);
                    mBookShelfAdapter.notifyItemRangeInserted(0, mMagazineData.getMagazines().size());
                    mBookShelfRv.setVisibility(View.VISIBLE);
                   // mBookShelfFl.setBackgroundColor(mActivity.getResources().getColor(R.color.list_background_normal));
                }
                break;
                case 1://没有数据,缓存为空
                {
                    Toast.makeText(MainActivityFragment.this.getActivity(), "网络不给力,请检查网络是否可用!", Toast.LENGTH_LONG).show();
                    mBookShelfRv.setVisibility(View.GONE);
                    mNoNetHintIv.setVisibility(View.VISIBLE);
                    mBookShelfFl.setBackgroundColor(mActivity.getResources().getColor(R.color.book_shelf_main_bg));
                }
                break;
                default:
                break;
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle paramBundle)
    {
        super.onActivityCreated(paramBundle);
    }

    //初始化对应杂志下载任务
    private void initMagazineState()
    {
        for (int i = 0; i < mMagazineData.getMagazines().size(); i++)
        {
            ((Magazine)mMagazineData.getMagazines().get(i)).setPosition(i);
            String str = ((Magazine)mMagazineData.getMagazines().get(i)).getId();
            ((Magazine)mMagazineData.getMagazines().get(i)).setZipPath(Env.zipFilePath + str);
            ((Magazine)mMagazineData.getMagazines().get(i)).setDirPath(Env.unzipFilePath + str + "/");
            ((Magazine)mMagazineData.getMagazines().get(i)).setCurrentOperateState(MagazineDbService.getInstence(this.mActivity).getMagazineStatus(((Magazine)mMagazineData.getMagazines().get(i)).getUrl()));
            DownloadTask localDownloadTask = this.downloadManager.buildDownloadTask(((Magazine)mMagazineData.getMagazines().get(i)).getId(),
                                                                                    ((Magazine)mMagazineData.getMagazines().get(i)).getUrl(),
                                                                                    new File(Env.zipFilePath + ((Magazine)mMagazineData.getMagazines().get(i)).getId() + ".zip"));
            ((Magazine)mMagazineData.getMagazines().get(i)).setDownloadTask(localDownloadTask);
            MainActivity.sMagazineMap.put(((Magazine)mMagazineData.getMagazines().get(i)).getDownloadTask().getUrl(), mMagazineData.getMagazines().get(i));
        }
        if (isFirstIn)
        {
            isFirstIn = false;
            Intent localIntent = new Intent(this.mActivity, MagazineDownloadService.class);
            localIntent.putExtra("type", 0);
            this.mActivity.startService(localIntent);
        }
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("jim","fragment");
        View localView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.i("jim","set fragment_main");
        initMisc();
        setupViews(localView);
        initRecyclerView();
        //deleteZipIfFisrtIn();
        return localView;
    }

    private void initMisc()//初始化资源及其接受
    {
        this.mActivity = ((MainActivity)getActivity());
        sNeedUpdatePosition = new ArrayList();
        this.downloadManager = DownloadManager.getDownloadManager(this.mActivity, "bookshelf", null);
       // this.receiver = new MagazineReceiver();
       // IntentFilter localIntentFilter = new IntentFilter();
       // localIntentFilter.addAction("com.example.jim.bookshelf.magazinereceiver");
       // this.mActivity.registerReceiver(this.receiver, localIntentFilter);
    }

    private void setupViews(View paramView)
    {
        this.mBookShelfRv = ((RecyclerView)paramView.findViewById(R.id.book_shelf_rv));
        //this.mProgressBarCircularIndetermininate = ((ProgressBarCircularIndetermininate)paramView.findViewById(R.id.progressBarCircularIndetermininate));
        this.mNoNetHintIv = ((ImageView)paramView.findViewById(R.id.no_net_hint_iv));
       // this.mBookShelfFl = ((FrameLayout)paramView.findViewById(R.id.book_shelf_fl));
       /* this.mNoNetHintIv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                requestMagazineData();
            }
        });*/
    }

    private void initRecyclerView()
    {
        GridLayoutManager localGridLayoutManager = new GridLayoutManager(this.mActivity, 2, 1, false);
        this.mBookShelfRv.setLayoutManager(localGridLayoutManager);
        this.mBookShelfRv.setItemAnimator(new RebountItemAnimator());
        this.mBookShelfRv.setHasFixedSize(true);
        requestMagazineData();
    }
    private void requestMagazineData()
    {
        this.mNoNetHintIv.setVisibility(View.GONE);
        //this.mProgressBarCircularIndetermininate.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get("http://reader.pchouse.com.cn/pchousemag/android/json/magazines.json", new
                        AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                                //System.out.println(new String(bytes));
                                Message localMessage = new Message();
                                localMessage.what = 0;
                                localMessage.obj = new String(bytes);
                                dataHandler.sendMessage(localMessage);
                            }

                            @Override
                            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        }
        );

    }


    private void deleteZipIfFisrtIn()
    {
       /* new Thread(new Runnable()
        {
            public void run()
            {
                if (AppUtils.isFirstIn(MainActivityFragment.this.getActivity()))
                    FileUtils.deleteDirectory(new File(Env.zipFilePath), false);
            }
        }).start();*/
    }

    public void onItemClickListener(View paramView, int paramInt)
    {
        Magazine localMagazine = (Magazine)mMagazineData.getMagazines().get(paramInt);
        if (sActionMode == null)
        {
            Log.i("slz", "sActionMode !=null");
            if (localMagazine.currentOperateState == 2)//阅读杂志
            {

               /* localIntent = new Intent(this.mActivity, ReaderViewActivity.class);
                localIntent.putExtra("path", Env.unzipFilePath + localMagazine.getId() + "/");
                this.mActivity.startActivity(localIntent);*/

            }
        }
        while ((localMagazine.getDownloadTask().getTaskState() != 4)
                && (localMagazine.getCurrentOperateState() != 2)
                && (localMagazine.getCurrentOperateState() != 3))
        {
            Intent localIntent;
            if (!SDCardUtils.ExistSDCard())
            {
                Toast.makeText(this.mActivity, "请将SD卡插入手机!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (SDCardUtils.getSDFreeSize() < localMagazine.getMagazine_size())
            {
                Log.i("slz", "getSDFreeSize" + SDCardUtils.getSDFreeSize() + "magazine.getMagazine_size():" + localMagazine.getMagazine_size());
                Toast.makeText(this.mActivity, "SD卡剩余空间不足!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!NetworkUtils.isNetworkAvailable(this.mActivity))
            {
                Toast.makeText(this.mActivity, "网络未连接,请检查连接设置!", Toast.LENGTH_SHORT).show();
                return;
            }
            doTask(localMagazine);
            return;
        }
        myToggleSelection(paramInt);
    }

    public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
        switch (paramMenuItem.getItemId())
        {
            case R.id.menu_delete:
            default:
                return false;
        }
        //delMagazine(this.mBookShelfAdapter.getSelectedItems());//删除
        //paramActionMode.finish();
        //return true;
    }

    public boolean onItemLongClickListener(View paramView, int paramInt)
    {
        /*
        if ((sActionMode == null)
         && (
             (((Magazine)mMagazineData.getMagazines().get(paramInt)).getDownloadTask().getTaskState() == 4)
             || (((Magazine)mMagazineData.getMagazines().get(paramInt)).getCurrentOperateState() == 2)
             || (((Magazine)mMagazineData.getMagazines().get(paramInt)).getCurrentOperateState() == 3)
        ))
        {
            sActionMode = this.mActivity.getSupportActionBar().startActionMode(this);
            myToggleSelection(paramInt);
            return true;
        }
        */
        return false;
    }


    public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
        /*
        View localView = this.mActivity.getLayoutInflater().inflate(R.layout.book_shelf_edit_header, null);
        YoYo.with(Techniques.FlipInX).duration(500L).playOn(localView);
        ((TextView)localView.findViewById(R.id.downloadedMagazineNumTv)).setText(MagazineHelper.getDownloadedStateNum() + "本");
        ((TextView)localView.findViewById(2131296355)).setText(MagazineHelper.getDownloadingStateNum() + "本");
        ((TextView)localView.findViewById(2131296356)).setText(MagazineHelper.getDownloadedStateSize() + "M");
        paramActionMode.setCustomView(localView);
        */
        this.mActivity.getMenuInflater().inflate(R.menu.book_shelf_menu, paramMenu);
        return true;
    }

    public void onDestroyActionMode(ActionMode paramActionMode)
    {
        sActionMode = null;
        this.mBookShelfAdapter.clearSelections();
    }

    public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
        this.mBookShelfAdapter.notifyDataSetChanged();
        return false;
    }

    //下载任务
    private void doTask(final Magazine paramMagazine)
    {
        paramMagazine.setDel(false);
        if (NetworkUtils.getNetworkState(this.mActivity) == 1)
        {
            sendTaskMeg(paramMagazine);
            return;
        }
        if ((paramMagazine.getDownloadTask().getTaskState() == 0)
           || (paramMagazine.getDownloadTask().getTaskState() == 4)
           || (paramMagazine.getDownloadTask().getTaskState() == 6))
        {
           /* new SweetAlertDialog(this.mActivity, 3)
                .setTitleText("您现在处于非wifi状态下，确认要下载吗?")
                .setConfirmText("是")
                .setCancelText("否")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    public void onClick(SweetAlertDialog paramAnonymousSweetAlertDialog) {
                        sendTaskMeg(paramMagazine);
                        paramAnonymousSweetAlertDialog.dismiss();
                    }
                }).show();*/
            return;
        }
        sendTaskMeg(paramMagazine);
    }

    private void sendTaskMeg(Magazine paramMagazine)
    {
        Intent localIntent = new Intent(this.mActivity, MagazineDownloadService.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("type", 1);
        localBundle.putString("id", paramMagazine.getDownloadTask().getId());
        localBundle.putSerializable("filePath", paramMagazine.getDownloadTask().getFilePath());
        localBundle.putString("taskUrl", paramMagazine.getDownloadTask().getUrl());
        localBundle.putInt("position", paramMagazine.getPosition());
        localIntent.putExtras(localBundle);
        this.mActivity.startService(localIntent);
    }

    private void myToggleSelection(int paramInt)
    {
        this.mBookShelfAdapter.toggleSelection(paramInt);
    }
}