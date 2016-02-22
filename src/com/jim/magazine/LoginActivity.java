package com.jim.magazine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.jim.magazine.R;
import com.jim.magazine.bean.BeanUser;
import com.jim.magazine.bean.BeanBase.API_METHOD_INDEX;
import com.jim.magazine.entity.User;
import com.jim.magazine.help.DialogUtil;
import com.jim.magazine.help.HttpPostThread;
import com.jim.magazine.help.KeyBoardUtil;
import com.jim.magazine.help.NetworkUtil;
import com.jim.magazine.help.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户登录
 * 
 * @author jim
 * 
 */
public class LoginActivity extends FragmentActivity implements OnClickListener {
	private EditText       txtName;
	private EditText       txtPasswd;
	private ImageView      ivLogin;
	private ProgressBar    pbLogin;
	private String         openId;
	private String         nickName;
	private String         headImgUrl;
	private CheckBox       cbPwd;
	public  static LoginActivity   loginPage;
	private RelativeLayout ivActionBar;
	
	private BeanUser bean_user = new BeanUser();
	
	//提醒消息
	private String tipMessage[] = {
		"登录成功",                //0
		"登录失败",                //1
		"用户名不存在或密码错误",    //2
		"用户被限制登录",           //3
		"用户访问的IP被限制",       //4
		"服务器连接失败",           //5
		"账号或密码不为空",         //6
		"登录中...",              //7
		"用户登录",               //8
		"注册",                  //9
	};
	private void getMessage(Integer index)
	{
		Toast.makeText(getApplicationContext(), tipMessage[index], 0).show();
	}

	// 登陆
		Handler handler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					String result = (String) msg.obj;
					User user = bean_user.ParseLoginResult(result);
					if (user != null) {
						switch (bean_user.getStatus()) {
						case 0:
							getMessage(0);
							
							getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE)
									.edit()
									.putBoolean("login_type", true)
									.putLong("id",       Long.valueOf(user.getId()))
									.putString("nickname",    user.getNickname())
									.putString("sex",         user.getSex())
									.commit();
							
							//if (Util.getRegisterType(LoginActivity.this)) {
								Intent intent = new Intent(LoginActivity.this,MainActivity.class);
								startActivity(intent);
								Util.UpdateRegisterType(LoginActivity.this, false);
							//}
							finish();
							break;
						case -1:
							getMessage(1);
							break;
						case -2:
							getMessage(2);
							break;
						case -3:
							getMessage(3);
							break;
						case -4:
							getMessage(4);
							break;
						default:
							break;
						}
					} else {
						getMessage(5);
					}
					//pb_login.setVisibility(View.INVISIBLE);
					DialogUtil.dismissProgressDialog();
					//iv_login.setClickable(true);
					break;
				default:
					break;
				}
			}
		};

	// 登录状态
	Handler login_hander = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginPage=this;
		initView();		
	}

	private void initView() {		
		// 进度条
		pbLogin = (ProgressBar) findViewById(R.id.pb_login);
		
		// 标题
		ivActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		ivActionBar.setVisibility(View.VISIBLE);
		ivActionBar.setOnClickListener(this);
		
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText(tipMessage[8]);
		
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setText(tipMessage[9]);
		iv_home.setVisibility(View.VISIBLE);
		iv_home.setOnClickListener(this);
		
		txtName   = (EditText) findViewById(R.id.input_user);    // 账号
		txtPasswd = (EditText) findViewById(R.id.input_pwd);     // 密码		
		findViewById(R.id.jump_findpwd).setOnClickListener(this); // 找回密码
		
		//登录
		ivLogin = (ImageView) findViewById(R.id.iv_login);
		ivLogin.setOnClickListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return Util.getRegisterType(LoginActivity.this)? false:super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jump_findpwd:// 找回密码			
			/*
			Intent intent = new Intent(MineFragment_Login.this, MineFragment_FindPwd.class);
			startActivity(intent);
			*/
			break;
		case R.id.iv_login:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),LoginActivity.this);

			// 登录
			String name    = txtName.getText().toString();
			String passwd  = txtPasswd.getText().toString();
			
			//临时跳转到主页
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);			
			if (name != null 
			 && passwd != null
			 && !"".equals(name)
			 && !"".equals(passwd)) {
				// 判断网络是否正常
				boolean networkConnected = NetworkUtil.isNetworkConnected(LoginActivity.this);
				if (networkConnected) {
					DialogUtil.showProgressDialog(LoginActivity.this, tipMessage[7], 0 );
					Map<String,Object> my_request = new HashMap<String, Object>();
					my_request.put("name", name);
					my_request.put("passwd", passwd);
					List<NameValuePair> params = bean_user.CallApi(API_METHOD_INDEX.API_USER_LOGIN, 
							                                       my_request);
					new HttpPostThread(params, handler, 0).start();
				} else {					
					DialogUtil.dismissProgressDialog();
					getMessage(5);
				}
			} else {
				getMessage(6);
			}
			break;
		case R.id.iv_ActionBar:
			// 返回上一页
			finish();
			break;
		case R.id.iv_home:// 注册
			Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	Handler cancel_bind = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				break;

			default:
				break;
			}
		};
	};
}
