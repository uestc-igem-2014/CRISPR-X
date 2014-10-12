package com.example.crispr_x;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;

public class CreatMap {

	static ArrayList<Integer> ListEndpoint = new ArrayList<Integer>(); 
	static ArrayList<String> ListDescription = new ArrayList<String>(); 
	
	public CreatMap(){};
	
	public CreatMap(ArrayList<Integer> cListEndpoint, ArrayList<String> cListDescription){
		CreatMap.ListEndpoint = cListEndpoint;
		CreatMap.ListDescription = cListDescription;
	};
	
	public Bitmap canvasParse(String allPos, String selcetPos, String dir, int SCREEN_WIDTH) {
		Bitmap chromeMap = Bitmap.createBitmap( SCREEN_WIDTH, 80, Config.ARGB_8888 );
		int startPos, endPos;
		int len;		//总序列长度,单位像素的序列长度
		float lenUnit;
		int onPos;
		String x[]=allPos.split("[:..]");
		startPos = Integer.parseInt(x[1]);		//开始位点
		endPos = Integer.parseInt(x[3]);		//终止位点
		
		//画总序列
		len = endPos-startPos;
		lenUnit = (float) (len/(SCREEN_WIDTH*0.9));
		int x0 = SCREEN_WIDTH/20;
		int x1 = SCREEN_WIDTH/20*19;
		int y0 = 50;
		int y1 = y0+10;
		
		int x0temp = SCREEN_WIDTH/20;
		int x1temp = SCREEN_WIDTH/20;
		
		Canvas canvas = new Canvas(chromeMap);
		Paint pen = new Paint();
		
		pen.setColor(Color.GRAY);
		canvas.drawRect(x0, y0, x1, y1, pen);	//画总序列
		
		for(int i=0; i<ListEndpoint.size(); i++){
			Log.d("Xdebug", ListDescription.get(i));
			if(ListDescription.get(i).equals("INTERGENIC")) {
				pen.setColor(Color.GRAY);
			}else if(ListDescription.get(i).equals("UTR")) {
				pen.setColor(Color.YELLOW);
			}else if(ListDescription.get(i).equals("INTRON")) {
				pen.setColor(Color.RED);
			}else if(ListDescription.get(i).equals("EXON")) {
				pen.setColor(Color.GREEN);
			}
			x1temp = (int) (SCREEN_WIDTH/20 + (ListEndpoint.get(i))/lenUnit);
			if(x1temp > x1) {
				x1temp = x1;
			}
			canvas.drawRect(x0temp, y0, x1temp, y1, pen);	//画总序列
			x0temp = x1temp;
		}
		
		
		pen.setColor(Color.BLACK);
		canvas.drawText(x[1], x0-10, y1+10, pen);
		canvas.drawText(x[3], x1-10, y1+10, pen);
		
		if(selcetPos !=null){
			String y[]=selcetPos.split("[:]");
			onPos = Integer.parseInt(y[1]);		//选中位点

			if(dir.equals("+")) {
				//画选中gRNA
				x0 = (int) (SCREEN_WIDTH/20 + (onPos-startPos)/lenUnit);
				x1 = (int) (x0 + 23/lenUnit);
				y0 = 30;
				y1 = 40;
				pen.setColor(Color.LTGRAY);
				canvas.drawRect(x0, y0, x1, y1, pen);	//画gRNA
				pen.setColor(Color.BLACK);
				canvas.drawRect(x0-1, y0-1, x0+1, y1+1, pen);	//画PAM
			} else if(dir.equals("-")) {
				//画选中gRNA
				x0 = (int) (SCREEN_WIDTH/20 + (onPos-startPos)/lenUnit);
				x1 = (int) (x0 - 23/lenUnit);
				y0 = 30;
				y1 = 40;
				pen.setColor(Color.LTGRAY);
				canvas.drawRect(x0, y0, x1, y1, pen);	//画gRNA
				pen.setColor(Color.BLACK);
				canvas.drawRect(x1-1, y0-1, x1+1, y1+1, pen);	//画PAM
			}
			pen.setColor(Color.BLACK);
			canvas.drawText(dir + y[1], x0-10, y1+10, pen);
		} 
		
		return chromeMap;
	}
	
}
