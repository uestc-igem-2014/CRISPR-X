package Jiemian;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

import  com.jtattoo.*;

import Model.*;

import org.jb2011.lnf.*;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI;
import org.json.JSONException;
public class Inter extends JFrame implements ActionListener
{
	JTextField inputId;
	JTextField inputPosition;
	JComboBox germ,PAMbox;
	JButton submit=null,upLoad,advanced,login,history;
	JLabel TargetGenome,ID,PAM;
	JLabel Position,Sequence;
	JTextArea inputSequence; 
	JScrollPane gd;
	DynGifLabel shangc;
	static int[] rfc={0,0,0,0,0,0};
	static String rfcStr="000000";
	int r1Value;
	public static void main(String[] args) 
	{
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
			Inter lx=new Inter();
//		  try {
//			   UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//	            Inter lx=new Inter();
//			  } catch (Exception e) {
//			   e.printStackTrace();
//			  }
//
	}
	Inter()
	{
		
		ImageIcon sub=new ImageIcon("image/submit.png");
		ImageIcon adv=new ImageIcon("image/advanced.png");
		ImageIcon log=new ImageIcon("image/login.png");
		
		TargetGenome=new JLabel("Target Genome");
		inputSequence=new JTextArea();
		ID=new JLabel("Locus Tag");
		Position=new JLabel("gene");
		PAM=new JLabel("PAM");
		inputPosition=new JTextField("thrA",100);
		Sequence=new JLabel("Sequence");
		inputId=new JTextField("NC_001134-chromosome2:200..2873",20);
		gd=new JScrollPane(inputSequence);
		String[] list1={"Saccharomyces-cerevisiae","E.coil"};
		String[] list2={"NGG","NNNNGMTT","NNAGAAW","NRG"};
		germ=new JComboBox(list1);
		PAMbox=new JComboBox(list2);
		login=new JButton();
		
		advanced=new JButton(adv);
		submit=new JButton(sub);
		upLoad=new JButton("import");
		shangc = new DynGifLabel(new ImageIcon("image/import.gif").getImage());
		history=new JButton("history");
//		upLoad.setBorder(null);
//		upLoad.setOpaque(false);
//		upLoad.setContentAreaFilled(false);//透明效果
		login=new JButton(log);
		
		
		ImageIcon background = new ImageIcon("image/interBack.png");// 背景图片
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, background.getIconWidth(),
			    background.getIconHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// 内容窗格默认的布局管理器为BorderLayout
		imagePanel.setLayout(new FlowLayout());
		imagePanel.add(ID);
		this.setLayout(null);//设置布局
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.getContentPane().add(ID);
		this.getContentPane().add(inputId);
		this.getContentPane().add(Position);
		this.getContentPane().add(inputPosition);
		this.getContentPane().add(TargetGenome);
		this.getContentPane().add(germ);
		this.getContentPane().add(PAM);
		this.getContentPane().add(PAMbox);
		this.getContentPane().add(Sequence);
		this.getContentPane().add(gd);
		this.getContentPane().add(submit);
		this.getContentPane().add(upLoad);
		this.getContentPane().add(advanced);
		this.getContentPane().add(login);
		this.getContentPane().add(shangc);
		this.getContentPane().add(history);
		submit.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
//		advanced.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		
		
		TargetGenome.setBounds(45, 30,87, 23);
		germ.setBounds(145, 30,120, 25);//germ的位置
		inputId.setBounds(145, 105, 250, 25);//输入ID框位置
		ID.setBounds(45,105,60,23);//ID的位置
		Position.setBounds(45, 65,50, 23);//position位置
		inputPosition.setBounds(145, 65, 250, 25);//inputPosition位置
		PAM.setBounds(45, 140, 30, 23);
		PAMbox.setBounds(145, 140,90,25);
		Sequence.setBounds(45, 175, 60, 23);
		gd.setBounds(145, 175, 400,250);
		submit.setBounds(310, 460, 100,100);
//		upLoad.setBounds(145, 460, 70, 30);
		shangc.setBounds(230,460, 80, 80);
		advanced.setBounds(720, 320,160,60);
		login.setBounds(600, 460, 100, 70);
		history.setBounds(600,550,70,30);
//		this.setUndecorated(true);
		
		 submit.addActionListener(this);
		 advanced.addActionListener(this);
		 upLoad.addActionListener(this);
		 login.addActionListener(this);
		 history.addActionListener(this);
		 
		 this.setSize(908, 662);
			this.setTitle("用户窗口");
			 this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
			this.setLocation(300,120);
			 this.setResizable(false);
			 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 this.setVisible(true);
		 
	}
	public Inter(int[] rfc, int r1Value) {
		Inter.rfc=rfc;
		this.r1Value=r1Value;
		Inter.rfcStr=String.valueOf(rfc[0])+String.valueOf(rfc[1])+String.valueOf(rfc[2])+String.valueOf(rfc[3])+String.valueOf(rfc[4])+String.valueOf(rfc[5]);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit){
			String TargetGenomeValue=(String)germ.getSelectedItem();
			String PAMValue=(String)PAMbox.getSelectedItem();
			String IDValue=(String)inputId.getText();
			String PositionValue=(String)inputPosition.getText();
//			System.out.print(rfcStr);
			if(PositionValue.trim().length() != 0||IDValue.trim().length() != 0){
//				System.out.println(PositionValue+IDValue);
				Send send=new Send(TargetGenomeValue, PAMValue, IDValue, PositionValue,rfcStr);
				Thread sendkais=new Thread(send);
				sendkais.start();
			}else{
				javax.swing.JOptionPane.showMessageDialog(null,"您有参数未填！！！");
			}
		}
		if(e.getSource()==advanced){
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			new Advanced();
		}
		if(e.getSource()==upLoad){
//			System.out.println("123123");
			FileReader wj = null;
//			try {
//				wj = new FileReader("");
//				int aa = 0;
//				StringBuffer value=new StringBuffer();
//				aa = wj.read();
//				while (aa!=(-1)) 
//				{
//					value.append((char)aa);
//					wj.close();
//				}
//				String varlueStr=value.toString();
//				System.out.println(varlueStr);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
		}
		if(e.getSource()==login){
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			denlu denlu=new denlu();
			Thread denluxc=new Thread(denlu);
			denluxc.start();
			String key=new denlu().key;
//			if(key.toString().equals("-")){				
//			}else{
//			}
		}
		if(e.getSource()==history){
			System.out.println("123");
			try {
				new Send(2);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}
}