package com.example.jim.bookshelf.misc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jim on 16-3-21.
 */
public class RebountItemAnimator extends RecyclerView.ItemAnimator
{
    private List<RecyclerView.ViewHolder> mViewHolders = new ArrayList();
    private SpringConfig springConfig = new SpringConfig(70.0D, 10.0D);
    private SpringSystem springSystem = SpringSystem.create();

    public boolean animateAdd(RecyclerView.ViewHolder paramViewHolder)
    {
        paramViewHolder.itemView.setScaleX(0.0F);
        paramViewHolder.itemView.setScaleY(0.0F);
        return this.mViewHolders.add(paramViewHolder);
    }

    /*@Override
    public  boolean animateChange(ViewHolder oldHolder,
                                  ViewHolder newHolder,
                                  ItemHolderInfo preLayoutInfo,
                                  ItemHolderInfo postLayoutInfo)
    {
        return false;
    }*/

    public boolean animateMove(RecyclerView.ViewHolder paramViewHolder,
                               int paramInt1,
                               int paramInt2,
                               int paramInt3,
                               int paramInt4)
    {
        return false;
    }

    @Override
    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }

    public boolean animateRemove(RecyclerView.ViewHolder paramViewHolder)
    {
        return false;
    }

    public void endAnimation(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void endAnimations()
    {
    }

    public boolean isRunning()
    {
        return !this.mViewHolders.isEmpty();
    }

    public void runPendingAnimations()
    {
        if (!this.mViewHolders.isEmpty())
        {
            Iterator localIterator = this.mViewHolders.iterator();
            while (localIterator.hasNext())
            {
                final View localView = ((RecyclerView.ViewHolder)localIterator.next()).itemView;
                Spring localSpring = this.springSystem.createSpring();
                localSpring.setSpringConfig(this.springConfig);
                localSpring.setCurrentValue(0.0D);
                localSpring.addListener(new SimpleSpringListener()
                {
                    public void onSpringUpdate(Spring paramAnonymousSpring)
                    {
                        float f = (float)paramAnonymousSpring.getCurrentValue();
                        localView.setScaleX(f);
                        localView.setScaleY(f);
                    }
                });
                localSpring.setEndValue(1.0D);
            }
        }
    }

    /*@Override
    public boolean animateDisappearance(ViewHolder viewHolder,
                                        ItemHolderInfo preLayoutInfo,
                                        ItemHolderInfo postLayoutInfo)
    {
        return true;
    }*/

    /*@Override
    public boolean animateAppearance(ViewHolder viewHolder,
                                     ItemHolderInfo preLayoutInfo,
                                     ItemHolderInfo postLayoutInfo)
    {
        return true;
    }*/

    /*public boolean animatePersistence(ViewHolder viewHolder,
                                      ItemHolderInfo preLayoutInfo,
                                      ItemHolderInfo postLayoutInfo)
    {
        return false;
    }*/
}
