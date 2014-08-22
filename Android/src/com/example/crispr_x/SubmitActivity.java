package com.example.crispr_x;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitActivity extends Activity {

	private EditText etPosTag; // postag编辑框
	private EditText etSaveURL; // URL编辑框
	private Spinner spTargetGenome; // targetgenome下拉框
	private Spinner spPam; // pam下拉框
	private Button btnPosition; // position按钮
	private Button btnLocusTag; // locus tag按钮
	private Button btnDefault; // default按钮
	private Button btnAdvanced; // advance按钮
	private Button btnHelp; //help按钮
	private Button btnSubmit; // submit按钮
	private Button btnSaveURL; // URL按钮

	private CheckBox cb10, cb12, cb12a, cb21, cb23, cb25; // RFC CheckBox
	private RadioGroup group;
	private TextView tvWeight;
	private SeekBar sbWeight;

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

	private static String URL = "immunet.cn/iGEM2014/getMain.php";
	private long timeInterval = 2 * 60 * 1000; // 登录超时时间
	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	private boolean tpFalg = true;
	private boolean isStatus = false;
	private int weightR1 = 65;

	HttpThread myHttpThread;
	static Handler handler;
	ProgressDialog pDialog;
	AlertDialog alertDialog;

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
		etSaveURL = (EditText) findViewById(R.id.editText_url);
		spTargetGenome = (Spinner) findViewById(R.id.spinner_targetgenome);
		spPam = (Spinner) findViewById(R.id.spinner_pam);
		btnLocusTag = (Button) findViewById(R.id.button_locustag);
		btnPosition = (Button) findViewById(R.id.button_position);
		btnDefault = (Button) findViewById(R.id.button_default);
		btnAdvanced = (Button) findViewById(R.id.button_advanced);
		btnHelp = (Button) findViewById(R.id.button_help);
		btnSaveURL = (Button) findViewById(R.id.button_save);
		btnSubmit = (Button) findViewById(R.id.button_submit);

		Context ctx = SubmitActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("INFO", MODE_PRIVATE);
		URL = info.getString("URL", "http://immunet.cn/iGEM2014/getMain.php"); // 更新
		etSaveURL.setText(URL);

		if (tpFalg) {
			btnLocusTag.setBackgroundResource(R.drawable.deep);
			btnPosition.setBackgroundResource(R.drawable.shallow);
		} else {
			btnLocusTag.setBackgroundResource(R.drawable.shallow);
			btnPosition.setBackgroundResource(R.drawable.deep);
		}

		/************************** msg接收 *************************/

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case HttpThread.MAIN_SUBMIT:
					pDialog.dismiss();
					timeHandler.removeCallbacks(runnable1);
					System.out.println("进入hanlder");
					String result = (String) msg.obj;

					if (getStatus(result)) {
						saveMessage(result);
						Intent intent = new Intent(SubmitActivity.this,
								ResultActivity.class); // 启动Activity
						startActivity(intent);
					} else {
						debugDialog(getErrorMessage(result));
					}


					// debugDialog(result);
					System.out.println(result);
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
		dict_tg.add(new Dict("Saccharomyces", "Saccharomyces"));
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

		/************************* position按钮 **************************/

		btnPosition.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

				advancedDialog();
			}
		});

		btnAdvanced.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.ad1);
					// advancedDialog();
				} else {
					v.setBackgroundResource(R.drawable.adv);
				}
				return false;
			}
		});
		
		/************************* 帮助信息按钮 **************************/
		
		btnHelp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		btnHelp.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.help1);
					// advancedDialog();
				} else {
					v.setBackgroundResource(R.drawable.help);
				}
				return false;
			}
		});
		
		/************************* 填写默认信息按钮 **************************/

		btnDefault.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etPosTag.setText("1:336..2798");
				spTargetGenome.setSelection(1, true);
				spPam.setSelection(1, true);
				tpFalg = false;
				if (tpFalg) {
					btnLocusTag.setBackgroundResource(R.drawable.deep);
					btnPosition.setBackgroundResource(R.drawable.shallow);
				} else {
					btnLocusTag.setBackgroundResource(R.drawable.shallow);
					btnPosition.setBackgroundResource(R.drawable.deep);
				}
				etPosTag.setHint("eg:1:200..3566");
			}
		});

		btnDefault.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.def1);
				} else {
					v.setBackgroundResource(R.drawable.def);
				}
				return false;
			}
		});

		/************************* 保存URL按钮 **************************/

		btnSaveURL.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context ctx = SubmitActivity.this;
				SharedPreferences info = ctx.getSharedPreferences("INFO",
						MODE_PRIVATE);
				Editor editor = info.edit();
				editor.putString("URL", etSaveURL.getText().toString());
				editor.commit();
				URL = info.getString("URL", "null"); // 更新
				Toast.makeText(SubmitActivity.this, "保存成功 IP:" + URL,
						Toast.LENGTH_SHORT).show();
			}

		});

		/************************* 提交主程序按钮 **************************/

		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
				MainSubmitPost("http://" + URL);
			}
		});

		btnSubmit.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.sub1);
				} else {
					v.setBackgroundResource(R.drawable.sub);
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
				+ "&rfc=" + strRFC + "&listcount=" + strCount + "&key=" + "123"+ "&r1=" + String.valueOf(weightR1);
		if (tpFalg) {
			strRequest = strRequest + "&gene=" + strPosTag;
		} else {
			strRequest = strRequest + "&location=" + strPosTag;
		}
		URL = URL + strRequest;
		myHttpThread = new HttpThread(URL, HttpThread.MAIN_SUBMIT, handler);
		myHttpThread.start();
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
		builder.setTitle("DebugInfo");
		builder.setCancelable(true);
		builder.create().show();
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
		RadioGroup group = (RadioGroup) DialogView.findViewById(R.id.radioGroup1);
		tvWeight = (TextView) DialogView.findViewById(R.id.textView_weightT);
		sbWeight = (SeekBar) DialogView.findViewById(R.id.seekBar_weight);

		sbWeight.setMax(100);
		tvWeight.setText("Specificity " + String.valueOf(weightR1) + "  Active" + String.valueOf(100-weightR1));
		sbWeight.setProgress(weightR1);
		
		sbWeight.setOnSeekBarChangeListener(new OnSeekBarChangeListener() //调音监听器  
        {  
            public void onProgressChanged(SeekBar arg0,int progress,boolean fromUser)  
            {  
            	weightR1 = sbWeight.getProgress();
            	tvWeight.setText("Specificity " + String.valueOf(weightR1) + "  Active" + String.valueOf(100-weightR1));
            }

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.dismiss();
					}
				});

		alertDialog = builder.create();
		alertDialog.show();
		WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
		lp.width = (int) (SCREEN_WIDTH * 0.5);// 定义宽度
		lp.height = (int) (SCREEN_HEIGHT * 0.9);// 定义高度
		alertDialog.getWindow().setAttributes(lp);

	}

	/************************** 获取结果状态 *************************/
	
	private boolean getStatus(String json) {
		String strStatus = "";
		try {
			strStatus = new JSONObject(json).getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strStatus.equals("0")) {
			return true;
		} else
			return false;
	}

	/************************** 获取错误信息 *************************/
	
	private String getErrorMessage(String json) {
		String errorMessage = null;
		try {
			errorMessage = new JSONObject(json).getString("message");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMessage;
	}

	/************************** 保存参数信息 *************************/
	
	private void saveMessage(String json) {
		String saveMessage = null;
		String saveSPECIE = null;
		String saveGENE = null;
		String saveLOCATION = null;
		try {
			saveMessage = new JSONObject(json).getString("message");
			JSONObject jsonObj = new JSONObject(saveMessage);
			saveSPECIE = jsonObj.getString("specie");
			saveGENE = jsonObj.getString("gene");
			saveLOCATION = jsonObj.getString("location");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 保存json与一些信息
		Context ctx = SubmitActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("BUFF", MODE_PRIVATE);
		Editor editor = info.edit();
		editor.putString("SPECIE", saveSPECIE);
		editor.putString("GENE", saveGENE);
		editor.putString("LOCATION", saveLOCATION);
		editor.putString("JSON", json);
		editor.commit();
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
		// //销毁线程通信
		// myHttpThread.interrupt();
	}

}
