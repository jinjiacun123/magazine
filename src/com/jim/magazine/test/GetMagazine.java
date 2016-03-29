package com.jim.magazine.test;

import com.jim.magazine.R;
import com.jim.magazine.service.MagazineDownloadService;
import com.jim.magazine.bean.BeanMagazine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;



public class GetMagazine extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_get_magazine);
		
		Button my_button = (Button)findViewById(R.id.button1);
		my_button.setOnClickListener(this );
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button1:
		{
			/*
			 "id":"768",
			"magazine":"PChouse",
			"publisher":"家居网",
			"volume":"0",
			"bookDownloadStatus":"1",
			"issue":"3月刊",
			"publishTime":"2016-03-10 16:42:29",
			"cover":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/cover\/768-big?201603101642",
			"thumb":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/cover\/768-small?201603101642",
			"dir-ver":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/content\/768-ver-dir",
			"summary":"",
			"url":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/768.zip",
			"base_url":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/seperatepack\/768\/768-base.zip",
			"base_url_prefix":"http:\/\/reader.pchouse.com.cn\/pchousemag\/android\/seperatepack\/768\/",
			"deviceType":"0",
			"md5":"6a100163d1f6d145991a6c9fc27b9e55",
			"bestTopicUrl":"",
			"size":"38.66MB"
			 * */
			BeanMagazine localMagazine = new BeanMagazine();
			localMagazine.setId("768");
			 //doTask(localMagazine);
			 sendTaskMeg(localMagazine);
		}
			break;
		}
	}
	
	  private void sendTaskMeg(BeanMagazine paramMagazine)
	  {
	    Intent localIntent = new Intent(this, MagazineDownloadService.class);
	    
	    String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	    String appDirName = "on_magazine";
	    String appDirPath = sdCardPath + "/" + appDirName + "/";
	    
	    Log.i("-------------------------jim", appDirPath);
	    
	    Bundle localBundle = new Bundle();
	    localBundle.putInt("type", 1);
	    localBundle.putString("id", "768");
	    localBundle.putSerializable("filePath",appDirPath);
	    localBundle.putString("taskUrl", "http://reader.pchouse.com.cn/pchousemag/android/768.zip");
	    localBundle.putInt("position", 1);
	    localIntent.putExtras(localBundle);
	    
	    startService(localIntent);
	  }
	
	
	
	
	
	
	
	
	
	
	
	
}
