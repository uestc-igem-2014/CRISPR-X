package Jiemian;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.AnalyzeM;
import Model.Getimage;
import Model.MoreIfozhi;

public class MoreInfo extends JFrame{
	static JTable moreinfo;
	static JPanel mb1;
	static AnalyzeM analyzeM;
	static JScrollPane kkc;
	static List<MoreIfozhi> MoreInfo;
	static int GC;
	static String RNAfold;
	static String[][] xiansi;
	static String[] xiansihander={"start","end","myhistory","strand","sequece"};
	static BufferedImage image;
	static JLabel imagespace; 
	int WIDTH=150;
	int HEIGHT=150;
	MoreInfo(AnalyzeM analyzeM){
		this.analyzeM=analyzeM;
		MoreInfo=analyzeM.MoreInfo;
		GC=analyzeM.getGC();
		RNAfold=analyzeM.getRNAfold();
		new MoreInfo();
	}
	public MoreInfo(){
		xiansi=new String[MoreInfo.size()][5];
		for(int i=0;i<MoreInfo.size();i++){
			xiansi[i][0]=MoreInfo.get(i).getStart()+"";
			xiansi[i][1]=MoreInfo.get(i).getEnd()+"";
			xiansi[i][2]=MoreInfo.get(i).getEnzyme_name();
			xiansi[i][3]=MoreInfo.get(i).getStrand();
			xiansi[i][4]=MoreInfo.get(i).getMatched_seq();
		}
		DefaultTableModel tableModel = new DefaultTableModel(xiansi,xiansihander){ public boolean isCellEditable(int row, int column) { return false; }};
		moreinfo=new JTable(tableModel);
		kkc = new JScrollPane (moreinfo);
		try {
			image=new Getimage().image();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ImageIcon logoimage=new ImageIcon(image);
		logoimage.setImage(logoimage.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_DEFAULT));
		imagespace=new JLabel(logoimage);
		mb1=new JPanel(null);
		mb1.setBackground(Color.white);
		
		
		mb1.add(kkc);
		mb1.add(imagespace);
		
		
		kkc.setBounds(200,0, 400, 400);
		imagespace.setBounds(0, 0, 200, 300);
		mb1.setBounds(0, 0, 600, 400);
		
		this.add(mb1);
		
		this.setTitle("结果窗口");
		this.setSize(600,400);
		 this.setLocation(300,155);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 this.setVisible(true);
	}
}
