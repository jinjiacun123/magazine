package com.example.jim.bookshelf.ui.material;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by jim on 16-3-19.
 */
public class CustomView extends RelativeLayout
{
    static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    static final String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    int beforeBackground;
    final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    public boolean isLastTouch = false;

    public CustomView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public void setEnabled(boolean paramBoolean)
    {
        super.setEnabled(paramBoolean);
        if (paramBoolean)
        {
            setBackgroundColor(this.beforeBackground);
            return;
        }
        setBackgroundColor(this.disabledBackgroundColor);
    }
}
