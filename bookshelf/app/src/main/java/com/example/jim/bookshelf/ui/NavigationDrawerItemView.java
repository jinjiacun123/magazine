package com.example.jim.bookshelf.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jim.bookshelf.NavigationDrawerItem;
import com.example.jim.bookshelf.R;

public class NavigationDrawerItemView extends RelativeLayout
{
    private ImageView mNavigationDrawerItemIconIV;
    private TextView mNavigationDrawerItemSubTitleTV;
    private TextView mNavigationDrawerItemTitleTV;
    final Resources res;

    public NavigationDrawerItemView(Context paramContext)
    {
        super(paramContext);
        this.res = paramContext.getResources();
    }

    public NavigationDrawerItemView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        this.res = paramContext.getResources();
    }

    public NavigationDrawerItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        this.res = paramContext.getResources();
    }

    private Drawable getIcon(int paramInt)
    {
        return getContext().getResources().getDrawable(paramInt);
    }

    private void initViews()
    {
        this.mNavigationDrawerItemIconIV = ((ImageView)findViewById(R.id.navigationDrawerItemIconIV));
        this.mNavigationDrawerItemTitleTV = ((TextView)findViewById(R.id.navigationDrawerItemTitleTV));
        this.mNavigationDrawerItemSubTitleTV = ((TextView)findViewById(R.id.navigationDrawerItemSubTitleTV));
    }

    public void bindTo(NavigationDrawerItem paramNavigationDrawerItem)
    {
        requestLayout();
        this.mNavigationDrawerItemTitleTV.setText(paramNavigationDrawerItem.getItemName());
        this.mNavigationDrawerItemIconIV.setImageDrawable(getIcon(paramNavigationDrawerItem.getItemIcon()));
        this.mNavigationDrawerItemSubTitleTV.setText(paramNavigationDrawerItem.getItemSubTitle());
        if (paramNavigationDrawerItem.isSelected())
        {
            this.mNavigationDrawerItemTitleTV.setTypeface(null, 1);
            return;
        }
        this.mNavigationDrawerItemTitleTV.setTypeface(null, 0);
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        initViews();
    }
}