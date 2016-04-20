package com.jim.magazine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

//意见反馈
public class FeedbackActivity extends Activity implements OnClickListener{
	 @Override
	    protected void onCreate(Bundle arg0)
	    {
	        super.onCreate(arg0);
	        setContentView(R.layout.activity_feedback);
	    }

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.finish://完成
		{
			
		}
		break;
		case R.id.cancel://取消
		{
			
		}
		}
		
	}
}