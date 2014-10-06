package Model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BulidJson {
	static String[][] chromosomeName;
	static String PAM;
	static int length;
	static String speciename;
	public static void main(String[] args) {
		new BulidJson();
	}
	public BulidJson(){
		
	}
	public BulidJson(String[][] chromosomeName,String PAM,int length,String speciename){
		this.chromosomeName=chromosomeName;
		this.length=length;
		this.PAM=PAM;
		this.speciename=speciename;
	}
	public JSONObject Json(){
		JSONObject ok=new JSONObject();
		JSONObject ok1=new JSONObject();
		JSONArray jsonMembers = new JSONArray();
		for(int i=0;i<length;i++){
			ok.put("fileName",chromosomeName[i][0].substring(0,chromosomeName[i][0].lastIndexOf(".")-1));
			jsonMembers.add(ok);
		}
//		ok.put("chromosomeName", "value");
		ok1.put("specie", speciename);
		ok1.put("files", jsonMembers);
		ok1.put("PAM", PAM);
		return ok1;
	}
	
}
