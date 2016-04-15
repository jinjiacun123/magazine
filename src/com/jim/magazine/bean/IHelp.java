package com.jim.magazine.bean;

import java.util.ArrayList;

import com.jim.magazine.entity.HomeArticle;
import com.jim.magazine.entity.User;

public interface IHelp {
	public abstract String[] ParseHomeImgResult(String result);        //解析首页接口返回结果
	public ArrayList<HomeArticle> ParseHomeArticleListResult(String result);				 //解析首页文章切换返回结果
}
