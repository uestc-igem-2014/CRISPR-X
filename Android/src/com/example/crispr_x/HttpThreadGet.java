package com.example.crispr_x;

import java.util.List;
import org.apache.http.NameValuePair;
import android.os.Handler;
import android.os.Message;

public class HttpThreadGet extends Thread {
	public static final int MAIN_SUBMIT = 0; // 主程序提交

	List<NameValuePair> params;
	String httpUrl;
	int type;
	Handler handler;

	HttpThreadGet(String url, int type, Handler handler) {
		this.type = type;
		this.httpUrl = url;
		this.handler = handler;
	}

	public void run() {
		HttpRunner jsonParser = new HttpRunner();
		String json = jsonParser.makeHttpGET(httpUrl);
		Message msg = new Message();
		switch (type) {
		case MAIN_SUBMIT: // 主程序提交
			msg.what = MAIN_SUBMIT;
			break;
		default:
			break;
		}
		msg.obj = json;
		handler.sendMessage(msg);
	}
}
