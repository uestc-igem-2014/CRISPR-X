package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalyzeM {
	String MoreInfoStr,RNAfold,RNAfoldimg;
	int GC;
	public List<MoreIfozhi> MoreInfo=new ArrayList<MoreIfozhi>();
	public AnalyzeM(String MoreInfoStr) throws JSONException{
		JSONObject dataJson=new JSONObject(MoreInfoStr);
		RNAfoldimg=dataJson.getString("RNAfold-img");
		GC=dataJson.getInt("GC-ratio");
		RNAfold=dataJson.getString("RNAfold-res");
		JSONArray enzyme= dataJson.getJSONArray("enzyme");
		int jsonLength=enzyme.length();
		for(int i=0;i<jsonLength;i++){
			MoreIfozhi moreIfozhi=new MoreIfozhi();
			int start=enzyme.getJSONObject(i).getInt("start");
			moreIfozhi.setStart(start);
			int end=enzyme.getJSONObject(i).getInt("end");
			moreIfozhi.setEnd(end);
			String enzyme_name=enzyme.getJSONObject(i).getString("enzyme_name");
			moreIfozhi.setEnzyme_name(enzyme_name);
			String strand=enzyme.getJSONObject(i).getString("strand");
			moreIfozhi.setStrand(strand);
			String matched_seq=enzyme.getJSONObject(i).getString("matched_seq");
			moreIfozhi.setMatched_seq(matched_seq);
			MoreInfo.add(moreIfozhi);
		}
		
	}
	public String getRNAfoldimg() {
		return RNAfoldimg;
	}
	public AnalyzeM() {
		
	}
	public String getRNAfold() {
		return RNAfold;
	}
	public int getGC() {
		return GC;
	}
}
