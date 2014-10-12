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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitActivity extends Activity {

	// public static final String URL = "http://192.168.10.100/iGEM2014/";
	public static final String URL = "http://i.uestc.edu.cn/iGEM2014/";

	private EditText etTag; // tag编辑框
	// private EditText etSaveURL; // URL编辑框
	private EditText etUserName; // username编辑框
	private EditText etPassword; // password编辑框
	private Spinner spTargetGenome; // targetgenome下拉框
	private Spinner spPam; // pam下拉框
	private Spinner spChromosome; // Chromosome下拉框
	private EditText etPos1; // pos1编辑框
	private EditText etPos2; // pos2编辑框
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
	private RelativeLayout rlPam;
	private RelativeLayout rlPos;
	private RelativeLayout rlTag;

	private EditText etLogonUserName;
	private EditText etLogonPassword;
	private EditText etLogonPassword2;
	private EditText etLogonEmail;
	private Button btnLogonLogon;

	private CheckBox cb10, cb12, cb12a, cb21, cb23, cb25, cbEXON, cbINTRON,
			cbUTR, cbINTERGENIC; // RFC CheckBox

	private TextView tvWeight;
	private SeekBar sbWeight;

	private EditText etCheck; // check编辑框
	private Button btnCheck; // check按钮

	private String strType = "1";// 1=针对基因敲除，2=针对基因干扰，暂时默认1
	private String strTag = null;// Position LocusTag字串
	private String strTargetGenome = null;// TargetGenome字串
	private String strPam = null;// Pam字串
	private String strCount = "20";// Count字串
	private String strRFC = "111111";// RFC字串
	private String strRegion = "1111";// region字串
	private String strRFC10 = "1";// RFC字串
	private String strRFC12 = "1";// RFC字串
	private String strRFC12a = "1";// RFC字串
	private String strRFC21 = "1";// RFC字串
	private String strRFC23 = "1";// RFC字串
	private String strRFC25 = "1";// RFC字串
	private String strEXON = "1";// region字串
	private String strINTRON = "1";// region字串
	private String strUTR = "1";// region字串
	private String strINTERGENIC = "1";// region字串
	private long timeInterval = 1 * 60 * 1000; // 登录超时时间
	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	private boolean tpFalg = true;
	private boolean kiFalg = true;
	private boolean isLogin = false;
	private int weightR1 = 65;

	private String token = null;
	private String userName = null;
	private String userFile = null;

	HttpThreadGet myHttpThreadGet;
	HttpThreadPost myHttpThreadPost;
	static Handler handler;
	ProgressDialog pDialog;
	AlertDialog alertDialog;
	SimpleAdapter listItemAdapter;
	MD5 md5 = new MD5();
	SpeciesChromosome chromossomeList = new SpeciesChromosome();

	List<Dict> dict_tg = new ArrayList<Dict>();
	List<Dict> dict_chromosome = new ArrayList<Dict>();
	List<Dict> dict_pam = new ArrayList<Dict>();
	ArrayAdapter<Dict> adapter_pam, adapter_tg, adapter_chromosome;

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

		spChromosome = (Spinner) findViewById(R.id.spinner_chromosome);
		etTag = (EditText) findViewById(R.id.editText_Tag);
		// etSaveURL = (EditText) findViewById(R.id.editText_url);
		etUserName = (EditText) findViewById(R.id.editText_username);
		etPassword = (EditText) findViewById(R.id.editText_pswd);
		spTargetGenome = (Spinner) findViewById(R.id.spinner_targetgenome);
		etPos1 = (EditText) findViewById(R.id.editText_pos1);
		etPos2 = (EditText) findViewById(R.id.editText_pos2);
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
		rlPam = (RelativeLayout) findViewById(R.id.relativeLayout_pam);
		rlPos = (RelativeLayout) findViewById(R.id.relativeLayout_Pos);
		rlTag = (RelativeLayout) findViewById(R.id.relativeLayout_Tag);
		rlPos.setVisibility(View.GONE);
		rlPam.setVisibility(View.GONE);

		isLogin = new BackGroundService().getIsLogin();
		token = new BackGroundService().getToken();
		if (isLogin) {
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
					if (isLogin) {
						intent = new Intent(SubmitActivity.this,
								HistoryActivity.class); // 启动Activity
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"Please login !", Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					intent = new Intent(SubmitActivity.this, WebActivity.class); // 启动Activity
					startActivity(intent);
					finish();
					break;
				case 4:
					intent = new Intent(SubmitActivity.this, HelpActivity.class); // 启动Activity
					startActivity(intent);
					finish();
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
					// debugDialog(result);
					// System.out.println(result);
					break;
				case HttpThreadPost.LOGIN:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGIN");
					result = (String) msg.obj;
					// System.out.println(result);
					getLoginMessage(result);
					break;
				case HttpThreadPost.LOGON:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGON");
					result = (String) msg.obj;
					// System.out.println(result);
					getLogonMessage(result);
					break;
				case HttpThreadPost.LOGOUT:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入LOGOUT");
					result = (String) msg.obj;
					// System.out.println(result);
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
						Intent intent = new Intent(SubmitActivity.this,
								ResultActivity.class); // 启动Activity
						startActivity(intent);
					} else {
						debugDialog(result);
					}
					break;
				case HttpThreadPost.CHECKFILE:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入CHECKFILE");
					result = (String) msg.obj;
					userFile = result;
					reflashList(userFile);
					// debugDialog(result);
					// 添加用户物种文件
					// dict_tg = chromossomeList.UserSpecies(dict_tg, result);
					// adapter_tg = new ArrayAdapter<Dict>(SubmitActivity.this,
					// R.layout.my_spinner, dict_tg);
					// spTargetGenome.setAdapter(adapter_tg);
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
		dict_tg = chromossomeList.Species(dict_tg);
		adapter_tg = new ArrayAdapter<Dict>(this, R.layout.my_spinner, dict_tg);
		spTargetGenome.setAdapter(adapter_tg);

		spTargetGenome.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取键的方法：mySpinner.getSelectedItem().toString()或((Dict)mySpinner.getSelectedItem()).getId()
				// 获取值的方法：((Dict)mySpinner.getSelectedItem()).getText();

				if (position != 0) {
					if (isLogin) {
						dict_chromosome.clear();
						dict_chromosome = chromossomeList
								.paseUserChromosomesJson(userFile, position,
										dict_chromosome);
					} else {
						dict_chromosome.clear();
						switch (position) {
						case 1:
							dict_chromosome = chromossomeList
									.Ecoli_Chromosome(dict_chromosome);
							break;
						case 2:
							dict_chromosome = chromossomeList
									.Saccharomyces_Chromosome(dict_chromosome);
							break;
						case 3:
							dict_chromosome = chromossomeList
									.Drosophila_Chromosome(dict_chromosome);
							break;
						case 4:
							dict_chromosome = chromossomeList
									.Caenorhabditis_Chromosome(dict_chromosome);
							break;
						case 5:
							dict_chromosome = chromossomeList
									.Arabidopsis_Chromosome(dict_chromosome);
							break;
						default:
							break;
						}
					}
					adapter_chromosome = new ArrayAdapter<Dict>(
							SubmitActivity.this, R.layout.my_spinner,
							dict_chromosome);
					spChromosome.setAdapter(adapter_chromosome);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		/************************* Pam位点下拉框 **************************/

		dict_pam.add(new Dict("0", "PAM"));
		dict_pam.add(new Dict("NGG", "NGG"));
		dict_pam.add(new Dict("NNNNGMTT", "NNNNGMTT"));
		dict_pam.add(new Dict("NNAGAAW", "NNAGAAW"));
		dict_pam.add(new Dict("NRG", "NRG"));
		dict_pam.add(new Dict("NAAAAC", "NAAAAC"));
		dict_pam.add(new Dict("1", "other"));
		adapter_pam = new ArrayAdapter<Dict>(this, R.layout.my_spinner,
				dict_pam);
		spPam.setAdapter(adapter_pam);

		spPam.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取键的方法：mySpinner.getSelectedItem().toString()或((Dict)mySpinner.getSelectedItem()).getId()
				// 获取值的方法：((Dict)mySpinner.getSelectedItem()).getText();
				if (((Dict) spPam.getSelectedItem()).getKey().equals("1")) {
					rlPam.setVisibility(View.VISIBLE);
				} else {
					rlPam.setVisibility(View.GONE);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		/************************** 染色体选择下拉框 *************************/

		dict_chromosome = chromossomeList.Ecoli_Chromosome(dict_chromosome);
		adapter_chromosome = new ArrayAdapter<Dict>(this, R.layout.my_spinner,
				dict_chromosome);
		spChromosome.setAdapter(adapter_chromosome);

		spChromosome.setOnItemSelectedListener(new OnItemSelectedListener() {
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
				rlPos.setVisibility(View.VISIBLE);
				rlTag.setVisibility(View.GONE);
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
				rlPos.setVisibility(View.GONE);
				rlTag.setVisibility(View.VISIBLE);
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

				spTargetGenome.setSelection(2, true);
				spPam.setSelection(1, true);
				dict_chromosome.clear();
				dict_chromosome = chromossomeList
						.Saccharomyces_Chromosome(dict_chromosome);
				adapter_chromosome = new ArrayAdapter<Dict>(
						SubmitActivity.this, R.layout.my_spinner,
						dict_chromosome);
				spChromosome.setAdapter(adapter_chromosome);
				spChromosome.setSelection(1, true);
				etPos1.setText("200");
				etPos2.setText("2873");
				rlPos.setVisibility(View.VISIBLE);
				rlTag.setVisibility(View.GONE);
				tpFalg = false;
				if (tpFalg) {
					btnLocusTag.setBackgroundResource(R.drawable.deep);
					btnPosition.setBackgroundResource(R.drawable.shallow);
				} else {
					btnLocusTag.setBackgroundResource(R.drawable.shallow);
					btnPosition.setBackgroundResource(R.drawable.deep);
				}
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
				if(isLogin){
					Toast.makeText(getApplicationContext(),
							"Welcome "+userName,
							Toast.LENGTH_SHORT).show();
					return false;
				}
				else if (event.getAction() == MotionEvent.ACTION_DOWN) {
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

				strTag = etTag.getText().toString();
				strTargetGenome = ((Dict) spTargetGenome.getSelectedItem())
						.getKey();
				strPam = ((Dict) spPam.getSelectedItem()).getKey();

				// if (etTag.getText().toString().isEmpty() ||
				// strPam.equals("0")
				// || strTargetGenome.equals("0")) {
				// Toast.makeText(getApplicationContext(),
				// "Please fill in the information",
				// Toast.LENGTH_SHORT).show();
				// return;
				// }
				// ;
				timeHandler.postDelayed(runnable1, timeInterval);
				MainSubmitGet(URL + "getMain.php");
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

	private void MainSubmitGet(String URL) {
		System.out.println("进入MainSubmitPost");
		strRFC = strRFC10 + strRFC12 + strRFC12a + strRFC21 + strRFC21
				+ strRFC23 + strRFC25;
		strRegion = strEXON + strINTRON + strUTR + strINTERGENIC;
		// 添加参数
		String strRequest = "?specie=" + strTargetGenome + "&pam=" + strPam
				+ "&length=" + strCount + "&region=" + strRegion + "&type="
				+ strType + "&rfc=" + strRFC + "&r1="
				+ String.valueOf((double) weightR1 / 100.0);
		if (tpFalg) {
			strRequest = strRequest + "&gene=" + strTag;
		} else {
			strRequest = strRequest + "&location="
					+ ((Dict) spChromosome.getSelectedItem()).getKey() + ":"
					+ etPos1.getText().toString() + ".."
					+ etPos2.getText().toString();
		}
		if (isLogin) {
			strRequest = strRequest + "&token=" + token;
		}
		URL = URL + strRequest;
		Log.d("Xdebug", URL);
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

				if (etLogonUserName.getText().toString().isEmpty()) {
					etLogonUserName.setError("Fill a user name");
					return;
				}
				if (etLogonPassword.getText().toString().isEmpty()) {
					etLogonPassword.setError("Fill a password");
					return;
				}
				if (etLogonPassword2.getText().toString().isEmpty()) {
					etLogonPassword2.setError("Fill again the password");
					return;
				}
				if (etLogonEmail.getText().toString().isEmpty()) {
					etLogonEmail.setError("Fill your Email address");
					return;
				}
				if (!(etLogonPassword.getText().toString()
						.equals(etLogonPassword2.getText().toString()))) {
					etLogonPassword2.setError("Please unified the password");
					return;
				} else {
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
					myHttpThreadPost = new HttpThreadPost(URL
							+ "login/signup.php", params, HttpThreadPost.LOGON,
							handler);
					myHttpThreadPost.start();
					pDialog.setMessage("LogOn...");
					pDialog.show();
				}
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
		// builder.setNegativeButton("Cancel",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// alertDialog.dismiss();
		// }
		// });
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
		cbEXON = (CheckBox) DialogView.findViewById(R.id.checkBox_EXON);
		cbINTRON = (CheckBox) DialogView.findViewById(R.id.checkBox_INTRON);
		cbUTR = (CheckBox) DialogView.findViewById(R.id.checkBox_UTR);
		cbINTERGENIC = (CheckBox) DialogView
				.findViewById(R.id.checkBox_INTERGENIC);
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
		if (strUTR.equals("1")) {
			cbUTR.setChecked(true);
		} else {
			cbUTR.setChecked(false);
		}
		if (strEXON.equals("1")) {
			cbEXON.setChecked(true);
		} else {
			cbEXON.setChecked(false);
		}
		if (strINTERGENIC.equals("1")) {
			cbINTERGENIC.setChecked(true);
		} else {
			cbINTERGENIC.setChecked(false);
		}
		if (strINTRON.equals("1")) {
			cbINTRON.setChecked(true);
		} else {
			cbINTRON.setChecked(false);
		}

		if (strCount.equals("17")) {
			group.check(R.id.radio_17);
		}
		if (strCount.equals("18")) {
			group.check(R.id.radio_18);
		}
		if (strCount.equals("19")) {
			group.check(R.id.radio_19);
		}
		if (strCount.equals("20")) {
			group.check(R.id.radio_20);
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
		cbEXON.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cbEXON.isChecked()) {
					strEXON = "1";
				} else {
					strEXON = "0";
				}
			}
		});
		cbINTRON.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cbINTRON.isChecked()) {
					strINTRON = "1";
				} else {
					strINTRON = "0";
				}
			}
		});
		cbUTR.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cbUTR.isChecked()) {
					strUTR = "1";
				} else {
					strUTR = "0";
				}
			}
		});
		cbINTERGENIC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cbINTERGENIC.isChecked()) {
					strINTERGENIC = "1";
				} else {
					strINTERGENIC = "0";
				}
			}
		});

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {

				switch (arg1) {
				case R.id.radio_17:
					strCount = "17";
					break;
				case R.id.radio_18:
					strCount = "18";
					break;
				case R.id.radio_19:
					strCount = "19";
					break;
				case R.id.radio_20:
					strCount = "20";
					break;
				default:
					break;
				}
			}

		});

		// builder.setNegativeButton("Cancel",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// alertDialog.dismiss();
		// }
		// });

		alertDialog = builder.create();
		alertDialog.show();
		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = (int) (SCREEN_WIDTH * 0.9);// 定义宽度
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

		etCheck = (EditText) DialogView.findViewById(R.id.editText_checkid);

		btnCheck = (Button) DialogView.findViewById(R.id.button_check);

		etCheck.setText(checkid);

		btnCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (etCheck.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(),
							"Please fill in the ID", Toast.LENGTH_SHORT).show();
					return;
				}
				stopService();

				timeHandler.postDelayed(runnable1, timeInterval);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", etCheck.getText()
						.toString()));
				myHttpThreadPost = new HttpThreadPost(SubmitActivity.URL
						+ "getResult.php", params, HttpThreadPost.CHECKID,
						handler);
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
		// builder.setNegativeButton("Cancel",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// alertDialog.dismiss();
		// }
		// });
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
		String saveCG = null;
		try {
			JSONObject jsonObj = new JSONObject(json);
			saveSPECIE = jsonObj.getString("specie");
			saveGENE = jsonObj.getString("gene");
			saveLOCATION = jsonObj.getString("location");
			saveREGION = jsonObj.getString("region");
			saveCG = jsonObj.getString("GC");
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
		editor.putString("CG", saveCG);
		editor.putString("JSON", json);
		editor.commit();
	}

	/************************** 更新物种信息 *************************/

	private void reflashList(String json) {
		dict_tg.clear();
		dict_tg = chromossomeList.paseUserSpeciesJson(json, dict_tg);
		adapter_tg = new ArrayAdapter<Dict>(this, R.layout.my_spinner, dict_tg);
		spTargetGenome.setAdapter(adapter_tg);
	}

	/************************** 获取登录信息 *************************/

	private void getLoginMessage(String message) {
		if (message.trim().equals("-")) {
			Toast.makeText(getApplicationContext(), "Login error",
					Toast.LENGTH_SHORT).show();
		} else if (message.trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Can not login",
					Toast.LENGTH_SHORT).show();
		} else {
			token = message; // 获取密钥
			Toast.makeText(getApplicationContext(), "Login succeed",
					Toast.LENGTH_SHORT).show();
			etUserName.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
			btnLogin.setBackgroundResource(R.drawable.vip);
			btnLogin.setText("");
			btnLogon.setText("Logout");
			isLogin = true;
			new BackGroundService(userName, token, true);
			startService();
			// 查询用户文件
			timeHandler.postDelayed(runnable1, timeInterval);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", token));
			// myHttpThreadPost = new HttpThreadPost(SubmitActivity.URL
			// + "upload/viewmyfiles.php", params, HttpThreadPost.CHECKFILE,
			// handler);

			myHttpThreadPost = new HttpThreadPost(SubmitActivity.URL
					+ "upload/viewmyspecies.php", params,
					HttpThreadPost.CHECKFILE, handler);
			myHttpThreadPost.start();
			pDialog.setMessage("Check Files...");
			pDialog.show();
		}
	}

	/************************** 获取退出登录信息 *************************/

	private void getLogoutMessage(String message) {
		if (message.equals("")) {
			Toast.makeText(getApplicationContext(), "Logout succeed",
					Toast.LENGTH_SHORT).show();
			etUserName.setVisibility(View.VISIBLE);
			etPassword.setVisibility(View.VISIBLE);
			btnLogin.setBackgroundResource(R.drawable.shallow);
			btnLogin.setText("Login");
			btnLogon.setText("Logon");
			isLogin = false;
			new BackGroundService(userName, token, false);
			stopService();
			dict_tg.clear();
			dict_tg = chromossomeList.Species(dict_tg);
			adapter_tg = new ArrayAdapter<Dict>(this, R.layout.my_spinner,
					dict_tg);
			spTargetGenome.setAdapter(adapter_tg);
		}
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

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
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
		// stopService();
		// //销毁线程通信
		// myHttpThread.interrupt();
	}

}
