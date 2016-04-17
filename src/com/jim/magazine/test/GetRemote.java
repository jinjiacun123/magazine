package com.jim.magazine.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.jim.magazine.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class GetRemote extends Activity {
	 private static final String IMG_URL= "http://192.168.1.131/yms_api/Public/media/ad/item01.jpg"; 
			 //"http://images.cnitblog.com/i/169207/201408/112229149526951.png"; 
			 //"http://192.168.1.131/yms_api/Public/media/ad/item01.jpg"; 
			 //"http://10.101.6.121:8080/drawpanel/smile.jpg";  
	 
	 public static String GetSystemVersion(){
		 return android.os.Build.VERSION.RELEASE;
		 }
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_get_remote_img);
		//ImageView remote_img = (ImageView)findViewById(R.id.remote_img);
		//获取远程图片
		//http://192.168.1.131/yms_api/Public/media/ad/item01.jpg
		//String url = "http://192.168.1.131/yms_api/Public/media/ad/item01.jpg";
		  //ImageView remote_img = (ImageView)findViewById(R.id.remote_img);
		//ImageLoader my_image_loader = new ImageLoader(this, false);
		//my_image_loader.DisplayImage(url, remote_img);
			/* Bitmap bitmap = getBitmapFromUrl(IMG_URL);
			 remote_img.setImageBitmap(bitmap);
			 remote_img.invalidate();*/
		//remote_img.setImageBitmap(my_image_loader.getBitmap(url));
		new MyThread().start();
	}
	
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			switch(msg.what)
			{
			case 0://获取图片
			{
				ImageView remote_img = (ImageView)findViewById(R.id.remote_img);
				//ImageLoader my_image_loader = new ImageLoader(this, false);
				//my_image_loader.DisplayImage(url, remote_img);
				 Bitmap bitmap = (Bitmap)msg.obj;
				 remote_img.setImageBitmap(bitmap);
				 remote_img.invalidate();
			}
			break;
			}
		}
	};
	
    class MyThread extends Thread {
		@Override
		public void run() {
			 URL url;
             Bitmap bitmap = null;
             Message msg = new Message();
             try {
                     url = new URL(IMG_URL);
                     InputStream is = url.openConnection().getInputStream();
                     BufferedInputStream bis = new BufferedInputStream(is);
                     bitmap = BitmapFactory.decodeStream(bis);
                     bis.close();
             } catch (MalformedURLException e) {
                     e.printStackTrace();
             } catch (IOException e) {
                     e.printStackTrace();
             }
             Log.i("jim","down picture");
            msg.what=0;
            msg.obj = bitmap;
            handler.sendMessage(msg);
			super.run();
		}
	}	
}
