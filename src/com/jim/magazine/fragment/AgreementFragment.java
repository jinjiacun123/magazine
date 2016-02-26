package com.jim.magazine.fragment;

import com.jim.magazine.R;
import com.jim.magazine.R.id;
import com.jim.magazine.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 用户注册协议
 * 
 * @author jim
 * 
 */
public class AgreementFragment extends Activity implements OnClickListener {
	private AppManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(AgreementFragment.this);
	}

	private void initView() {
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("用户协议");
		// 用户协议
		WebView wv_clause = (WebView) findViewById(R.id.wv_clause);
		wv_clause.loadUrl("file:///android_asset/agreement.html");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(AgreementFragment.this);
			break;

		default:
			break;
		}

	}
}
