package com.example.jim.bookshelf.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jim.bookshelf.BookShelfAction;
import com.example.jim.bookshelf.MainActivity;
import com.example.jim.bookshelf.MainActivityFragment;
import com.example.jim.bookshelf.R;
import com.example.jim.bookshelf.bean.Magazine;
import com.example.jim.bookshelf.config.Config;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.example.jim.bookshelf.MainActivity;

 class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener
{
    BookShelfAction bookShelfAction;
    ImageView bookmark;
    TextView downLoadPercent;
    ImageView imageView;
    ProgressBar loadprogress;
    LinearLayout magazineLayout;
    ImageView magazineStateImg;
    TextView name;
    ImageView operateImg;
    CheckBox stateCheck;
    FrameLayout unzipPbLayout;
    RelativeLayout wholeRv;
    TextView yearTitle;

    public ViewHolder(View paramBookShelfAction, BookShelfAction arg3)
    {
        super(paramBookShelfAction);
        initViews(paramBookShelfAction);
        bookShelfAction = arg3;
        paramBookShelfAction.setOnClickListener(this);
        paramBookShelfAction.setOnLongClickListener(this);
    }

    private void initViews(View paramView)
    {
        this.magazineLayout = ((LinearLayout)paramView.findViewById(R.id.magazineLayout));
        this.loadprogress = ((ProgressBar)paramView.findViewById(R.id.loadprogress));
        this.imageView = ((ImageView)paramView.findViewById(R.id.imageView));
        this.magazineStateImg = ((ImageView)paramView.findViewById(R.id.magazineStateImg));
        this.downLoadPercent = ((TextView)paramView.findViewById(R.id.downLoadPercent));
        this.unzipPbLayout = ((FrameLayout)paramView.findViewById(R.id.unzipPbLayout));
        this.name = ((TextView)paramView.findViewById(R.id.name));
        this.operateImg = ((ImageView)paramView.findViewById(R.id.operateImg));
        this.bookmark = ((ImageView)paramView.findViewById(R.id.bookmark));
        this.stateCheck = ((CheckBox)paramView.findViewById(R.id.state_check));
        this.yearTitle = ((TextView)paramView.findViewById(R.id.year_title));
        this.wholeRv = ((RelativeLayout)paramView.findViewById(R.id.whole_rv));
    }

    public void onClick(View paramView)
    {
        this.bookShelfAction.onItemClickListener(paramView, getPosition());
    }

    public boolean onLongClick(View paramView)
    {
        return this.bookShelfAction.onItemLongClickListener(paramView, getPosition());
    }
}
/**
 * Created by jim on 16-3-19.
 */
public class BookShelfAdapter extends RecyclerView.Adapter<ViewHolder>
{


    private BookShelfAction mBookShelfAction;
    private List<Magazine> mMagazines = new ArrayList();
    private SparseBooleanArray mSelectedItems;
    private Magazine notifyMagazine;
    private int sdcardOverflowTipsNum = 0;

    public BookShelfAdapter(LinkedHashMap<String, Magazine> paramLinkedHashMap,
                            BookShelfAction paramBookShelfAction)
    {
        this.mMagazines.addAll(paramLinkedHashMap.values());
        int i = 1;
        /*while (true) {
            if (i < this.mMagazines.size()) {
                String str1 = ((Magazine) this.mMagazines.get(i)).getYear();
                Object localObject = "";
                try {
                    String str2 = ((Magazine) this.mMagazines.get(i - 1)).getYear();
                    localObject = str2;
                    if ((localObject != null)
                            && (!"".equals(localObject))
                            && (i % 2 != 0) && (!str1.equals(localObject)))
                    {
                        this.mMagazines.add(i, null);
                        MainActivityFragment.mMagazineData.getMagazines().add(i, null);
                        i++;
                    }
                    i++;
                } catch (Exception localException) {
                        localException.printStackTrace();
                }
            }
        }*/
        this.mBookShelfAction = paramBookShelfAction;
        this.mSelectedItems = new SparseBooleanArray();
        setHasStableIds(true);
    }

