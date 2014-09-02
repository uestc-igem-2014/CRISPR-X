package com.example.crispr_x;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.os.Handler;
import android.os.Message;

public class HttpThreadPost extends Thread {
	public static final int LOGIN = 1; // 用户登陆
	public static final int LOGON = 2; // 用户注册
	public static final int LOGOUT = 3; // 退出登录
	public static final int HISTORY = 4; // 获取历史
	public static final int CHECKID = 5; // 查询ID

	List<NameValuePair> params;
	String httpUrl;
	int type;
	Handler handler;

	HttpThreadPost(String url, List<NameValuePair> params, int type, Handler handler) {
		
		this.params = new ArrayList<NameValuePair>();
		this.params.addAll(params);
		this.type = type;
		this.httpUrl = url;
		this.handler = handler;
	}

	public void run() {
		HttpRunner jsonParser = new HttpRunner();
		String json = jsonParser.makeHttpPOST(httpUrl, params);
		Message msg = new Message();
		switch (type) {
		case LOGIN: // 用户登陆
			msg.what = LOGIN;
			break;
		case LOGON: // 用户注册
			msg.what = LOGON;
			break;
		case LOGOUT: // 退出登录
			msg.what = LOGOUT;
			break;
		case HISTORY: // 获取历史
			msg.what = HISTORY;
			break;
		case CHECKID: // 查询ID
			msg.what = CHECKID;
			break;
		default:
			break;
		}
		msg.obj = json;
		handler.sendMessage(msg);
	}
}
