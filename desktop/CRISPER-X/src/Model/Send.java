package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

import Jiemian.IDsearch;
import Jiemian.denlu;

public class Send implements Runnable{
	String PAMValue;
	String TargetGenomeValue;
	String IDValue;
	String PositionValue;
	String Value1;
	int postStatus=3;//0_signup,1_login,2_history
	String lines,rfcStr;
	String name,pswd,email;
	public String key="e419421cfe2b0410b283ac7ab43c882c";
	static StringBuffer ID=new StringBuffer();
	StringBuffer value=new StringBuffer();
	String blast="CCACATGCCAT";
	public String result_URL="http://immunet.cn/iGEM2014/getResult.php";
	public String GET_URL="http://immunet.cn/iGEM2014/getMain.php?";
	public String signup_URL=" hhttp://immunet.cn/iGEM2014/login/signup.php";
	public String login_url="http://immunet.cn/iGEM2014/login/";
	public String getHistory_url="http://immunet.cn/iGEM2014/getHistory.php";
	
	
    	public Send(String TargetGenomeValue,String PAMValue,String IDValue,String PositionValue, String rfcStr) {
		    this.TargetGenomeValue = TargetGenomeValue;
		    this.PAMValue=PAMValue;
		    this.IDValue=IDValue;
		    this.PositionValue=PositionValue;
		    this.rfcStr=rfcStr;	    
    	}
    	public Send() {
			
		}
    	public String Send(String name,String pswd){
    		this.name=name;
    		this.pswd=pswd;
    		postStatus=1;
    		SendPost(login_url,name,pswd,"");
//    		System.out.println("sussess");
			return value.toString();
    	}
    	public Send(String ID){
    		this.IDValue=ID;
    	}
    	public  Send(int postStatus) throws JSONException{
    		this.postStatus=postStatus;
    		if(postStatus==0){
		    	SendPost(signup_URL,name,pswd,email);
		    }else if(postStatus==1){
		    	SendPost(login_url,name,pswd,"");
		    }else if(postStatus==2){
		    	StringBuffer history=SendPost(getHistory_url,key,"","");
		    	new AnalyzeHistory().historyAnalyze(history.toString());
		    }else if(postStatus==3){
		    	StringBuffer kk=SendPost(result_URL,ID.toString(), "", "");
		    	System.out.println(kk);
		    	new Analyze(kk);
		    }
    	}
		public  void SendGet() throws IOException{
    		String getURL = GET_URL + "key=123"+"&pam="+PAMValue+"&specie=" + TargetGenomeValue+"&location="+IDValue+"&gene="+PositionValue+"&rfc="+rfcStr+"&listcount=40"+"&token="+key;
    		URL getUrl = new URL(getURL);
    		 HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
    		 connection.connect();
    		 InputStreamReader reade=new InputStreamReader(connection.getInputStream(),"utf-8");
    		 for(int i=0;i<20;i++){
    		 int kk=reade.read();
    		 if((char)kk=='Q'){
//    			 char line=(char)kk;
//    			 value.append(line);
    			break;
    		 }
    		 char line=(char)kk;
			 ID.append(line); 
    		 }
//    		 System.out.println(value);
    		 new IDsearch(ID.toString());
    		 new IDsearch();
//    		 SendPost(result_URL,ID.toString(), "", "");
//    		 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//    		    while ((lines = reader.readLine()) !=null){
//    	        	lines = new String(lines.getBytes(), "utf-8");
//    	        	value.append(lines);
//    	            System.out.println(lines);
//    	            System.out.println(value);
//    	        }
//    	        reader.close();
    	        connection.disconnect();
    	}
		public  StringBuffer SendPost(String sUrl,String name,String pswd,String email){
			
			try{
				if(sUrl==null||sUrl.trim().equals("")){
					System.out.print("URLµØÖ·ÎÞÐ§");
				}
				URL url=new URL(sUrl);
				HttpURLConnection link=(HttpURLConnection) url.openConnection();
				link.setRequestMethod("POST");
				link.setDoOutput(true);
				link.setDoInput(true);
				OutputStream Data=link.getOutputStream();
				OutputStreamWriter inputData=new OutputStreamWriter(Data,"utf-8");
				if(postStatus==0){
					inputData.write("name=TTEE&pswd=12345&email=23111");//WUTE&12345&8e300bf609f4415c99a57d78b3e73abf
				}else if(postStatus==1){
					inputData.write("name="+name+"&pswd="+pswd);
				}else if(postStatus==2){
					inputData.write("token="+name);
				}else if (postStatus==3){
					inputData.write("id="+ID);
					System.out.println(ID);
				}
//				System.out.println(inputData.toString());
				inputData.flush();
				inputData.close();
				Data.close();
				InputStream in=link.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(link.getInputStream(),"utf-8"));
    		    while ((lines = reader.readLine()) != null){
    	        	lines = new String(lines.getBytes(), "utf-8");
    	        	value.append(lines);
//    	            System.out.println(lines);
//    	            System.out.println(value);
    	        }
				in.close();
				link.disconnect();
				if(postStatus==1){
					if(value.toString().equals("-")){
						key="0";
					}else{
						key=value.toString();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return value;
		}
		@Override
		public void run() {
			try {
				SendGet();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
}