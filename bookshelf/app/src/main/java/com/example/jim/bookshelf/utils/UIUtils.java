package com.example.jim.bookshelf.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by jim on 16-3-19.
 */
public class UIUtils
{
    public static int dpToPx(float paramFloat, Resources paramResources)
    {
        return (int) TypedValue.applyDimension(1, paramFloat, paramResources.getDisplayMetrics());
    }

    public static int getRelativeLeft(View paramView)
    {
        if (paramView.getId() == 16908290)
            return paramView.getLeft();
        return paramView.getLeft() + getRelativeLeft((View)paramView.getParent());
    }

    public static int getRelativeTop(View paramView)
    {
        if (paramView.getId() == 16908290)
            return paramView.getTop();
        return paramView.getTop() + getRelativeTop((View)paramView.getParent());
    }
}
