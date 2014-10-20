package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.json.JSONException;





import Jiemian.IDsearch;
import Jiemian.denlu;

public class Send implements Runnable{
	String PAMValue;
	String TargetGenomeValue;
	String IDValue;
	String PositionValue;
	String Value1;
	double r1;
	int postStatus=3,ntlength;//0_signup,1_login,2_history
	String lines,rfcStr,regionStr;
	String name,pswd,email;
	public String key="361610445ee59220d3463620ee42fa1a";
	static StringBuffer ID;
	StringBuffer value=new StringBuffer();
	StringBuffer value_MoreInfo=new StringBuffer();
	String blast="CCACATGCCAT";
	static File file;
	static String note,IDStr;
	static String filename;
	static JSONObject fliesImport;
	public String result_URL="http://i.uestc.edu.cn/iGEM2014/getResult.php";
	public String GET_URL="http://i.uestc.edu.cn/iGEM2014/getMain.php?";
	public String signup_URL=" http://i.uestc.edu.cn/iGEM2014/login/signup.php";
	public String login_url="http://i.uestc.edu.cn/iGEM2014/login/";
	public String getHistory_url="http://i.uestc.edu.cn/iGEM2014/getHistory.php";
	public String upload_url="http://i.uestc.edu.cn/iGEM2014/upload/";
	public String checkfile_URL="http://i.uestc.edu.cn/iGEM2014/upload/import.php";
	public String importfile_URL="http://i.uestc.edu.cn/iGEM2014/upload/import.php";
	public String Viewmyfiles_URL="http://i.uestc.edu.cn/iGEM2014/upload/viewmyfiles.php";
	public String Viewmysp_URL="http://i.uestc.edu.cn/iGEM2014/upload/viewmyspecies.php";
	public String MoreInfo_URL="http://i.uestc.edu.cn/iGEM2014/getMoreInfo.php?";
	public String MoreInfoimg_URL="http://i.uestc.edu.cn/iGEM2014/RNAFold/";
	
	
    	public Send(String TargetGenomeValue,String PAMValue,String IDValue,String PositionValue, String rfcStr,String regeion,int ntlength,double r1ralue) {
		    this.TargetGenomeValue = TargetGenomeValue;
		    this.PAMValue=PAMValue;
		    this.IDValue=IDValue;
		    this.PositionValue=PositionValue;
		    this.rfcStr=rfcStr;	
		    this.regionStr=regeion;
		    this.ntlength=ntlength;
		    this.r1=r1ralue;
    	}
    	public Send() {
			
		}
    	public String Send(String name,String pswd){
    		this.name=name;
    		this.pswd=pswd;
    		postStatus=1;
    		SendPost(login_url,name,pswd,"");
    		key=value.toString();
    		System.out.println(key);
			return value.toString();
    	}
    	public Send(String ID){
    		this.IDValue=ID;
    	}
    	public int uploadFile(File file,String note,String filename){
    		this.file=file;
    		String ll=this.file.getName();
    		this.filename=filename;
    		this.note=note;
//    		new Send(4);
			return 1;
    	}
    	public int importFile(JSONObject fliesImport){
    		this.fliesImport=fliesImport;
    		System.out.print(fliesImport.toString());
			return 1;
    	}
    	public  Send(int postStatus) throws JSONException{
    		this.postStatus=postStatus;
    		if(postStatus==0){
		    	SendPost(signup_URL,name,pswd,email);
		    }else if(postStatus==1){
		    }else if(postStatus==2){
		    	StringBuffer history=SendPost(getHistory_url,key,"","");
		    	new AnalyzeHistory().historyAnalyze(history.toString());
		    }else if(postStatus==3){
		    	StringBuffer kk=SendPost(result_URL,IDStr, "", "");;
		    	new Analyze(kk);
		    }else if(postStatus==4){
		    	SendPost(upload_url,name,pswd,email);
		    	if(value.equals("N2")){
		    		javax.swing.JOptionPane.showMessageDialog(null,filename+"Upload failed");
		    	}else{
		    		javax.swing.JOptionPane.showMessageDialog(null,filename+"Uploaded successfully");
		    	}
		    }else if(postStatus==5){
		    	SendPost(importfile_URL,fliesImport.toString(),"","");
		    }else if(postStatus==6){
		    	StringBuffer files=SendPost(Viewmyfiles_URL,name,"","");
		    	new AnalyzeUserFiles().Analyze(files.toString());
		    }else if(postStatus==7){
		    	StringBuffer sp=SendPost(Viewmysp_URL,name,"","");
		    	new AnalyzeUserspce().Analyze(sp.toString());
		    }
    	}
		public  void SendGet() throws IOException{
    		String getURL = GET_URL + "type=1"+"&pam="+PAMValue+"&specie=" + TargetGenomeValue+"&location="+IDValue+"&gene="+PositionValue+"&rfc="+rfcStr+"&region="+regionStr+"&length="+ntlength+"&r1="+r1+"&token="+key;
    		URL getUrl = new URL(getURL);
    		System.out.print(getURL);
    		 HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
    		 connection.connect();
    		 connection.setConnectTimeout(100000);
    		 InputStreamReader reade=new InputStreamReader(connection.getInputStream(),"utf-8");
    		 ID=new StringBuffer();
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
    		 IDStr=ID.toString();
//    		 System.out.println(value);
    		 new IDsearch(IDStr);
    		 new IDsearch();
//    		 SendPost(result_URL,ID.toString(), "", "");
    		 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//    		    while ((lines = reader.readLine()) !=null){
//    	        	lines = new String(lines.getBytes(), "utf-8");
//    	        	value.append(lines);
//    	            System.out.println(lines);
//    	            System.out.println(value);
//    	        }
//    	        reader.close();
    	        connection.disconnect();
    	}
		public String sendgetM(String sequenceStr) throws IOException{
			String sequence= MoreInfo_URL +"sequence="+sequenceStr;
			System.out.println(sequence);
			URL getUrl = new URL(sequence);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
   		 	connection.connect();
   		 	connection.setConnectTimeout(100000);
   		 	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
		    while ((lines = reader.readLine()) !=null){
		    	lines = new String(lines.getBytes(), "utf-8");
		    	value_MoreInfo.append(lines);
		    	System.out.println(value_MoreInfo);
		    }
		    reader.close();
		    connection.disconnect();
			return value_MoreInfo.toString();
			
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
				link.setConnectTimeout(100000);
				OutputStream Data=link.getOutputStream();
				OutputStreamWriter inputData=new OutputStreamWriter(Data,"utf-8");
				if(postStatus==0){
					inputData.write("myhistory=TTEE&pswd=12345&email=23111");
				}else if(postStatus==1){
					inputData.write("myhistory="+name+"&pswd="+pswd);
				}else if(postStatus==2){
					inputData.write("token="+name);
				}else if (postStatus==3){
					inputData.write("id="+IDStr);
//					System.out.println(ID);
				}else if (postStatus==4){
					StringBuffer flieStr = new StringBuffer();
					FileInputStream geneFile=new FileInputStream(file);
					int aa;
					aa=geneFile.read();
					while(aa!=-1){
						aa=geneFile.read();
						flieStr.append((char)aa);
					}
					inputData.write("token="+key+"&file="+flieStr.toString()+"&note="+note+"&filename="+filename);
					
				}else if(postStatus==5){
					inputData.write("token="+key+"&command="+name);
				}else if(postStatus==6){
					inputData.write("token="+key);
				}else if(postStatus==7){
					inputData.write("token="+key);
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
    	        	
    	            System.out.println(value);
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