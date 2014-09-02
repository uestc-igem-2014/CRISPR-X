package com.example.crispr_x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CreatList {
	static String listJson;

	public CreatList(String json) {
		this.listJson = json;
		Log.i("CreatList", listJson);
	}

	public CreatList() {
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> creatMenu() {
		// 生成动态数组，加入数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map; // 添加map

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "Crispr-X");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "CheckResult");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "History");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "Wiki");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "Help");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", "checkResult");
		map.put("menu", "About");
		list.add(map);

		return list;
	}

	public List<Map<String, Object>> creatHistory() {
//		Log.i("history", listJson);
		// 生成动态数组，加入数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map; // 添加map

		map = new HashMap<String, Object>();
		map.put("id", "ID");
		map.put("status", "Status");
		list.add(map);

		try {
			JSONArray jsonObjs = new JSONArray(listJson);
			for (int i = 0; i < jsonObjs.length(); i++) {
				String id = ((JSONObject) jsonObjs.opt(i))
						.getString("request_id");
				String statusNum = ((JSONObject) jsonObjs.opt(i))
						.getString("status");
				String status = null;
				if (statusNum.equals("0")) {
					status = "Done";
				} else if (statusNum.equals("1")) {
					status = "Error";
				} else if (statusNum.equals("2")) {
					status = "Running";
				}

				map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("status", status);
				list.add(map);
			}
		} catch (JSONException e) {
			System.out.println("Jsons parse error : list");
			e.printStackTrace();
		}

		return list;
	}
}
