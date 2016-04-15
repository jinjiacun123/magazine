package com.jim.magazine.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class BeanBase {
	protected int         status;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	//接口调用方法索引
	public enum API_METHOD_INDEX {
		//user1-(0,9)
		API_USER_EXISTS_NAME, //检查用户名是否存在
		API_USER_REGISTER,    //用户注册
		API_USER_LOGIN,       //登录
		API_USER_3,
		API_USER_4,
		API_USER_5,
		API_USER_6,
		API_USER_7,
		API_USER_8,
		API_USER_9,
		//help2-(10,19)
		API_HELP_HOME_FIVE_IMG_URL,   //10
		API_HELP_HOME_TEN_ARTICLE,     //11
		API_HELP_HOME_2,
		API_HELP_HOME_3,
		API_HELP_HOME_4,
		API_HELP_HOME_5,
		API_HELP_HOME_6,
		API_HELP_HOME_7,
		API_HELP_HOME_8,
		API_HELP_HOME_9,
		NULL;
	};	
	
	//接口调用方法映射
	protected Map<Integer, String> api_method_name = new HashMap<Integer, String>(){{
		//user
	    put(0, "User.exists_name"); //检查用户名是否存在
	    put(1, "User.register");    //用户注册
	    put(2, "User.login");		//登录
	    //help
	    put(10, "Help.home_five_img_url");//获取首页广告图片
	    put(11, "Help.home_ten_article"); //获取首页文章列表
	}};
	
	//接口方法模板
	protected Map<Integer, Map<String,Object>> api_method_params_template = new HashMap<Integer, Map<String,Object>>(){{
		//user
		put(0,   new HashMap<String,Object>(){{//检查用户名是否存在
			put("name",null);
		}}); 
		put(1,   new HashMap<String, Object>(){{//用户注册
			put("name",null);
			put("passwd",null);
		}});    
		put(2,   new HashMap<String, Object>(){{//登录
			put("name", null);
			put("passwd", null);
		}});
		//help
		put(10,   new HashMap<String, Object>(){{//获取首页广告图片
		}});
		put(11,   new HashMap<String, Object>(){{//获取首页文章列表
		}});
	}};
	
	
	public BeanBase() {
		
	}
	
	//调用api接口
	public List<NameValuePair> CallApi(API_METHOD_INDEX method_index, Map request_value)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject object = new JSONObject();
		//获取当前方法的请求参数
		Map<String, Object> request_params = this.api_method_params_template.get(method_index.ordinal());		
		String key = "";
		try {
			for(Map.Entry<String, Object> entry:request_params.entrySet())
			{
				key = entry.getKey();
				object.put(key, request_value.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("method", this.api_method_name.get(method_index.ordinal())));
		params.add(new BasicNameValuePair("content", object.toString()));
			
		return params; 
	}

}
