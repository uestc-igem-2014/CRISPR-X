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
		CreatList.listJson = json;
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
		map.put("icon", R.drawable.home);
		map.put("menu", "Crispr-X");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.check);
		map.put("menu", "CheckResult");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.his);
		map.put("menu", "History");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.check);
		map.put("menu", "Wiki");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.help);
		map.put("menu", "Help");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.about);
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
	
	public List<Map<String, Object>> creatEnzyme(String json) {
//		Log.i("history", listJson);
		// 生成动态数组，加入数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map; // 添加map

		map = new HashMap<String, Object>();
		map.put("enzyme_name", "name");
		map.put("strand", "strand");
		map.put("matched_seq", "matched");
		list.add(map);

		@SuppressWarnings("unused")
		int start,end;
		String enzyme_name,strand,matched_seq;
		try {
			JSONArray jsonObjs = new JSONObject(json).getJSONArray("enzyme");
			for (int i = 0; i < jsonObjs.length(); i++) {
				JSONObject jsonObjarr = ((JSONObject) jsonObjs.opt(i));

				start = jsonObjarr.getInt("start");
				end = jsonObjarr.getInt("end");
				enzyme_name = jsonObjarr.getString("enzyme_name");
				strand = jsonObjarr.getString("strand");
				matched_seq = jsonObjarr.getString("matched_seq");
				
				String strVoid = "";
				if(strand.endsWith("+")){
					for(int k=0;k<start;k++){
						strVoid = strVoid + " ";
					}
					matched_seq = strVoid+matched_seq;
				} else{
					matched_seq = reverseString(matched_seq);
					for(int k=0;k<start;k++){
						strVoid = strVoid + " ";
					}
					matched_seq = strVoid+matched_seq;
				}
				map = new HashMap<String, Object>();
				map.put("enzyme_name", enzyme_name);
				map.put("strand", strand);
				map.put("matched_seq", matched_seq);
				list.add(map);
			}
		} catch (JSONException e) {
			System.out.println("Jsons parse error : MoreInfo");
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 倒置字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String reverseString(String str)
	{
		char[] arr=str.toCharArray();
		int middle = arr.length>>1;//EQ length/2
		int limit = arr.length-1;
		for (int i = 0; i < middle; i++) {
			char tmp = arr[i];
			arr[i]=arr[limit-i];
			arr[limit-i]=tmp;
		}
		return new String(arr);
	}
	
}
