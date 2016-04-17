package com.jim.magazine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		
		//返回按钮
		ImageView my_back = (ImageView)findViewById(R.id.back);
		my_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				SettingActivity.this.finish();
			}
		});
		
	}
}
