package com.jim.magazine;

import com.jim.magazine.fragment.AppManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HowmemActivity extends Activity
{
	private AppManager manager;
	
	 @Override
	    protected void onCreate(Bundle arg0)
	    {
	        super.onCreate(arg0);
	        setContentView(R.layout.activity_howmem);
	        
	        ImageView ctlLeft = (ImageView)findViewById(R.id.ctl_left);
	        manager = AppManager.getInstance();
	        ctlLeft.setOnClickListener(new OnClickListener(){
	        	
	        	@Override
	        	public void onClick(View arg0)
	        	{
	        		manager.killActivity(HowmemActivity.this);
	        	}
	        });
	    }
}