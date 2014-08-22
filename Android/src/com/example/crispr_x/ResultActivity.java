package com.example.crispr_x;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ResultActivity extends Activity {
	
	private TextView tvTargetGenomeT, tvLocusTagT, tvPositionT, tvPositionF, tvRNAF, tvScore;
	private ListView lvFather, lvChild;
	private ImageView ivMap;

	private String strPosition = null;// Position字串
	private String strLocusTag = null;// LocusTag字串
	private String strTargetGenome = null;// TargetGenome字串
	private String jsonString;
	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	private String jGrna, jPosition, jTotalScore, jSspe, jSeff, jStrand,fGrna, fPosition, fTotalScore;	//父级关键字
	
	SimpleAdapter fatherListAdapter, childListAdapter;
	Bitmap Map ;
	View oldView;
	
	ResultParser resultParser = new ResultParser();
	
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
		
		setContentView(R.layout.activity_result);
		
		tvTargetGenomeT = (TextView) this.findViewById(R.id.textView_targetgenomeTR);
		tvLocusTagT = (TextView) this.findViewById(R.id.textView_textView_locustagRT);
		tvPositionT = (TextView) this.findViewById(R.id.textView_positionRT);
		lvFather = (ListView) this.findViewById(R.id.listView_father);
		lvChild = (ListView) this.findViewById(R.id.listView_child);
		tvPositionF = (TextView) this.findViewById(R.id.textView_position);
		tvRNAF = (TextView) this.findViewById(R.id.textView_grna);
		tvScore = (TextView) this.findViewById(R.id.textView_score);
		ivMap = (ImageView) this.findViewById(R.id.imageView_map);
		
		Context ctx = ResultActivity.this;
		SharedPreferences info = ctx.getSharedPreferences("BUFF", MODE_PRIVATE);
		strPosition = info.getString("LOCATION", ""); // 更新
		strLocusTag = info.getString("GENE", "");
		strTargetGenome = info.getString("SPECIE", "");
		jsonString = info.getString("JSON", "");
		
		tvTargetGenomeT.setText(strTargetGenome);
		tvLocusTagT.setText(strLocusTag);
		tvPositionT.setText(strPosition);
		
		parseFatherListJson(jsonString);
		
		/*********************** FatherList点击事件 *********************/

		lvFather.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2!=0) {
					parseChildListJson(jsonString,arg2-1);
					getDetail(jsonString,arg2-1);
					tvPositionF.setText(fPosition);
					tvRNAF.setText(fGrna);
					tvScore.setText(fTotalScore);
					CreatMap map = new CreatMap();
					Map =  map.canvasParse(strPosition, jPosition, jStrand, SCREEN_WIDTH);
					ivMap.setImageBitmap(Map);
				}
			}
		});
		
	}
	
	/*********************** 解析FatherList数据并加入列表 **********************/

	public void parseFatherListJson(String JSON) {

		List<Map<String, Object>> fatherList = resultParser.fatherList(JSON);

		// 生成适配器的父级列表和动态数组对应的元素
		fatherListAdapter = new SimpleAdapter(this, fatherList, R.layout.result_list_father,
				new String[] { "key", "grna", "total_score" },
				new int[] { R.id.textView_key, R.id.textView_grna,
						R.id.textView_total_score});

		// 添加并且显示
		lvFather.setAdapter(fatherListAdapter);
	}
	
	/*********************** 解析ChildList数据并加入列表 **********************/

	public void parseChildListJson(String JSON, int itemID) {

		List<Map<String, Object>> childList = resultParser.childList(JSON, itemID);

		// 生成适配器的子级列表和动态数组对应的元素
		childListAdapter = new SimpleAdapter(this, childList, R.layout.result_list_child,
				new String[] { "sequence", "score", "mms", "strand", "position", "region" },
				new int[] { R.id.textView_sequence, R.id.textView_score,
						R.id.textView_mms,
						R.id.textView_strand,
						R.id.textView_position,
						R.id.textView_region});

		// 添加并且显示
		lvChild.setAdapter(childListAdapter);
		
	}
	
	/*********************** 解析FatherList数据详细信息 **********************/
	
	private void getDetail(String json, int itemID){
		String saveMessage = null;
		try {
			//json根信息提取
			JSONArray jsonObjs = new JSONObject(json).getJSONArray("result");

			//json父级信息提取
				JSONObject jsonObj = ((JSONObject) jsonObjs.opt(itemID));
				jPosition = jsonObj.getString("position"); // 位置
				jStrand = jsonObj.getString("strand"); // 方向
				jGrna = jsonObj.getString("grna"); // GRNA
				jTotalScore = jsonObj.getString("total_score"); // 总评分
				jSspe = jsonObj.getString("Sspe"); // Sspe分
				jSeff = jsonObj.getString("Seff"); // Seff分
				fTotalScore = "TotalScore:" + jTotalScore + "  Specificity:" + jSspe + "  Active:" + jSeff;
				fPosition = "Position:" + jStrand + jPosition;
				fGrna = "gRNA:" + jGrna;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}
	
}
