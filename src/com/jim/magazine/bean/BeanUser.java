package com.jim.magazine.bean;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jim.magazine.entity.User;

/**
 * 用户业务逻辑及其接口调用
 * 
 * @author jim
 * 
 */
public class BeanUser extends BeanBase implements IUser{

	private int         status;

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

	private User        user;
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the userl
	 */
	public List<User> getUserl() {
		return userl;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param userl the userl to set
	 */
	public void setUserl(List<User> userl) {
		this.userl = userl;
	}

	private List<User>  userl;
	
	public BeanUser() {
		
	}	
	
	//------------------------解析接口返回方法----------------------------------
	
	//解析登录接口返回结果
	@Override
	public User ParseLoginResult(String result)
	{	
		User user = new User();
		try {
			JSONObject object = new JSONObject(result);
			int status_code = Integer.valueOf(object.getString("status_code"));
			if (status_code == 200) {
				JSONObject object2 = object.getJSONObject("content");
				if (object2 != null) {
					Integer is_success = Integer.valueOf(object2.getString("is_success"));
					this.status = is_success;
					if (is_success == 0) {
						user.setId(object2.getString("id"));
						user.setNickname(object2.getString("nickname"));
						user.setSex(object2.getString("sex"));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	//解析登录注册返回结果
	@Override
	public void ParseRegisterResult(String result)
	{
		try {
			JSONObject object = new JSONObject(result);
			int status_code = Integer.valueOf(object.getString("status_code"));
			if (status_code == 200) {
				JSONObject object2 = object.getJSONObject("content");
				if (object2 != null) {
					Integer is_success = Integer.valueOf(object2.getString("is_success"));
					this.status = is_success;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//判断用户名是否存在
	@Override
	public void ParseNameExistsResult(String result) {
		// TODO Auto-generated method stub
		
	}
}
