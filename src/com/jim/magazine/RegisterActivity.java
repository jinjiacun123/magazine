package com.jim.magazine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.jim.magazine.R;
import com.jim.magazine.bean.BeanUser;
import com.jim.magazine.bean.BeanBase.API_METHOD_INDEX;
import com.jim.magazine.entity.User;
import com.jim.magazine.fragment.AgreementFragment;
import com.jim.magazine.fragment.AppManager;
import com.jim.magazine.help.Code;
import com.jim.magazine.help.HttpPostThread;
import com.jim.magazine.help.KeyBoardUtil;
import com.jim.magazine.help.NetworkUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
 * 用户注册
 * 
 * @author jim
 * 
 */
public class RegisterActivity extends Activity implements OnClickListener {
	private AppManager manager;
	private ImageView  register_code;
	private EditText   txt_name;
	private EditText   txt_passwd;
	private EditText   register_ed_code;
	private CheckBox   cb_clause;
	private boolean    is_register = false; // 手机是否注册状态
	private boolean    pwd_type    = false; // 密码状态
	private boolean    code_type   = false; // 验证码状态
	
	private BeanUser   beanUser = new BeanUser();
	
	// 注册
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				if (result != null 
				&& !"".equals(result)) {
					User user = beanUser.ParseLoginResult(result);
					if (user != null) {
						int is_sucess = beanUser.getStatus();
						if (is_sucess == 0) {
							Toast.makeText(getApplicationContext(), "注册成功", 0)
								 .show();
							manager.killActivity(RegisterActivity.this);
						} else if (is_sucess == -1) {
							Toast.makeText(getApplicationContext(), "注册失败", 0)
								 .show();
						} else {
							Toast.makeText(getApplicationContext(), "用户名已存在",0)
							     .show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
							 .show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
						 .show();
				}
				iv_register.setClickable(true);
				pb_register.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		};
	};

	// 判断用户名是否存在
	Handler phone_handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				if (result != null && !"".equals(result)) {
					beanUser.ParseNameExistsResult(result);
					int is_success = beanUser.getStatus();
					if (is_success == 0) {
						txt_name.setError("用户名已注册");
						is_register = false;
					} else if (is_success == -2) {
						Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
								.show();
					} else {
						is_register = true;
					}
				} else {
					Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
							.show();
				}
				break;

			default:
				break;
			}
		};
	};
	private ProgressBar pb_register;
	private ImageView iv_register;
	private String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(RegisterActivity.this);
	}

	private void initView() {
		// 进度条
		pb_register = (ProgressBar) findViewById(R.id.pb_register);
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("注册");
		// tv_ClassName.setMovementMethod(movement);
		// 用户名
		txt_name = (EditText) findViewById(R.id.register_ed_phone);
		// 登录密码
		txt_passwd = (EditText) findViewById(R.id.register_ed_pwd);
		// 输入验证码
		register_ed_code = (EditText) findViewById(R.id.register_ed_code);
		// 验证码图画
		register_code = (ImageView) findViewById(R.id.register_code_bitmap);
		register_code.setOnClickListener(this);
		// 勾选用户协议
		cb_clause = (CheckBox) findViewById(R.id.cb_clause);
		// 查看用户协议
		findViewById(R.id.watch_clause).setOnClickListener(this);
		// 注册
		iv_register = (ImageView) findViewById(R.id.iv_register);
		iv_register.setOnClickListener(this);
		setOnClickListener();
	}

	private void setOnClickListener() {
		// 用户名
		txt_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		
		// 密码
		txt_passwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && !"".equals(s)) {
					/*
					if ((s.length() > 16 || s.length() < 6)) {
						pwd_type = false;
						register_ed_pwd.setError("长度在6到16位之间");
					} else {
					*/
						pwd_type = true;
					//}
				} else {
					txt_passwd.setError("账号或密码不为空");
				}
			}
		});
		// 验证码
		register_ed_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && !"".equals(s) && s.length() == 4) {
					if (code != null && !"".equals(code)) {
						if (code.equals(s.toString())) {
							code_type = true;
						} else {
							code_type = false;
							//register_ed_code.setError("验证码输入有误");
						}
					} else {
						Toast.makeText(getApplicationContext(), "请先点击获取验证码", 0)
								.show();
					}
				} else {
					//register_ed_code.setError("验证码为4位");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(RegisterActivity.this);
			break;
		case R.id.iv_register:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					RegisterActivity.this);

			// 注册
			if (cb_clause.isChecked() && is_register && pwd_type && code_type) {
				String name    = txt_name.getText().toString();
				String passwd  = txt_passwd.getText().toString();
				// 判断网络是否正常
				boolean networkConnected = NetworkUtil.isNetworkConnected(RegisterActivity.this);
				if (networkConnected) {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					Map<String, Object> my_request = new HashMap<String, Object>();
					try {
						my_request.put("name",   name);
						my_request.put("passwd", passwd);
						params = beanUser.CallApi(API_METHOD_INDEX.API_USER_REGISTER, my_request);
					} catch (Exception e) {
						e.printStackTrace();
					}
					new HttpPostThread(params, handler, 0).start();
					iv_register.setClickable(false);
					pb_register.setVisibility(View.VISIBLE);
				} else {
					iv_register.setClickable(false);
					pb_register.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
				}
			} else {
				String string = txt_name.getText().toString();
				if(txt_name.getEditableText().length()==0){
					Toast.makeText(getApplicationContext(), "用户名不为空", 0)
					     .show();
				}
				else if (!code_type) {
					Toast.makeText(getApplicationContext(), "验证码输入有误", 0)
						 .show();
				} else if (!cb_clause.isChecked()) {
					Toast.makeText(getApplicationContext(), "请先勾选用户协议", 0)
						 .show();
				}
			}
			break;
		case R.id.watch_clause:
			// 查看用户协议
			Intent intent = new Intent(RegisterActivity.this,
					AgreementFragment.class);
			startActivity(intent);
			break;
		case R.id.register_code_bitmap:
			boolean Code_type=false;
			// 更换验证码
			String phone = txt_name.getText().toString();
			String pwd = txt_passwd.getText().toString();
			if (phone != null && !"".equals(phone) && pwd != null
					&& !"".equals(pwd)) {
				if (is_register && pwd_type) {
					register_code.setImageBitmap(Code.getInstance().createBitmap());
					code = Code.getInstance().getCode();
					register_ed_code.setText("");																
				} else {
					if (!is_register) {
						Toast.makeText(getApplicationContext(), "用户名已注册", 0)
								.show();
					} else if (!pwd_type) {
						Toast.makeText(getApplicationContext(),
								"长度在6到16位之间", 0).show();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), "用户名或密码不为空", 0).show();
			}
			break;
		default:
			break;
		}

	}
}
