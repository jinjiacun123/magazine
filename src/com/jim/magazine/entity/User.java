package com.jim.magazine.entity;

/**
 * 用户信息
 * 
 * @author jim
 * 
 */
public class User{		
	
	private String id;      	 // id
	private String nickname; 	 // 昵称
	private String sex;      	 // 性别

	public User() {
		
	}


	/**
	 * @param id 
	 * @param nickname
	 * @param sex
	 * */
	public User(String id, 
			    String nickname, 
			    String sex) 
	{
		super();
		this.id            = id;
		this.nickname      = nickname;
		this.sex           = sex;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id
				+ ", nickname=" + nickname 
				+ ", sex=" + sex
				+ "]";
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
