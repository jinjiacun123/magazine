package com.jim.magazine.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jim.magazine.entity.HomeArticle;

/**
 * 辅助业务逻辑及其接口调用
 * 
 * @author jim
 * 
 */
public class BeanHelp extends BeanBase implements IHelp{

	//解析首页图片切换返回结果
	@Override
	public String[] ParseHomeImgResult(String result) {
		String[] img_url = new String[5];
		try {
			JSONObject object = new JSONObject(result);
			int status_code = Integer.valueOf(object.getString("status_code"));
			if (status_code == 200) {
				JSONObject object2 = object.getJSONObject("content");
				if (object2 != null) {					
					JSONArray json_array = object2.getJSONArray("list");
					JSONObject json_object;
					for (int i = 0; i < json_array.length(); i++) {
						json_object = (JSONObject) json_array.get(i);
						img_url[i] = json_object.toString();
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return img_url;
	}
	
	//解析首页文章切换返回结果
		@Override
		public ArrayList<HomeArticle> ParseHomeArticleListResult(String result) {
			ArrayList<HomeArticle> article_list = new ArrayList<HomeArticle>();
			try {
				JSONObject object = new JSONObject(result);
				int status_code = Integer.valueOf(object.getString("status_code"));
				if (status_code == 200) {
					JSONObject object2 = object.getJSONObject("content");
					if (object2 != null) {					
						JSONArray json_array = object2.getJSONArray("list");
						JSONObject json_object;
						HomeArticle home_article;
						for (int i = 0; i < json_array.length(); i++) {
							json_object = (JSONObject) json_array.get(i);
							home_article = new HomeArticle();
							home_article.setTitle(json_object.getString("title"));
							home_article.setContent(json_object.getString("content"));
							home_article.setP1(json_object.getString("p1"));
							home_article.setP2(json_object.getString("p2"));
							article_list.add(home_article);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return article_list;
		}

}
