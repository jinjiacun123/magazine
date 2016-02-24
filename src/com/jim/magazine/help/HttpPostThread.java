package com.jim.magazine.help;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.jim.magazine.config.PhpUrl;

import android.os.Handler;
import android.os.Message;

/**
 * HttpPost请求的封装方法
 * 
 * @author jim
 * 
 */
public class HttpPostThread extends Thread{

	private List<NameValuePair> params;//请求参数
	private Handler handler;//异步通知的handler
	private int type ;//类型 handler.what的值
	private HttpPost post;
	private HttpClient client;
	private String result ="null";//返回的结果值
	private Message msg;
	
	public HttpPostThread(List<NameValuePair> params,Handler handler,int type) {
		this.handler = handler;
		this.params = params;
		this.type = type;
		msg = new Message();
	}
	
	
	@Override
	public void run() {
		post = new HttpPost(PhpUrl.API_URL);
		client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 20000);
		try {
			post.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3");
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity());
			}else{
				if (response.getStatusLine().getStatusCode() == 501) {
					handler.sendEmptyMessage(1000);
				}
			}
			msg.obj = result;
			msg.what = type;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();
		}
	}
}
