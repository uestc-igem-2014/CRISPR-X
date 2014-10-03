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
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitActivity extends Activity {

	public static final String URL = "http://192.168.10.100/iGEM2014/";
	//public static final String URL = "http://immunet.cn/iGEM2014/";

	private EditText etPosTag; // postag编辑框
	// private EditText etSaveURL; // URL编辑框
	private EditText etUserName; // username编辑框
	private EditText etPassword; // password编辑框
	private Spinner spTargetGenome; // targetgenome下拉框
	private Spinner spPam; // pam下拉框
	private Button btnPosition; // position按钮
	private Button btnLocusTag; // locus tag按钮
	private Button btnDefault; // default按钮
	private Button btnAdvanced; // advance按钮
	private Button btnKnockout; // Knockout按钮
	private Button btnInterference; // Interference按钮
	private Button btnLogin; // login按钮
	private Button btnLogon; // logon按钮
	private Button btnSubmit; // submit按钮
	private ListView lvMenu;

	private EditText etLogonUserName;
	private EditText etLogonPassword;
	private EditText etLogonPassword2;
	private EditText etLogonEmail;
	private Button btnLogonLogon;

	private CheckBox cb10, cb12, cb12a, cb21, cb23, cb25; // RFC CheckBox
	private TextView tvWeight;
	private SeekBar sbWeight;
	
	private EditText etCheck; // check编辑框
	private Button btnCheck; // check按钮

	private String strType = "1";// 1=针对基因敲除，2=针对基因干扰，暂时默认1
	private String strPosTag = null;// Position LocusTag字串
	private String strTargetGenome = null;// TargetGenome字串
	private String strPam = null;// Pam字串
	private String strCount = "20";// Count字串
	private String strRFC = "111111";// RFC字串
	private String strRFC10 = "1";// RFC字串
	private String strRFC12 = "1";// RFC字串
	private String strRFC12a = "1";// RFC字串
	private String strRFC21 = "1";// RFC字串
	private String strRFC23 = "1";// RFC字串
	private String strRFC25 = "1";// RFC字串
	private long timeInterval = 2 * 60 * 1000; // 登录超时时间
	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	private boolean tpFalg = true;
	private boolean kiFalg = true;
	private boolean isLogin = false;
	private int weightR1 = 65;

	private String token = null;
	private String userName = null;
	

	HttpThreadGet myHttpThreadGet;
	HttpThreadPost myHttpThreadPost;
	static Handler handler;
	ProgressDialog pDialog;
	AlertDialog alertDialog;
	SimpleAdapter listItemAdapter;
	MD5 md5 = new MD5();

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

		setContentView(R.layout.activity_submit);

		etPosTag = (EditText) findViewById(R.id.editText_postag);
		// etSaveURL = (EditText) findViewById(R.id.editText_url);
		etUserName = (EditText) findViewById(R.id.editText_username);
		etPassword = (EditText) findViewById(R.id.editText_pswd);
		spTargetGenome = (Spinner) findViewById(R.id.spinner_targetgenome);
		spPam = (Spinner) findViewById(R.id.spinner_pam);
		btnLocusTag = (Button) findViewById(R.id.button_locustag);
		btnPosition = (Button) findViewById(R.id.button_position);
		btnDefault = (Button) findViewById(R.id.button_default);
		btnAdvanced = (Button) findViewById(R.id.button_advanced);
		// btnHelp = (Button) findViewById(R.id.button_help);
		// btnSaveURL = (Button) findViewById(R.id.button_save);
		btnLogin = (Button) findViewById(R.id.button_login);
		btnLogon = (Button) findViewById(R.id.button_logon);
		btnSubmit = (Button) findViewById(R.id.button_submit);
		btnKnockout = (Button) findViewById(R.id.button_knockout);
		btnInterference = (Button) findViewById(R.id.button_interference);
		// llLogin = (LinearLayout) findViewById(R.id.linearLayout_login);
		lvMenu = (ListView) findViewById(R.id.listView_menu);

		isLogin = new BackGroundService().getIsLogin();
		token = new BackGroundService().getToken();
		if(isLogin){
			startService();
			userName = new BackGroundService().getUserName();
			etUserName.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
			btnLogin.setBackgroundResource(R.drawable.ic_launcher);
			btnLogin.setText("");
			btnLogon.setText("Logout");
		} else {
			stopService();
		}
		CreatList menuList = new CreatList();
		List<Map<String, Object>> list = menuList.creatMenu();
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(this, list, R.layout.menu_list,
				new String[] { "icon", "menu" }, new int[] {
						R.id.imageView_icon, R.id.textView_menu });

		// 添加并且显示
		lvMenu.setAdapter(listItemAdapter);

		// 添加点击
		lvMenu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent;
				switch (arg2) {
				case 0:
					
					break;
				case 1:
					checkIdDialog("");
					break;
				case 2:
					if(isLogin){
						intent = new Intent(SubmitActivity.this,HistoryActivity.class); // 启动Activity
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "Please login !",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					intent = new Intent(SubmitActivity.this,WebActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				case 4:
					break;
				case 5:
					intent = new Intent(SubmitActivity.this,
							AboutActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				}
			}
		});

		if (tpFalg) {
			btnLocusTag.setBackgroundResource(R.drawable.deep);
			btnPosition.setBackgroundResource(R.drawable.shallow);
		} else {
			btnLocusTag.setBackgroundResource(R.drawable.shallow);
			btnPosition.setBackgroundResource(R.drawable.deep);
		}

		if (kiFalg) {
			btnKnockout.setBackgroundResource(R.drawable.deep);
			btnInterference.setBackgroundResource(R.drawable.shallow);
		} else {
			btnKnockout.setBackgroundResource(R.drawable.shallow);
			btnInterference.setBackgroundResource(R.drawable.deep);
		}

		/************************** msg接收 *************************/

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String result = null;
				switch (msg.what) {
				case HttpThreadGet.MAIN_SUBMIT:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入MAIN_SUBMIT");
					result = (String) msg.obj;
					checkIdDialog(result);
					startService();
					//debugDialog(result);
					//System.out.println(result);
					break;
				case HttpThreadPost.LOGIN:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGIN");
					result = (String) msg.obj;
					//System.out.println(result);
					getLoginMessage(result);
					break;
				case HttpThreadPost.LOGON:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGON");
					result = (String) msg.obj;
					//System.out.println(result);
					getLogonMessage(result);
					break;
				case HttpThreadPost.LOGOUT:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGOUT");
					result = (String) msg.obj;
					//System.out.println(result);
					getLogoutMessage(result);
					break;
				case HttpThreadPost.CHECKID:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入CHECKID");
					result = (String) msg.obj;
					startService();

					if (getStatus(result)) {
						saveMessage(result);
						Intent intent = new Intent(SubmitActivity.this,ResultActivity.class); // 启动Activity
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

		/************************** 界面增加进度显示框 *************************/

		pDialog = new ProgressDialog(this);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);

		/************************** 物种选择下拉框 *************************/

		List<Dict> dict_tg = new ArrayList<Dict>();
		dict_tg.add(new Dict("0", "Target Genome"));
		dict_tg.add(new Dict("E.coli", "E.coli"));
		dict_tg.add(new Dict("Saccharomyces-cerevisiae", "Saccharomyces"));
		ArrayAdapter<Dict> adapter_tg = new ArrayAdapter<Dict>(this,
				R.layout.my_spinner, dict_tg);
		spTargetGenome.setAdapter(adapter_tg);

		spTargetGenome.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取键的方法：mySpinner.getSelectedItem().toString()或((Dict)mySpinner.getSelectedItem()).getId()
				// 获取值的方法：((Dict)mySpinner.getSelectedItem()).getText();
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		/************************* Pam位点下拉框 **************************/

		List<Dict> dict_pam = new ArrayList<Dict>();
		dict_pam.add(new Dict("0", "PAM"));
		dict_pam.add(new Dict("NGG", "NGG"));
		dict_pam.add(new Dict("NNNNGMTT", "NNNNGMTT"));
		dict_pam.add(new Dict("NNAGAAW", "NNAGAAW"));
		dict_pam.add(new Dict("NRG", "NRG"));
		dict_pam.add(new Dict("NAAAAC", "NAAAAC"));
		ArrayAdapter<Dict> adapter_pam = new ArrayAdapter<Dict>(this,
				R.layout.my_spinner, dict_pam);
		spPam.setAdapter(adapter_pam);

		spPam.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取键的方法：mySpinner.getSelectedItem().toString()或((Dict)mySpinner.getSelectedItem()).getId()
				// 获取值的方法：((Dict)mySpinner.getSelectedItem()).getText();
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		/************************* Interference按钮 **************************/

		btnInterference.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				kiFalg = false;
				strType = "2";
				if (kiFalg) {
					btnKnockout.setBackgroundResource(R.drawable.deep);
					btnInterference.setBackgroundResource(R.drawable.shallow);
				} else {
					btnKnockout.setBackgroundResource(R.drawable.shallow);
					btnInterference.setBackgroundResource(R.drawable.deep);
				}
			}
		});

		/************************* Knockout按钮 **************************/

		btnKnockout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				kiFalg = true;
				strType = "1";
				if (kiFalg) {
					btnKnockout.setBackgroundResource(R.drawable.deep);
					btnInterference.setBackgroundResource(R.drawable.shallow);
				} else {
					btnKnockout.setBackgroundResource(R.drawable.shallow);
					btnInterference.setBackgroundResource(R.drawable.deep);
				}
			}
		});

		/************************* position按钮 **************************/

		btnPosition.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tpFalg = false;
				if (tpFalg) {
					btnLocusTag.setBackgroundResource(R.drawable.deep);
					btnPosition.setBackgroundResource(R.drawable.shallow);
				} else {
					btnLocusTag.setBackgroundResource(R.drawable.shallow);
					btnPosition.setBackgroundResource(R.drawable.deep);
				}
				etPosTag.setText("");
				etPosTag.setHint("eg:1:200..3566");
			}
		});

		/************************* tag按钮 **************************/

		btnLocusTag.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tpFalg = true;
				if (tpFalg) {
					btnLocusTag.setBackgroundResource(R.drawable.deep);
					btnPosition.setBackgroundResource(R.drawable.shallow);
				} else {
					btnLocusTag.setBackgroundResource(R.drawable.shallow);
					btnPosition.setBackgroundResource(R.drawable.deep);
				}
				etPosTag.setText("");
				etPosTag.setHint("eg:thrA");
			}
		});

		/************************* 高级选项按钮 **************************/

		btnAdvanced.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				advancedDialog();
			}
		});

		btnAdvanced.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.deep);
					// advancedDialog();
				} else {
					v.setBackgroundResource(R.drawable.shallow);
				}
				return false;
			}
		});

		// /************************* 帮助信息按钮 **************************/
		//
		// btnHelp.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		//
		//
		// }
		// });
		// btnHelp.setOnTouchListener(new Button.OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// v.setBackgroundResource(R.drawable.help1);
		// // advancedDialog();
		// } else {
		// v.setBackgroundResource(R.drawable.help);
		// }
		// return false;
		// }
		// });

		/************************* 填写默认信息按钮 **************************/

		btnDefault.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				etPosTag.setText("NC_001134-chromosome2:200..2873");
				spTargetGenome.setSelection(2, true);
				spPam.setSelection(1, true);
				tpFalg = false;
				if (tpFalg) {
					btnLocusTag.setBackgroundResource(R.drawable.deep);
					btnPosition.setBackgroundResource(R.drawable.shallow);
				} else {
					btnLocusTag.setBackgroundResource(R.drawable.shallow);
					btnPosition.setBackgroundResource(R.drawable.deep);
				}
				etPosTag.setHint("NC_001134-chromosome2:200..2873");
			}
		});

		btnDefault.setOnTouchListener(new Button.OnTouchListener() {
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

		// /************************* 保存URL按钮 **************************/
		//
		// btnSaveURL.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		//
		// Context ctx = SubmitActivity.this;
		// SharedPreferences info = ctx.getSharedPreferences("INFO",
		// MODE_PRIVATE);
		// Editor editor = info.edit();
		// editor.putString("URL", etSaveURL.getText().toString());
		// editor.commit();
		// URL = info.getString("URL", "null"); // 更新
		// Toast.makeText(SubmitActivity.this, "保存成功 IP:" + URL,
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// });

		/************************* 提交登陆按钮 **************************/

		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (isLogin) {

				} else {
					if (etUserName.getText().toString().isEmpty()
							|| etPassword.getText().toString().isEmpty()) {
						Toast.makeText(getApplicationContext(),
								"Please fill in the user information",
								Toast.LENGTH_SHORT).show();
						return;
					}
					;

					String pswd = null;
					pswd = MD5.getMd5Value(etPassword.getText().toString()
							.trim());
					userName = etUserName.getText().toString();
					timeHandler.postDelayed(runnable1, timeInterval);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("name", userName));
					params.add(new BasicNameValuePair("pswd", pswd));

					System.out.println(etUserName.getText().toString());
					System.out.println(pswd);

					myHttpThreadPost = new HttpThreadPost(URL + "login/",
							params, HttpThreadPost.LOGIN, handler);
					myHttpThreadPost.start();
					pDialog.setMessage("Login...");
					pDialog.show();
				}
			}
		});

		btnLogin.setOnTouchListener(new Button.OnTouchListener() {
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

		/************************* 提交注册按钮 **************************/

		btnLogon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (isLogin) {
					timeHandler.postDelayed(runnable1, timeInterval);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("token", token));
					System.out.println(token);

					myHttpThreadPost = new HttpThreadPost(URL
							+ "login/logout.php", params,
							HttpThreadPost.LOGOUT, handler);
					myHttpThreadPost.start();
					pDialog.setMessage("Logout...");
					pDialog.show();
				} else {
					logonDialog();
				}

			}
		});

		btnLogon.setOnTouchListener(new Button.OnTouchListener() {
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

		/************************* 提交主程序按钮 **************************/

		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				strPosTag = etPosTag.getText().toString();
				strTargetGenome = ((Dict) spTargetGenome.getSelectedItem())
						.getKey();
				strPam = ((Dict) spPam.getSelectedItem()).getKey();

				if (etPosTag.getText().toString().isEmpty()
						|| strPam.equals("0") || strTargetGenome.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"Please fill in the information",
							Toast.LENGTH_SHORT).show();
					return;
				}
				;
				timeHandler.postDelayed(runnable1, timeInterval);
				MainSubmitPost(URL + "getMain.php");
			}
		});

		btnSubmit.setOnTouchListener(new Button.OnTouchListener() {
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

	}

	/*********************** 主程序提交请求GET **********************/

	private void MainSubmitPost(String URL) {
		System.out.println("进入MainSubmitPost");
		// 添加参数
		String strRequest = "?specie=" + strTargetGenome + "&pam=" + strPam
				+ "&type=" + strType + "&rfc=" + strRFC + "&r1="
				+ String.valueOf((double) weightR1 / 100.0);
		if (tpFalg) {
			strRequest = strRequest + "&gene=" + strPosTag;
		} else {
			strRequest = strRequest + "&location=" + strPosTag;
		}
		if (isLogin) {
			strRequest = strRequest + "&token=" + token;
		}
		URL = URL + strRequest;
		myHttpThreadGet = new HttpThreadGet(URL, HttpThreadGet.MAIN_SUBMIT,
				handler);
		myHttpThreadGet.start();
		pDialog.setMessage("Program is running...");
		pDialog.show();
	}

	/**************** 定时器 ****************/

	Handler timeHandler = new Handler();

	Runnable runnable1 = new Runnable() {
		@Override
		public void run() {
			pDialog.dismiss();
			Toast.makeText(SubmitActivity.this, "Time out", Toast.LENGTH_SHORT)
					.show();
		}
	};

	/************************** debug显示框 *************************/

	protected void debugDialog(String message) {
		AlertDialog.Builder builder = new Builder(SubmitActivity.this);
		builder.setMessage(message);
		builder.setTitle("ErrorInfo");
		builder.setCancelable(true);
		builder.create().show();

	}

	/************************** 注册对话框 *************************/

	protected void logonDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View DialogView = null;
		DialogView = inflater.inflate(R.layout.logon_dialog, null);
		AlertDialog.Builder builder = new Builder(SubmitActivity.this);
		builder.setView(DialogView);

		etLogonUserName = (EditText) DialogView
				.findViewById(R.id.editText_logon_username);
		etLogonPassword = (EditText) DialogView
				.findViewById(R.id.editText_logon_password);
		etLogonPassword2 = (EditText) DialogView
				.findViewById(R.id.editText_logon_password2);
		etLogonEmail = (EditText) DialogView
				.findViewById(R.id.editText_logon_email);
		btnLogonLogon = (Button) DialogView
				.findViewById(R.id.button_logon_logon);

		btnLogonLogon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (etLogonUserName.getText().toString().isEmpty()
						|| etLogonPassword.getText().toString().isEmpty()
						|| etLogonPassword2.getText().toString().isEmpty()
						|| etLogonEmail.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(),
							"Please fill in the user information",
							Toast.LENGTH_SHORT).show();
					if (!(etLogonPassword.getText().toString()
							.equals(etLogonPassword2.getText().toString()))) {
						Toast.makeText(getApplicationContext(),
								"Please unified the password",
								Toast.LENGTH_SHORT).show();
					}
					return;
				}
				;

				String pswd = null;
				pswd = MD5.getMd5Value(etLogonPassword.getText().toString()
						.trim());
				timeHandler.postDelayed(runnable1, timeInterval);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", etLogonUserName
						.getText().toString().trim()));
				params.add(new BasicNameValuePair("pswd", pswd));
				params.add(new BasicNameValuePair("email", etLogonEmail
						.getText().toString().trim()));
				System.out.println(pswd);
				myHttpThreadPost = new HttpThreadPost(URL + "login/signup.php",
						params, HttpThreadPost.LOGON, handler);
				myHttpThreadPost.start();
				pDialog.setMessage("LogOn...");
				pDialog.show();
			}

		});
		btnLogonLogon.setOnTouchListener(new Button.OnTouchListener() {
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
		lp.width = (int) (SCREEN_WIDTH * 0.5);// 定义宽度
		lp.height = (int) (SCREEN_HEIGHT * 0.9);// 定义高度
		alertDialog.getWindow().setAttributes(lp);
	}

	/************************** 高级选项框 *************************/

	protected void advancedDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View DialogView = null;
		DialogView = inflater.inflate(R.layout.advanced_dialog, null);
		AlertDialog.Builder builder = new Builder(SubmitActivity.this);
		builder.setTitle("Advanced").setView(DialogView);

		cb10 = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc10);
		cb12 = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc12);
		cb12a = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc12a);
		cb21 = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc21);
		cb23 = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc23);
		cb25 = (CheckBox) DialogView.findViewById(R.id.checkBox_rfc25);
		RadioGroup group = (RadioGroup) DialogView
				.findViewById(R.id.radioGroup1);
		tvWeight = (TextView) DialogView.findViewById(R.id.textView_weightT);
		sbWeight = (SeekBar) DialogView.findViewById(R.id.seekBar_weight);

		sbWeight.setMax(100);
		tvWeight.setText("Specificity " + String.valueOf(weightR1) + "  Active"
				+ String.valueOf(100 - weightR1));
		sbWeight.setProgress(weightR1);

		sbWeight.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
		{
			public void onProgressChanged(SeekBar arg0, int progress,
					boolean fromUser) {
				weightR1 = sbWeight.getProgress();
				tvWeight.setText("Specificity " + String.valueOf(weightR1)
						+ "  Active" + String.valueOf(100 - weightR1));
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {

			}
		});

		if (strRFC10.equals("1")) {
			cb10.setChecked(true);
		} else {
			cb10.setChecked(false);
		}
		if (strRFC12.equals("1")) {
			cb12.setChecked(true);
		} else {
			cb12.setChecked(false);
		}
		if (strRFC12a.equals("1")) {
			cb12a.setChecked(true);
		} else {
			cb12a.setChecked(false);
		}
		if (strRFC21.equals("1")) {
			cb21.setChecked(true);
		} else {
			cb21.setChecked(false);
		}
		if (strRFC23.equals("1")) {
			cb23.setChecked(true);
		} else {
			cb23.setChecked(false);
		}
		if (strRFC25.equals("1")) {
			cb25.setChecked(true);
		} else {
			cb25.setChecked(false);
		}

		if (strCount.equals("20")) {
			group.check(R.id.radio_20);
		}
		if (strCount.equals("30")) {
			group.check(R.id.radio_30);
		}
		if (strCount.equals("50")) {
			group.check(R.id.radio_50);
		}
		if (strCount.equals("100")) {
			group.check(R.id.radio_100);
		}

		cb10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (cb10.isChecked()) {
					strRFC10 = "1";
				} else {
					strRFC10 = "0";
				}
			}

		});
		cb12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (cb12.isChecked()) {
					strRFC12 = "1";
				} else {
					strRFC12 = "0";
				}
			}

		});
		cb12a.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (cb12a.isChecked()) {
					strRFC12a = "1";
				} else {
					strRFC12a = "0";
				}
			}

		});
		cb21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (cb21.isChecked()) {
					strRFC21 = "1";
				} else {
					strRFC21 = "0";
				}
			}

		});
		cb23.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (cb23.isChecked()) {
					strRFC23 = "1";
				} else {
					strRFC23 = "0";
				}
			}

		});
		cb25.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (cb25.isChecked()) {
					strRFC25 = "1";
				} else {
					strRFC25 = "0";
				}
			}

		});

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {

				switch (arg1) {
				case R.id.radio_20:
					strCount = "20";
					break;
				case R.id.radio_30:
					strCount = "30";
					break;
				case R.id.radio_50:
					strCount = "50";
					break;
				case R.id.radio_100:
					strCount = "100";
					break;
				default:
					break;
				}
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
		lp.width = (int) (SCREEN_WIDTH * 0.5);// 定义宽度
		lp.height = (int) (SCREEN_HEIGHT * 0.9);// 定义高度
		alertDialog.getWindow().setAttributes(lp);

	}

	/************************** 查询对话框 *************************/

	protected void checkIdDialog(String checkid) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View DialogView = null;
		DialogView = inflater.inflate(R.layout.checkid_dialog, null);
		AlertDialog.Builder builder = new Builder(SubmitActivity.this);
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
		Context ctx = SubmitActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("BUFF", MODE_PRIVATE);
		Editor editor = info.edit();
		editor.putString("SPECIE", saveSPECIE);
		editor.putString("GENE", saveGENE);
		editor.putString("LOCATION", saveLOCATION);
		editor.putString("REGION", saveREGION);
		editor.putString("JSON", json);
		editor.commit();
	}

	/************************** 获取登录信息 *************************/

	private void getLoginMessage(String message) {
		if (message.trim().equals("-")) {
			Toast.makeText(getApplicationContext(), "Login error",
					Toast.LENGTH_SHORT).show();
		} else {
			token = message; // 获取密钥
			Toast.makeText(getApplicationContext(), "Login succeed",
					Toast.LENGTH_SHORT).show();
			etUserName.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
			btnLogin.setBackgroundResource(R.drawable.ic_launcher);
			btnLogin.setText("");
			btnLogon.setText("Logout");
			isLogin = true;
			new BackGroundService(userName, token, true);
			startService();
		}
	}

	/************************** 获取退出登录信息 *************************/

	private void getLogoutMessage(String message) {

		Toast.makeText(getApplicationContext(), "Logout succeed",
				Toast.LENGTH_SHORT).show();
		etUserName.setVisibility(View.VISIBLE);
		etPassword.setVisibility(View.VISIBLE);
		btnLogin.setBackgroundResource(R.drawable.shallow);
		btnLogin.setText("Login");
		btnLogon.setText("Logon");
		isLogin = false;
		new BackGroundService(userName,token, false);
		stopService();
	}

	/************************** 获取注册信息 *************************/

	private void getLogonMessage(String message) {
		if (message.equals("Sign In Succeed")) {
			Toast.makeText(getApplicationContext(), "Logon succeed",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Logon fail:" + message,
					Toast.LENGTH_SHORT).show();
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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

}
