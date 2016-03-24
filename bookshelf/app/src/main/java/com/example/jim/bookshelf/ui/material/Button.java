package com.example.jim.bookshelf.ui.material;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.jim.bookshelf.R;
import com.example.jim.bookshelf.utils.UIUtils;

/**
 * Created by jim on 16-3-19.
 */
public abstract class Button extends CustomView
{
    static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    int background;
    int backgroundColor = Color.parseColor("#1E88E5");
    int minHeight;
    int minWidth;
    View.OnClickListener onClickListener;
    float radius = -1.0F;
    int rippleSize = 3;
    float rippleSpeed = 10.0F;
    float x = -1.0F;
    float y = -1.0F;

    public Button(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        setDefaultProperties();
        setAttributes(paramAttributeSet);
        this.beforeBackground = this.backgroundColor;
    }

    public float getRippleSpeed()
    {
        return this.rippleSpeed;
    }

    public abstract TextView getTextView();

    public Bitmap makeCircle()
    {
        Bitmap localBitmap = Bitmap.createBitmap(getWidth() - UIUtils.dpToPx(6.0F, getResources()), getHeight() - UIUtils.dpToPx(7.0F, getResources()), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.drawARGB(0, 0, 0, 0);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(makePressColor());
        localCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
        if (this.radius > getHeight() / this.rippleSize)
            this.radius += this.rippleSpeed;
        if (this.radius >= getWidth())
        {
            this.x = -1.0F;
            this.y = -1.0F;
            this.radius = (getHeight() / this.rippleSize);
            if (this.onClickListener != null)
                this.onClickListener.onClick(this);
        }
        return localBitmap;
    }

    protected int makePressColor()
    {
        /*int i = 0xFF & this.backgroundColor >> 16;
        int j = 0xFF & this.backgroundColor >> 8;
        int k = 0xFF & this.backgroundColor >> 0;
        int m;
        int n;
        if (i - 30 < 0)
        {
            if (j - 30 >= 0)
                n = 0;
            if (k - 30 >= 0)
                m = 0;
        }
        for (int i1 = 0; ; i1 = k - 30)
        {
            //return Color.rgb(m, n, i1);
            m = i - 30;
            break;
            n = j - 30;
            break;
        }*/
        return 0;
    }

    @Override
    protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
    {
        super.onFocusChanged(paramBoolean,paramInt,paramRect);
        if (!paramBoolean)
        {
            this.x = -1.0F;
            this.y = -1.0F;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
        return true;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        /*if (isEnabled())
        {
            this.isLastTouch = true;
            if (paramMotionEvent.getAction() == 0) {
                this.radius = (getHeight() / this.rippleSize);
                this.x = paramMotionEvent.getX();
                this.y = paramMotionEvent.getY();
            }
        }
        do
        {
            do
            {
                if (paramMotionEvent.getAction() == 2) {
                    this.radius = (getHeight() / this.rippleSize);
                    this.x = paramMotionEvent.getX();
                    this.y = paramMotionEvent.getY();
                    return true;
                }
            }
            while ((paramMotionEvent.getX() <= getWidth()) && (paramMotionEvent.getX() >= 0.0F) && (paramMotionEvent.getY() <= getHeight()) && (paramMotionEvent.getY() >= 0.0F));
            this.isLastTouch = false;
            this.x = -1.0F;
            this.y = -1.0F;
            return true;
        }
        while (paramMotionEvent.getAction() != 1);
        if ((paramMotionEvent.getX() <= getWidth())
             && (paramMotionEvent.getX() >= 0.0F)
             && (paramMotionEvent.getY() <= getHeight())
             && (paramMotionEvent.getY() >= 0.0F))
        {
            radius = radius + 1.0F;
            return true;
        }
        this.isLastTouch = false;
        this.x = -1.0F;
        this.y = -1.0F;*/
        return true;
    }

    protected abstract void setAttributes(AttributeSet paramAttributeSet);

    public void setBackgroundColor(int paramInt)
    {
        this.backgroundColor = paramInt;
        if (isEnabled())
            this.beforeBackground = this.backgroundColor;
        try
        {
            ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(this.backgroundColor);
            return;
        }
        catch (Exception localException)
        {
        }
    }

    protected void setDefaultProperties()
    {
        setMinimumHeight(UIUtils.dpToPx(this.minHeight, getResources()));
        setMinimumWidth(UIUtils.dpToPx(this.minWidth, getResources()));
        setBackgroundResource(this.background);
        setBackgroundColor(this.backgroundColor);
    }

    public void setOnClickListener(View.OnClickListener paramOnClickListener)
    {
        this.onClickListener = paramOnClickListener;
    }

    public void setRippleSpeed(float paramFloat)
    {
        this.rippleSpeed = paramFloat;
    }
}
