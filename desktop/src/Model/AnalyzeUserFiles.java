package Model;

import org.json.JSONArray;
import org.json.JSONException;

import Jiemian.Intex;

public class AnalyzeUserFiles {
	static String[][] filesStr;
	public String[][] getAnalyzeUserFiles(){
		return filesStr;
	}
	
	public String[][] Analyze(String files) throws JSONException{
		System.out.println(files);
		JSONArray filesname=new JSONArray(files);
		int length=filesname.length();
		filesStr=new String[length][2];
		for(int i=0;i<length;i++){
			filesStr[i][0]=filesname.getJSONObject(i).getString("fileName");
			filesStr[i][1]=filesname.getJSONObject(i).getString("note");		
		}
//		new AnalyzeHistory(filesStr);
//		System.out.print(filesStr[0][0]);
//		new Intex("").change(filesStr);
		return filesStr;
	}
}