    public void clearSelections()
    {
        this.mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getItemCount()
    {
        return this.mMagazines.size();
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public int getItemViewType(int paramInt)
    {
        if ((this.notifyMagazine != null)
         && (this.mMagazines.get(paramInt) != null)
         && (this.notifyMagazine.getDownloadTask().getUrl().equals(((Magazine)this.mMagazines.get(paramInt)).getDownloadTask().getUrl()))
         && (!((Magazine)this.mMagazines.get(paramInt)).isDel()))
        {
            ((Magazine)this.mMagazines.get(paramInt)).setCurrentOperateState(this.notifyMagazine.getCurrentOperateState());
            ((Magazine)this.mMagazines.get(paramInt)).getDownloadTask().setTaskState(this.notifyMagazine.getDownloadTask().getTaskState());
            ((Magazine)this.mMagazines.get(paramInt)).getDownloadTask().setPercent(this.notifyMagazine.getDownloadTask().getPercent());
        }
        return 0;
    }

    public int getSelectedItemCount()
    {
        return this.mSelectedItems.size();
    }

    public List<Integer> getSelectedItems()
    {
        ArrayList localArrayList = new ArrayList(this.mSelectedItems.size());
        for (int i = 0; i < this.mSelectedItems.size(); i++)
            localArrayList.add(Integer.valueOf(this.mSelectedItems.keyAt(i)));
        return localArrayList;
    }

    public boolean isSelected(int paramInt)
    {
        return this.mSelectedItems.get(paramInt, false);
    }

    @Override
    public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
    {
        Log.i("jim", "onBindViewHolder" + paramInt);
        Magazine localMagazine = (Magazine)this.mMagazines.get(paramInt);
        if (localMagazine == null)
        {
            paramViewHolder.wholeRv.setVisibility(View.INVISIBLE);
            return;
        }
        paramViewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));
        ImageLoader.getInstance().displayImage(localMagazine.getThumb(), paramViewHolder.imageView);
        Log.i("jim", localMagazine.getThumb());
        paramViewHolder.name.setText(localMagazine.getIssue());
        if (localMagazine.getId().equals(Config.getInstence(paramViewHolder.bookmark.getContext()).getBookmarkByMagazineId()))
            paramViewHolder.bookmark.setVisibility(View.VISIBLE);
        while (true)
        {
            String str1="";
            Object localObject;
            //下载事件
            if (MainActivityFragment.sActionMode != null)
            {
                Log.i("jim", "sActionMode !=null in adapter" + paramInt);
                paramViewHolder.loadprogress.setVisibility(View.INVISIBLE);
                paramViewHolder.magazineStateImg.setVisibility(View.GONE);
                if (localMagazine.getDownloadTask().getTaskState() == 4)
                {
                    paramViewHolder.stateCheck.setVisibility(View.VISIBLE);
                    str1 = ((Magazine)this.mMagazines.get(paramInt)).getYear();
                    localObject = "";
                }
            }
            try
            {
                String str2 = ((Magazine)this.mMagazines.get(paramInt - 1)).getYear();
                localObject = str2;
                paramViewHolder.yearTitle.setText(str2);
                if (str1.equals(localObject))
                    paramViewHolder.yearTitle.setVisibility(View.INVISIBLE);
                paramViewHolder.itemView.setActivated(this.mSelectedItems.get(paramInt, false));
                paramViewHolder.stateCheck.setChecked(this.mSelectedItems.get(paramInt, false));
                paramViewHolder.bookmark.setVisibility(View.GONE);
                switch (localMagazine.getCurrentOperateState()) {
                    default:
                        paramViewHolder.magazineStateImg.setImageDrawable(null);
                        break;
                    case 2:
                        paramViewHolder.stateCheck.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        paramViewHolder.stateCheck.setVisibility(View.VISIBLE);
                        break;
                }
                    paramViewHolder.imageView.setAlpha(254);
                    paramViewHolder.loadprogress.setVisibility(View.VISIBLE);
                    paramViewHolder.stateCheck.setVisibility(View.GONE);
                    paramViewHolder.magazineStateImg.setVisibility(View.VISIBLE);
                    if (localMagazine.getCurrentOperateState() == 0)
                        switch (localMagazine.getDownloadTask().getTaskState())
                        {
                            default:
                                break;
                            case 1:
                                paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                                paramViewHolder.magazineStateImg.setImageResource(R.drawable.magazine_state_downloading_wait_bg);
                                paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                                break;
                            case 4:
                                paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                                paramViewHolder.magazineStateImg.setImageResource(R.drawable.magazine_state_downloading_pause_bg);
                                paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                                paramViewHolder.operateImg.setImageResource(R.drawable.magazine_operate_icon_continue);
                                paramViewHolder.operateImg.setVisibility(View.VISIBLE);
                                break;
                            case 2://下载
                                paramViewHolder.magazineStateImg.setImageDrawable(null);
                                //paramViewHolder.magazineStateImg.setBackgroundResource(R.anim.magazine_state_downloading);
                                ((AnimationDrawable)paramViewHolder.magazineStateImg.getBackground()).start();
                                paramViewHolder.downLoadPercent.setVisibility(View.VISIBLE);
                                paramViewHolder.downLoadPercent.setText(localMagazine.getDownloadTask().getPercent() + "%");
                                break;
                            case 3://暂停
                                paramViewHolder.magazineStateImg.setImageDrawable(null);
                                //paramViewHolder.magazineStateImg.setBackgroundResource(R.anim.magazine_state_downloading);
                                ((AnimationDrawable)paramViewHolder.magazineStateImg.getBackground()).start();
                                paramViewHolder.downLoadPercent.setVisibility(View.VISIBLE);
                                paramViewHolder.downLoadPercent.setText(localMagazine.getDownloadTask().getPercent() + "%");
                                paramViewHolder.operateImg.setImageResource(R.drawable.magazine_operate_icon_pause);
                                paramViewHolder.operateImg.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                                paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                                paramViewHolder.magazineStateImg.setVisibility(View.INVISIBLE);
                                paramViewHolder.unzipPbLayout.setVisibility(View.VISIBLE);
                                break;
                            case 6:
                                paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                                paramViewHolder.magazineStateImg.setImageResource(R.drawable.magazine_state_downloading_pause_bg);
                                paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                                break;
                        }
                    switch (localMagazine.getCurrentOperateState())
                    {
                        case 1:
                            paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                            paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                            paramViewHolder.magazineStateImg.setVisibility(View.INVISIBLE);
                            paramViewHolder.unzipPbLayout.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            paramViewHolder.magazineStateImg.setBackgroundDrawable(null);
                            paramViewHolder.downLoadPercent.setVisibility(View.GONE);
                            paramViewHolder.unzipPbLayout.setVisibility(View.INVISIBLE);
                            paramViewHolder.magazineStateImg.setImageResource(R.drawable.magazine_state_unziped_bg);
                            paramViewHolder.magazineStateImg.setVisibility(View.VISIBLE);
                            paramViewHolder.operateImg.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            if (this.sdcardOverflowTipsNum > 0)
                                break;
                            this.sdcardOverflowTipsNum = (1 + this.sdcardOverflowTipsNum);
                    }
                    break;
            }
            catch (Exception localException)
            {
                return;
            }
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        Log.i("jim", "onCreateViewHolder" + paramInt);
        LayoutInflater mInflater;
        /*ViewHolder localViewHolder = new ViewHolder(LayoutInflater
                                                    .from(paramViewGroup.getContext())
                                                    .inflate(R.layout.magazine_layout, paramViewGroup, false), this.mBookShelfAction);
        localViewHolder.setIsRecyclable(false);*/
        mInflater = LayoutInflater.from(paramViewGroup.getContext());
        //View v = mInflater.inflate(R.layout.magazine_layout, paramViewGroup, false);
        View v = mInflater.inflate(R.layout.item_home, paramViewGroup, false);
        ViewHolder vh = new ViewHolder(v,this.mBookShelfAction);
        Log.i("jim", "magazine_layout");
        return vh;
        //return localViewHolder;
    }

    /*@Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("jim", "onBindViewHolder" + position);
        Magazine localMagazine = (Magazine)this.mMagazines.get(position);
        if (localMagazine == null)
        {
            holder.wholeRv.setVisibility(View.INVISIBLE);
            return;
        }
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.getInstance().displayImage(localMagazine.getThumb(), holder.imageView);
        holder.name.setText(localMagazine.getIssue());
        if (localMagazine.getId().equals(Config.getInstence(holder.bookmark.getContext()).getBookmarkByMagazineId()))
            holder.bookmark.setVisibility(View.VISIBLE);
    }*/

    public void onViewAttachedToWindow(ViewHolder paramViewHolder)
    {
        super.onViewAttachedToWindow(paramViewHolder);
        Log.i("slz", "onViewAttachedToWindow" + paramViewHolder.name.getText());
    }

    public void onViewDetachedFromWindow(ViewHolder paramViewHolder)
    {
        super.onViewDetachedFromWindow(paramViewHolder);
        Log.i("slz", "onViewDetachedFromWindow" + paramViewHolder.name.getText());
    }

    public void onViewRecycled(ViewHolder paramViewHolder)
    {
        super.onViewRecycled(paramViewHolder);
        Log.i("slz", "onViewRecycled" + paramViewHolder.name.getText());
    }

    public void setNotifyMagazine(Magazine paramMagazine)
    {
        this.notifyMagazine = paramMagazine;
    }

    public void setSelection(int paramInt, boolean paramBoolean)
    {
        if ((!paramBoolean) && (this.mSelectedItems.get(paramInt, false)))
            this.mSelectedItems.delete(paramInt);
        while (true)
        {
            notifyItemChanged(paramInt);
            if (paramBoolean)
                this.mSelectedItems.put(paramInt, true);
        }
    }

    public void toggleSelection(int paramInt)
    {
        if (this.mSelectedItems.get(paramInt, false))
            this.mSelectedItems.delete(paramInt);
        while (true)
        {
            notifyItemChanged(paramInt);
            this.mSelectedItems.put(paramInt, true);
        }
    }
}
