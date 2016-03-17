package com.jim.magazine.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

import com.jim.magazine.R;

public class Animation extends Activity {
	
	private Button btn_start, btn_stop;
    private ImageView iv_frame;
    private AnimationDrawable frameAnim;
	
	 @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.test_activity_frameanim);

	        btn_start = (Button) findViewById(R.id.btn_start);
	        btn_stop = (Button) findViewById(R.id.btn_stop);

	        btn_start.setOnClickListener(click);
	        btn_stop.setOnClickListener(click);

	        iv_frame = (ImageView) findViewById(R.id.iv_frame);
	        
	        // 通过逐帧动画的资源文件获得AnimationDrawable示例
	        frameAnim=(AnimationDrawable) getResources().getDrawable(R.animator.bullet_anim);
	        // 把AnimationDrawable设置为ImageView的背景
	        iv_frame.setBackgroundDrawable(frameAnim);
	}
	 
	 private View.OnClickListener click = new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            switch (v.getId()) {
	            case R.id.btn_start:
	                start();
	                break;
	            case R.id.btn_stop:
	                stop();
	                break;
	            default:
	                break;
	            }
	        }
	    };

	    /**
	     * 开始播放
	     */
	    protected void start() {
	        if (frameAnim != null && !frameAnim.isRunning()) {
	            frameAnim.start();
	            Toast.makeText(Animation.this, "开始播放", 0).show();
	            Log.i("main", "index 为5的帧持续时间为："+frameAnim.getDuration(5)+"毫秒");
	            Log.i("main", "当前AnimationDrawable一共有"+frameAnim.getNumberOfFrames()+"帧");
	        }
	    }

	    /**
	     * 停止播放
	     */
	    protected void stop() {
	        if (frameAnim != null && frameAnim.isRunning()) {
	            frameAnim.stop();
	            Toast.makeText(Animation.this, "停止播放", 0).show();
	        }
	    }
}
