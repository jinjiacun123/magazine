package com.example.jim.bookshelf;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewAnimator;

public class BetterViewAnimator extends ViewAnimator
{
    public BetterViewAnimator(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public int getDisplayedChildId()
    {
        return getChildAt(getDisplayedChild()).getId();
    }

    public void setDisplayedChildId(int paramInt)
    {
        if (getDisplayedChildId() == paramInt)
            return;
        int i = 0;
        int j = getChildCount();
        while (i < j)
        {
            if (getChildAt(i).getId() == paramInt)
            {
                setDisplayedChild(i);
                return;
            }
            i++;
        }
        throw new IllegalArgumentException("No view with ID " + paramInt);
    }
}