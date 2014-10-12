package com.example.crispr_x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryActivity extends Activity {

	private ListView lvHistory;
	private ListView lvMenu;
	private EditText etCheck; // check编辑框
	private Button btnCheck; // check按钮

	private long timeInterval = 60 * 1000;
	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	boolean isLogin = false;
	String userName = null;
	String token = null;
	SimpleAdapter listItemAdapter,listItemAdapter2;
	ProgressDialog pDialog;
	AlertDialog alertDialog;
	Handler timeHandler;
	Runnable runnable1;
	HttpThreadGet myHttpThreadGet;
	HttpThreadPost myHttpThreadPost;
	static Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;

		setContentView(R.layout.activity_history);

		isLogin = new BackGroundService().getIsLogin();
		token = new BackGroundService().getToken();
		userName = new BackGroundService().getUserName();
		
		lvHistory = (ListView) findViewById(R.id.listView_history);
		lvMenu = (ListView) findViewById(R.id.listView_menu);

		/************************** 界面增加进度显示框 *************************/

		pDialog = new ProgressDialog(this);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);


		/**************** 定时器 ****************/

		timeHandler = new Handler();

		runnable1 = new Runnable() {
			@Override
			public void run() {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(), "Time out", Toast.LENGTH_SHORT)
						.show();
			}
		};
		
		/************************** msg接收 *************************/

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String result = null;
				switch (msg.what) {
				case HttpThreadPost.CHECKID:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入CHECKID");
					result = (String) msg.obj;
					startService();
					if (getStatus(result)) {
						saveMessage(result);
						Intent intent = new Intent(HistoryActivity.this,ResultActivity.class); // 启动Activity
						startActivity(intent);
					} else {
						debugDialog(result);
					}
					break;
				
				default:
					break;
				}
			}
		};
		
		/************** 添加菜单 ***************/

		CreatList menuList = new CreatList();
		List<Map<String, Object>> list = menuList.creatMenu();
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(this, list, R.layout.menu_list,
				new String[] { "icon", "menu" }, new int[] {
						R.id.imageView_icon, R.id.textView_menu });

		// 添加并且显示
		lvMenu.setAdapter(listItemAdapter);

		lvMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent;
				switch (arg2) {
				case 0:
					intent = new Intent(HistoryActivity.this,SubmitActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				case 1:
					checkIdDialog("");
					break;
				case 2:
					break;
				case 3:
					intent = new Intent(HistoryActivity.this,WebActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				case 4:
					intent = new Intent(HistoryActivity.this,HelpActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				case 5:
					intent = new Intent(HistoryActivity.this,
							AboutActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				}
			}
		});
		
		/************** 添加历史 ***************/

		CreatList historyList = new CreatList();
		List<Map<String, Object>> list2 = historyList.creatHistory();
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter2 = new SimpleAdapter(this, list2, R.layout.history_list,
				new String[] { "id", "status" }, new int[] {
						R.id.textView_id, R.id.textView_status });

		// 添加并且显示
		lvHistory.setAdapter(listItemAdapter2);
		
		lvHistory.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2!=0) {
					final TextView tvID = (TextView) arg1.findViewById(R.id.textView_id);
					checkIdDialog(tvID.getText().toString());
				}
			}
		});
		
	}
	
	/************************** 查询对话框 *************************/

	protected void checkIdDialog(String checkid) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View DialogView = null;
		DialogView = inflater.inflate(R.layout.checkid_dialog, null);
		AlertDialog.Builder builder = new Builder(HistoryActivity.this);
		builder.setView(DialogView);

		etCheck = (EditText) DialogView
				.findViewById(R.id.editText_checkid);
		
		btnCheck = (Button) DialogView
				.findViewById(R.id.button_check);

		etCheck.setText(checkid);
		
		btnCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (etCheck.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(),
							"Please fill in the ID",
							Toast.LENGTH_SHORT).show();
					return;
					}

				stopService();
				timeHandler.postDelayed(runnable1, timeInterval);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", etCheck.getText().toString()));
				myHttpThreadPost = new HttpThreadPost(SubmitActivity.URL + "getResult.php",
						params, HttpThreadPost.CHECKID, handler);
				myHttpThreadPost.start();
				pDialog.setMessage("Check Result...");
				pDialog.show();
			}
		});
		
		btnCheck.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.deep);
				} else {
					v.setBackgroundResource(R.drawable.shallow);
				}
				return false;
			}
		});
//		builder.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						alertDialog.dismiss();
//					}
//				});
		alertDialog = builder.create();
		alertDialog.show();
		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = (int) (SCREEN_WIDTH * 0.8);// 定义宽度
		lp.height = (int) (SCREEN_HEIGHT * 0.8);// 定义高度
		alertDialog.getWindow().setAttributes(lp);
	}
		
	/************************** 获取结果状态 *************************/

	public boolean getStatus(String json) {
		String strStatus = "";
		try {
			strStatus = new JSONObject(json).getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (strStatus.equals("0")) {
			return true;
		} else
			return false;
	}

	/************************** 获取错误信息 *************************/

	public String getErrorMessage(String json) {
		String errorMessage = null;
		try {
			errorMessage = new JSONObject(json).getString("message");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return errorMessage;
	}

	/************************** 保存参数信息 *************************/

	public void saveMessage(String json) {
		String saveSPECIE = null;
		String saveGENE = null;
		String saveLOCATION = null;
		String saveREGION = null;
		try {
			JSONObject jsonObj = new JSONObject(json);
			saveSPECIE = jsonObj.getString("specie");
			saveGENE = jsonObj.getString("gene");
			saveLOCATION = jsonObj.getString("location");
			saveREGION = jsonObj.getString("region");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		// 保存json与一些信息
		Context ctx = HistoryActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("BUFF", MODE_PRIVATE);
		Editor editor = info.edit();
		editor.putString("SPECIE", saveSPECIE);
		editor.putString("GENE", saveGENE);
		editor.putString("LOCATION", saveLOCATION);
		editor.putString("REGION", saveREGION);
		editor.putString("JSON", json);
		editor.commit();
	}
		
	/************************** debug显示框 *************************/

	protected void debugDialog(String message) {
		AlertDialog.Builder builder = new Builder(HistoryActivity.this);
		builder.setMessage(message);
		builder.setTitle("ErrorInfo");
		builder.setCancelable(true);
		builder.create().show();

	}
	
	@Override
	protected void onResume() {
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

	// 退出时，销毁dialog
		@Override
		protected void onDestroy() {
			super.onDestroy();
			if (null != pDialog) {
				pDialog.dismiss();
			}
			//stopService();
			// //销毁线程通信
			// myHttpThread.interrupt();
		}
		// 启动服务
		private void startService() {
			Intent intent = new Intent(this, BackGroundService.class);
			startService(intent);
		}
		// 关闭服务
		private void stopService() {
			Intent intent = new Intent(this, BackGroundService.class);
			stopService(intent);
		}
}
