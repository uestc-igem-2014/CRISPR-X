package Model;


import java.text.ParseException;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Jiemian.*;
public class Analyze
	{
	static String varlueStr;
	static StringBuffer value=new StringBuffer();
	static String[][][] dataofftarget;
	static String[][] datakey;
	static int offtargetNum=40;
	static int jsonLengthson;
	static int ok;
	static int status;
	public Analyze(StringBuffer value){
		this.value=value;
		varlueStr=value.toString();
		try {
			ParseJson(varlueStr);
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}
		new result(datakey,dataofftarget);
		if(ok==1){
			try{
				UIManager.put("RootPane.setupButtonVisible",false);
				BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
				org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
				new result();
				
			}
			catch(Exception e){
			}
		}else{
		}
		
		
	}
	   public Analyze() {
	}
	public static void ParseJson(String varlueStr) throws JSONException,
       ParseException {
		   String strand = null;
		   String position = null;
		   JSONObject dataJson=new JSONObject(varlueStr);
		   status=dataJson.getInt("status");
		   if(status==0){
//			   JSONObject message=dataJson.getJSONObject("message");
			   String location=dataJson.getString("location");
//			   System.out.print(location);
			   JSONArray result= dataJson.getJSONArray("result");
			   int jsonLength=result.length();
			   String[] positionStr=new String[jsonLength];
			   String[] strandStr=new String[jsonLength];
			   datakey=new String[jsonLength][offtargetNum];
			   dataofftarget=new String[jsonLength][offtargetNum][6];
			   for(int i=0;i<jsonLength;i++){
	       			JSONArray dataJsonOfftarget=result.getJSONObject(i).getJSONArray("offtarget");
//	       			System.out.print(dataJsonOfftarget);
	       			jsonLengthson=dataJsonOfftarget.length();
	       			for(int j=0;j<jsonLengthson;j++){
	       				String osequence=dataJsonOfftarget.getJSONObject(j).getString("osequence");
	       				String oscore=dataJsonOfftarget.getJSONObject(j).getString("oscore");
	       				int omms=dataJsonOfftarget.getJSONObject(j).getInt("omms");
	       				String ostrand=dataJsonOfftarget.getJSONObject(j).getString("ostrand");
	       				String oposition=dataJsonOfftarget.getJSONObject(j).getString("oposition");
	       				String oregion=dataJsonOfftarget.getJSONObject(j).getString("oregion");
	       				dataofftarget[i][j][0]=osequence;
	       				dataofftarget[i][j][1]=oscore+"";
	       				dataofftarget[i][j][2]=omms+"";
	       				dataofftarget[i][j][3]=ostrand;
	       				dataofftarget[i][j][4]=oposition;
	       				dataofftarget[i][j][5]=oregion;	
	       			}//i代表key；j代表offtarget
	       			String key=result.getJSONObject(i).getString("key");
	       			datakey[i][0]=key;
	       			String grna=result.getJSONObject(i).getString("grna");
	       			datakey[i][1]=grna;
	       			position=result.getJSONObject(i).getString("position");
        			datakey[i][2]=position;        			
        			positionStr[i]=position;
        			int total_score=result.getJSONObject(i).getInt("total_score");
        			datakey[i][3]=total_score+"";
        			strand=result.getJSONObject(i).getString("strand");
        			datakey[i][4]=strand;
        			strandStr[i]=strand;
//        			int count=result.getJSONObject(i).getInt("count");
//        			datakey[i][5]=count+"";
			   }
	     new result(positionStr,location,strandStr,jsonLength);
	     ok=1;
	   }else if(status==1){
		   javax.swing.JOptionPane.showMessageDialog(null,"没有该基因","massage",0);
	   }else if(status==2){
		   javax.swing.JOptionPane.showMessageDialog(null,"计算未完成","massage",0);
	   }
	}
}
