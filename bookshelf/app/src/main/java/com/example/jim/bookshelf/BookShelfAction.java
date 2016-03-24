package com.example.jim.bookshelf;

import android.view.View;

/**
 * Created by jim on 16-3-19.
 */
public interface BookShelfAction
{
    void onItemClickListener(View paramView, int paramInt);

    boolean onItemLongClickListener(View paramView, int paramInt);
}
