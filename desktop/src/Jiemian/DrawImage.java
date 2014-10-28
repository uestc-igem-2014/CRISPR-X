package Jiemian;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawImage extends JFrame{
	String[] strand;
	String[] location,position;
	int[] positionNum;
	String minStr;
	String maxStr,positionStr;
	int minNum,maxNum,count;
	public DrawImage(String[] position,String location,String[] strand,int count){
		this.count=count;
		this.strand=strand;
		this.location=location.split(":",2);
		this.positionNum=new int[count];
		for(int i=0;i<count;i++){
			this.position=position[i].split(":");
			this.positionNum[i]=Integer.parseInt(this.position[1]);
		}
		String ss=this.location[1];
		String[] ks=ss.split("\\..");
		this.minStr=ks[0];
		this.maxStr=ks[1];

		this.strand=strand;
		DrawImage();
	}
	void DrawImage(){
		minNum=Integer.parseInt(minStr);
		maxNum=Integer.parseInt(maxStr);
//		MapImage mapImage=new MapImage(positionNum,minNum,maxNum,count,strand);
//		this.add(mapImage);
		
	}
	int getminNum(){
		return minNum;
	}
	int getmaxNum(){
		return maxNum;
	}
}
class MapImage extends JPanel{
	float position;
	float[] site;
	int length=1000;
	String[] strand;
	int min,max;
	int[] high1={70,78,86,92};
	int[] high2={52,44,36,28};
	int count,x,y;
	public MapImage(int[] positionNum,int minNum,int maxNum,int count,String[] strand) {
		this.strand=strand;
		this.length=maxNum-minNum;
		this.count=count;
//		System.out.println(strand[1]);
		this.site=new float[count];
		for(int i=0;i<count;i++){
			this.site[i]=(((float)positionNum[i]-(float)minNum)/((float)maxNum-(float)minNum));
//			this.position=positionNum;
		}

	}
	public MapImage() {
	}
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(50,65,400,3);
//		int weiz=(int)(400*site[1])+50;
		for(int i=0;i<count;i++){
			int wei=(int)(400*site[i])+50;
//			System.out.println(strand[i]);
//			for(int j=0;j<count;j++){
			if(strand[i].equals("+")){
				g.setColor(Color.black);
				g.fillRect(wei, high1[(int)(i/10)], 2, 8);
//				System.out.println(wei);
				g.setColor(Color.green);
				g.fill3DRect(wei+2,high1[(int)(i/10)]+2,12,4, true);
			}else{
//				System.out.println(strand[i]);
				g.setColor(Color.black);
				g.fillRect(wei, high2[(int)(i/10)], 2, 8);
				g.setColor(Color.green);
				g.fill3DRect(wei+2,high2[(int)(i/10)]+2,12,4, true);
			}
//			}
		}

//		g.fillRect(weiz, 100, 2, 8);
//		g.setColor(Color.green);
//		g.fill3DRect(weiz+2,102,12,4, true);
	}
}