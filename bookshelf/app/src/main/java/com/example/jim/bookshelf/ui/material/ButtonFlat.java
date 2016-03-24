package com.example.jim.bookshelf.ui.material;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jim.bookshelf.R;
import com.example.jim.bookshelf.utils.UIUtils;

/**
 * Created by jim on 16-3-19.
 */
public class ButtonFlat extends Button
{
    TextView textButton;

    public ButtonFlat(Context paramContext)
    {
        super(paramContext, null);
    }

    public ButtonFlat(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public String getText()
    {
        return this.textButton.getText().toString();
    }

    public TextView getTextView()
    {
        return this.textButton;
    }

    protected int makePressColor()
    {
        return Color.parseColor("#88DDDDDD");
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        /*if (this.x != -1.0F)
        {
            Paint localPaint = new Paint();
            localPaint.setAntiAlias(true);
            localPaint.setColor(makePressColor());
            paramCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
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
        }
        invalidate();*/
    }

    protected void setAttributes(AttributeSet paramAttributeSet)
    {
        /*int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
        String str1;
        if (i != -1)
        {
            str1 = getResources().getString(i);
            if (str1 != null)
            {
                this.textButton = new TextView(getContext());
                this.textButton.setText(str1.toUpperCase());
                this.textButton.setTextColor(this.backgroundColor);
                this.textButton.setTypeface(null, 1);
                RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
                localLayoutParams.addRule(13, -1);
                this.textButton.setLayoutParams(localLayoutParams);
                addView(this.textButton);
            }
            int j = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
            if (j == -1)
                break label161;
            setBackgroundColor(getResources().getColor(j));
        }
        String str2="";
        do
        {
            str1 = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
            str2 = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
        }
        while (str2 == null);
        setBackgroundColor(Color.parseColor(str2));*/
    }

    public void setBackgroundColor(int paramInt)
    {
       /* this.backgroundColor = paramInt;
        if (isEnabled())
            this.beforeBackground = this.backgroundColor;
        this.textButton.setTextColor(paramInt);*/
    }

    protected void setDefaultProperties()
    {
        /*this.minHeight = 36;
        this.minWidth = 88;
        this.rippleSize = 3;
        setMinimumHeight(UIUtils.dpToPx(this.minHeight, getResources()));
        setMinimumWidth(UIUtils.dpToPx(this.minWidth, getResources()));
        setBackgroundResource(R.drawable.background_transparent);*/
    }

    public void setText(String paramString)
    {
        this.textButton.setText(paramString.toUpperCase());
    }
}
