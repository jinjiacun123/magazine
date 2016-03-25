package com.example.jim.bookshelf.ui.material;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.jim.bookshelf.R;

/**
 * Created by jim on 16-3-22.
 */
public class CheckBox extends CustomView
{
    int backgroundColor = Color.parseColor("#4CAF50");
    boolean check = false;
    Check checkView;
    OnCheckListener onCheckListener;
    boolean press = false;
    int step = 0;

    public CheckBox(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        setAttributes(paramAttributeSet);
    }

    private void changeBackgroundColor(int paramInt)
    {
        ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(paramInt);
    }

    public boolean isCheck()
    {
        return this.check;
    }

    protected int makePressColor()
    {
       /* int i = 0xFF & this.backgroundColor >> 16;
        int j = 0xFF & this.backgroundColor >> 8;
        int k = 0xFF & this.backgroundColor >> 0;
        int m;
        int n;
        if (i - 30 < 0)
        {
            m = 0;
            if (j - 30 >= 0)
                break;
            n = 0;
            if (k - 30 >= 0)
                break;
        }
        for (int i1 = 0; ; i1 = k - 30)
        {
            return Color.argb(70, m, n, i1);
            m = i - 30;
            break;
            n = j - 30;
            break label55;
        }*/
        return Color.argb(70, 118, 118, 188);
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        Paint localPaint=null;
        if (this.press)
        {
            if(this.check) {
                localPaint = new Paint();
                localPaint.setAntiAlias(true);
            }
        }
        for (int i = makePressColor(); ; i = Color.parseColor("#446D6D6D"))
        {
            localPaint.setColor(i);
            paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, localPaint);
            invalidate();
            return;
        }
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        int i=0;
        if (isEnabled())
        {
            this.isLastTouch = true;
           /* if (paramMotionEvent.getAction() != 0)
                break label47;
            if (!this.check)
                break label38;*/
            i = makePressColor();
            changeBackgroundColor(i);
        }
       // label38: label47:
       /* do
        {
            do
            {
                i = Color.parseColor("#446D6D6D");
                break;
            }
            while (paramMotionEvent.getAction() != 1);
            //changeBackgroundColor(getResources().getColor(17170445));
            this.press = false;
        }
        while ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getY() > getHeight()) || (paramMotionEvent.getY() < 0.0F));
        this.isLastTouch = false;*/
        //if (!this.check);
        /*for (boolean bool = true; ; bool = false)
        {
            this.check = bool;
            if (this.onCheckListener != null)
                this.onCheckListener.onCheck(this.check);
            if (this.check)
                this.step = 0;
            if (!this.check)
                break;
            this.checkView.changeBackground();
            //return true;
        }*/
        return true;
    }

    protected void setAttributes(AttributeSet paramAttributeSet)
    {
        setBackgroundResource(R.drawable.background_checkbox);
        //setMinimumHeight(UIUtils.dpToPx(48.0F, getResources()));
        //setMinimumWidth(UIUtils.dpToPx(48.0F, getResources()));
        int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
        if (i != -1)
            setBackgroundColor(getResources().getColor(i));
        while (true)
        {
            if (paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "check", false))
                post(new Runnable()
                {
                    public void run()
                    {
                        CheckBox.this.setChecked(true);
                        CheckBox.this.setPressed(false);
                        CheckBox.this.changeBackgroundColor(CheckBox.this.getResources().getColor(android.R.color.transparent));
                    }
                });
            this.checkView = new Check(getContext());
           // RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(UIUtils.dpToPx(20.0F, getResources()), UIUtils.dpToPx(20.0F, getResources()));
            //localLayoutParams.addRule(11, -1);
            //this.checkView.setLayoutParams(localLayoutParams);
            //addView(this.checkView);
            /*String str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
            if (str != null)
                setBackgroundColor(Color.parseColor(str));*/
        }
    }

    public void setBackgroundColor(int paramInt)
    {
        this.backgroundColor = paramInt;
        if (isEnabled())
            this.beforeBackground = this.backgroundColor;
        changeBackgroundColor(paramInt);
    }

    public void setChecked(boolean paramBoolean)
    {
        this.check = paramBoolean;
        setPressed(false);
        changeBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (paramBoolean)
            this.step = 0;
        if (paramBoolean)
            this.checkView.changeBackground();
    }

    public void setOncheckListener(OnCheckListener paramOnCheckListener)
    {
        this.onCheckListener = paramOnCheckListener;
    }

    class Check extends View
    {
        Bitmap sprite;

        public Check(Context arg2)
        {
            super(arg2);
            setBackgroundResource(R.drawable.background_checkbox_uncheck);
            this.sprite = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sprite_check);
        }

        public void changeBackground()
        {
            if (CheckBox.this.check)
            {
                setBackgroundResource(R.drawable.background_checkbox_check);
                ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(CheckBox.this.backgroundColor);
                return;
            }
            setBackgroundResource(R.drawable.background_checkbox_uncheck);
        }

        protected void onDraw(Canvas paramCanvas)
        {
            super.onDraw(paramCanvas);
            if (CheckBox.this.check) {
                if (CheckBox.this.step < 11) {
                    CheckBox localCheckBox2 = CheckBox.this;
                    localCheckBox2.step = (1 + localCheckBox2.step);
                }
            }
            while (true)
            {
                Rect localRect1 = new Rect(40 * CheckBox.this.step, 0, 40 + 40 * CheckBox.this.step, 40);
                Rect localRect2 = new Rect(0, 0, -2 + getWidth(), getHeight());
                paramCanvas.drawBitmap(this.sprite, localRect1, localRect2, null);
                invalidate();
                if (CheckBox.this.step >= 0)
                {
                    CheckBox localCheckBox1 = CheckBox.this;
                    localCheckBox1.step = (-1 + localCheckBox1.step);
                }
                if (CheckBox.this.step == -1)
                    changeBackground();
            }
        }
    }

    public static abstract interface OnCheckListener
    {
        public abstract void onCheck(boolean paramBoolean);
    }
}
