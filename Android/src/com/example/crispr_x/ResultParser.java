package com.example.crispr_x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultParser {
	
	String jKey, jGrna, jPosition, jTotalScore, jOfftarget;	//父级关键字
	String joSequence, joScore, joMms, joStrand, joPosition, joRegion;	//子级关键字

	//父级列表解析
	public List<Map<String, Object>> fatherList(String strResult) {
		// 生成动态数组，加入数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map; // 添加map
		map = new HashMap<String, Object>();
		map.put("key", "Count");
		map.put("grna", "gRNA");
		map.put("total_score", "TotalScore");
		//list嵌套list
		map.put("offtarget", "Offtarget");
		
		list.add(map);

		try {
			//json根信息提取
			JSONArray jsonObjs = new JSONObject(strResult)
					.getJSONArray("result");

			//json父级信息提取
			for (int i = 0; i < jsonObjs.length(); i++) {
				JSONObject jsonObj = ((JSONObject) jsonObjs.opt(i));
				jKey = jsonObj.getString("key"); // 名次
				jGrna = jsonObj.getString("grna"); // GRNA
				jTotalScore = jsonObj.getString("total_score"); // 总评分

				map = new HashMap<String, Object>();
				map.put("key", jKey);
				map.put("grna", jGrna);
				map.put("total_score", jTotalScore);
				
				list.add(map);
			}

		} catch (JSONException e) {
			System.out.println("Jsons parse error : fatherList");
			e.printStackTrace();
		}
		return list;
	}
	
	//子级列表解析
	public List<Map<String, Object>> childList(String strResult ,int itemID) {
		// 生成动态数组，加入数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map; // 添加map
		map = new HashMap<String, Object>();
		map.put("sequence", "Sequence");
		map.put("score", "Score");
		map.put("mms", "Mms");
		map.put("str", "Strand");
		map.put("position", "Position");
		map.put("region", "Region");
		
		
		list.add(map);

		try {
			//json根信息提取
			JSONArray jsonObjs = new JSONObject(strResult)
					.getJSONArray("result");

			//json父级信息提取
				JSONObject jsonObj = ((JSONObject) jsonObjs.opt(itemID));
				jKey = jsonObj.getString("key"); // 名次
				jGrna = jsonObj.getString("grna"); // GRNA
				jTotalScore = jsonObj.getString("total_score"); // 总评分
				jPosition = jsonObj.getString("position"); // 位置
				JSONArray jsonObjsc = jsonObj.getJSONArray("offtarget");	// 脱靶信息
				
			//json子级信息提取
			for (int i = 0; i < jsonObjsc.length(); i++) {
				JSONObject jsonObjc = ((JSONObject) jsonObjsc.opt(i));
				joSequence = jsonObjc.getString("osequence"); // 
				joScore = jsonObjc.getString("oscore"); // 
				joMms = jsonObjc.getString("omms"); // 
				joStrand = jsonObjc.getString("ostrand"); // 
				joPosition = jsonObjc.getString("oposition"); // 
				joRegion = jsonObjc.getString("oregion"); // 

				map = new HashMap<String, Object>();
				map.put("sequence", joSequence);
				map.put("score", joScore);
				map.put("mms", joMms);
				map.put("strand", joStrand);
				map.put("position", joPosition);
				map.put("region", joRegion);
				
				list.add(map);
			}

		} catch (JSONException e) {
			System.out.println("Jsons parse error : childList");
			e.printStackTrace();
		}
		return list;
	}
}
