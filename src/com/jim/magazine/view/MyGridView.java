package com.jim.magazine.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.jim.magazine.R;


public class MyGridView extends GridView
{
	private Bitmap background;
	
	public MyGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bookshelf_layer_center);		
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
	}
}