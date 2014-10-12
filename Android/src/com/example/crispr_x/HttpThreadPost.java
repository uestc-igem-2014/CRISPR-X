package com.example.crispr_x;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.os.Handler;
import android.os.Message;

public class HttpThreadPost extends Thread {
	public static final int LOGIN = 21; // 用户登陆
	public static final int LOGON = 22; // 用户注册
	public static final int LOGOUT = 23; // 退出登录
	public static final int HISTORY = 24; // 获取历史
	public static final int CHECKID = 25; // 查询ID
	public static final int CHECKFILE = 26; // 查询文件
	public static final int CHESPECIES = 27; // 查询物种

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
		case CHECKFILE: // 查询文件
			msg.what = CHECKFILE;
			break;
		case CHESPECIES: // 查询文件
			msg.what = CHESPECIES;
			break;
		default:
			break;
		}
		msg.obj = json;
		handler.sendMessage(msg);
	}
}
