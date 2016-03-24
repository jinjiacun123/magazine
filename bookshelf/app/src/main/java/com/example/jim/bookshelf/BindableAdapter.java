package com.example.jim.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BindableAdapter<T> extends BaseAdapter
{
    private final Context context;
    private final LayoutInflater inflater;

    public BindableAdapter(Context paramContext)
    {
        this.context = paramContext;
        this.inflater = LayoutInflater.from(paramContext);
    }

    public void bindDropDownView(T paramT, int paramInt, View paramView)
    {
        bindView(paramT, paramInt, paramView);
    }

    public abstract void bindView(T paramT, int paramInt, View paramView);

    public Context getContext()
    {
        return this.context;
    }

    public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        if (paramView == null)
        {
            paramView = newDropDownView(this.inflater, paramInt, paramViewGroup);
            if (paramView == null)
                throw new IllegalStateException("newDropDownView result must not be null.");
        }
        bindDropDownView(getItem(paramInt), paramInt, paramView);
        return paramView;
    }

    public abstract T getItem(int paramInt);

    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        if (paramView == null)
        {
            paramView = newView(this.inflater, paramInt, paramViewGroup);
            if (paramView == null)
                throw new IllegalStateException("newView result must not be null.");
        }
        bindView(getItem(paramInt), paramInt, paramView);
        return paramView;
    }

    public View newDropDownView(LayoutInflater paramLayoutInflater, int paramInt, ViewGroup paramViewGroup)
    {
        return newView(paramLayoutInflater, paramInt, paramViewGroup);
    }

    public abstract View newView(LayoutInflater paramLayoutInflater, int paramInt, ViewGroup paramViewGroup);
}