package com.example.jim.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jim.bookshelf.ui.NavigationDrawerItemView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends BindableAdapter<NavigationDrawerItem>
{
    private List<NavigationDrawerItem> items = Collections.emptyList();

    public NavigationDrawerAdapter(Context paramContext)
    {
        super(paramContext);
    }

    public void bindView(NavigationDrawerItem paramNavigationDrawerItem, int paramInt, View paramView)
    {
        ((NavigationDrawerItemView)paramView).bindTo(paramNavigationDrawerItem);
    }

    public int getCount()
    {
        return this.items.size();
    }

    public NavigationDrawerItem getItem(int paramInt)
    {
        return this.items.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public View newView(LayoutInflater paramLayoutInflater, int paramInt, ViewGroup paramViewGroup)
    {
        return paramLayoutInflater.inflate(R.layout.navigation_drawer_item, paramViewGroup, false);
    }

    public void replaceWith(List<NavigationDrawerItem> paramList)
    {
        this.items = new ArrayList(paramList);
        notifyDataSetChanged();
    }
}