package Model;

import java.util.List;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONArray;
import org.json.JSONException;

import Jiemian.User;

public class AnalyzeHistory implements Runnable{
	String[] ID,status;
	String[] statusY;
	static String[][] historyStr,files;
	String history;
	static List<Specie> ff;
	static String[] filesname;
//	public AnalyzeHistory(String[][] files){
//		this.files=files;
//	}
	
	public AnalyzeHistory(List<Specie> ff){
		this.ff=ff;
		int length=ff.size();
		files=new String[length][2];
		for(int i=0;i<length;i++){
			files[i][0]=ff.get(i).getSpecieName();
			files[i][1]="1";
		}
	}
	 public AnalyzeHistory() {
	}
	public void historyAnalyze(String history) throws JSONException{
		System.out.println(history);
		   JSONArray datahistory=new JSONArray(history);
//		   System.out.println(history);
		   int length=datahistory.length();
		   historyStr=new String[length][2];
		   for(int i=0;i<length;i++){
			   historyStr[i][0]=datahistory.getJSONObject(i).getString("request_id");
			   historyStr[i][1]=datahistory.getJSONObject(i).getString("status");
			   
		   }
		   BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		   new User(historyStr,files);
	   }

	@Override
	public void run() {
		try {
				check();
			   Thread.sleep(3000);
		}catch ( InterruptedException | JSONException e) {
			e.printStackTrace();
		}   
	   }
	public String[] check() throws JSONException{
		JSONArray datahistory = null;
		datahistory = new JSONArray(history);
		int length=datahistory.length();
		   for(int i=0;i<length;i++){
			   statusY[i]=datahistory.getJSONObject(i).getString("status");
			   if(statusY[i].equals(historyStr[i][1])){
				   System.out.print(historyStr[i][0]+"ÒÑÍê³É");
			   }else{
				   status[i]=statusY[i];
			   }
	   }
		return statusY;		
	}
}
