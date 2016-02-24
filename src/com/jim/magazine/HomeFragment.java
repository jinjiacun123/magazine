package com.jim.magazine;

import java.util.List;

import com.jim.magazine.R;
import com.jim.magazine.entity.Company;
import com.jim.magazine.entity.Exposure_Dynamic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页
 * 
 * @author jim
 * 
 */
public class HomeFragment extends Fragment {

	private View view;// 缓存Fragment view
	private Button btn_homepage_regist;// 注册控件
	private TextView tv_homepage_version;// 版本号控件
	private EditText et_homepage_search;// 搜索输入框
	private ImageView iv_homepage_search;// 搜索控件
	private PackageManager manager;// 包管理器
	private PackageInfo packageinfo;// 当前项目的包
	private SharedPreferences preferences;
	private boolean login_type = false;
	private ConnectivityManager manager2;// 获取网咯的管理器
	private long id;
	private List<Company> companys;
	private Company company;
	private String search_key;// 搜索关键字
	private String old_count;// 获取的公司识别次数
	private List<Company> companys2;
	private TextView tv_exocommpany1;
	private TextView tv_exocontent1;
	private TextView tv_exoname1;
	private TextView tv_exotime1;
	private ImageView tv_exoimage2;
	private TextView tv_exocommpany2;
	private TextView tv_exocontent2;
	private TextView tv_exoname2;
	private TextView tv_exotime2;
	private LinearLayout layout;
	private Exposure_Dynamic dynamic;//第一条动态
	private Exposure_Dynamic exposure_Dynamic2;//第二条动态

	private Handler handler = new Handler() {
		
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String result = msg.obj.toString();
				if ("null".equals(result)) {
					Toast.makeText(getActivity(), "无信号",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "有信号",                                       
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		};
	};
	private TextView tv_background;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			                 ViewGroup container,
			                 Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_home, null);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCount();
	}
	
	//===========
	private void setData(String name, String nature, String trade, int type) {
			Toast.makeText(getActivity(), "setData", 0).show();
	}


	private void initCount() {
			Toast.makeText(getActivity(), "initCount", 0).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		//�Ƿ��½����½������ע�ᰴť
		login_type = preferences.getBoolean("login_type", false);
		if (login_type) {
			btn_homepage_regist.setVisibility(View.GONE);
		} else {
			btn_homepage_regist.setVisibility(View.VISIBLE);
		}
	}

	
	private void initView() {
		//第一条曝光的内容
				
				tv_exocommpany1 = (TextView) getActivity().findViewById(R.id.tv_exocommpany1);
				tv_exocontent1 = (TextView) getActivity().findViewById(R.id.tv_exocontent1);
				tv_exoname1 = (TextView) getActivity().findViewById(R.id.tv_exoname1);
				tv_exotime1 = (TextView) getActivity().findViewById(R.id.tv_exotime1);
				//第一条曝光的整个布局
				LinearLayout ll_top = (LinearLayout) getActivity().findViewById(R.id.ll_top);
				
				//第二条曝光的整个布局
				LinearLayout ll_center = (LinearLayout) getActivity().findViewById(R.id.ll_center);
				// 查询最多
				ImageView iv_search = (ImageView) getActivity().findViewById(
						R.id.iv_search);				
				// 公司识别次数布局
				//rl_left = (RelativeLayout) getActivity().findViewById(R.id.rl_left);
				// 公司及平台数
				//rl_right = (RelativeLayout) getActivity().findViewById(R.id.rl_right);
				manager2 = (ConnectivityManager) getActivity().getSystemService(
						Context.CONNECTIVITY_SERVICE);
				btn_homepage_regist = (Button) this.getView().findViewById(
						R.id.btn_homepage_regist);
				// tv_homepage_version = (TextView) this.getView().findViewById(
				// R.id.tv_homepage_version);
				et_homepage_search = (EditText) this.getView().findViewById(
						R.id.et_homepage_search);
				iv_homepage_search = (ImageView) this.getView().findViewById(
						R.id.iv_homepage_search);
				preferences = this.getActivity().getSharedPreferences("Login_UserInfo",
						Context.MODE_PRIVATE);
				id = preferences.getLong("user_id", 0);	
		
	}

	// ��ȡ��ǰ�汾
	private void initVersion() {
		manager = getActivity().getPackageManager();
		try {
			packageinfo = manager.getPackageInfo(
					getActivity().getPackageName(), 0);
			String app_version = packageinfo.versionName;
			tv_homepage_version.setText("�汾�ţ�" + app_version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
