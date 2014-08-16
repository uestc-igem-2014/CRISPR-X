package Model;

import org.json.JSONArray;
import org.json.JSONException;

import Jiemian.User;

public class AnalyzeHistory implements Runnable{
	String[] ID,status;
	String[] statusY;
	static String[][] historyStr;
	String history;
	 public void historyAnalyze(String history) throws JSONException{
		   JSONArray datahistory=new JSONArray(history);
		   System.out.println(history);
		   int length=datahistory.length();
		   historyStr=new String[length][2];
		   System.out.println(length);
		   for(int i=0;i<length;i++){
			   historyStr[i][0]=datahistory.getJSONObject(i).getString("request_id");
			   historyStr[i][1]=datahistory.getJSONObject(i).getString("status");
			   
		   }
		   new User(historyStr);
		   new User();
	   }

	@Override
	public void run() {
		JSONArray datahistory = null;
		try {
			datahistory = new JSONArray(history);
			int length=datahistory.length();
			   ID=new String[length];
			   status=new String[length];
			   for(int i=0;i<length;i++){
				   statusY[i]=datahistory.getJSONObject(i).getString("status");
				   if(statusY[i].equals(status[i])){					   
				   }else{
					   status[i]=statusY[i];
				   }
		   }
		}catch (JSONException e) {
			e.printStackTrace();
		}   
	   }
}
