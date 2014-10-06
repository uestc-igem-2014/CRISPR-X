package Jiemian;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import Model.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.json.JSONException;
public class Intex extends JFrame implements ActionListener,ItemListener
{
	JTextField inputId;
	JTextField inputPosition;
	static JComboBox<String> germ,PAMbox;
	static JButton submit=null,upLoad,advanced,login,history;
	JLabel TargetGenome,ID,PAM;
	JLabel Position,Sequence;
	JTextArea inputSequence; 
	JScrollPane gd;
	JFileChooser file=new JFileChooser();
	DynGifLabel shangc;
	File blastWJFile;
	static int[] rfc={0,0,0,0,0,0};
	static String rfcStr="000000";
	int r1Value;
	StringBuffer blast=new StringBuffer();
	int userstatus=0;
	static List<Specie> ff;
	public static void main(String[] args) 
	{
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
			Intex lx=new Intex();
//		  try {
//			   UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//	            Intex lx=new Intex();
//			  } catch (Exception e) {
//			   e.printStackTrace();
//			  }
//
	}
	Intex()
	{
		
		ImageIcon sub=new ImageIcon("image/submit1.png");
		ImageIcon adv=new ImageIcon("image/advanced.png");
		ImageIcon log=new ImageIcon("image/login.png");
		ImageIcon up = new ImageIcon("image/import1.png");
		
		TargetGenome=new JLabel("Target Genome");
		inputSequence=new JTextArea();
		ID=new JLabel("Locus Tag");
		Position=new JLabel("gene");
		PAM=new JLabel("PAM");
		inputPosition=new JTextField("thrA",100);
		Sequence=new JLabel("Sequence");
		inputId=new JTextField("NC_001134-chromosome2:200..2873",20);
		gd=new JScrollPane(inputSequence);
		String[] list1={"Saccharomyces-cerevisiae","E.coli-K12-MG1655","Drosophila melanogaster","Caenorhabditis elegans","Arabidopsis thaliana"};
		String[] list2={"NGG","NNNNGMTT","NNAGAAW","NRG"};
		germ=new JComboBox(list1);
		PAMbox=new JComboBox(list2);
		
		advanced=new JButton(adv);
		submit=new JButton(sub);
		upLoad=new JButton(up);
		shangc = new DynGifLabel(new ImageIcon("image/import.gif").getImage());
		history=new JButton("history");
//		upLoad.setBorder(null);
//		upLoad.setOpaque(false);
//		upLoad.setContentAreaFilled(false);//透明效果
		login=new JButton(log);
		
		
		ImageIcon background = new ImageIcon("image/interBackimage.png");// 背景图片
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
		germ.setBounds(145, 30,190, 25);//germ的位置
		inputId.setBounds(145, 105, 250, 25);//输入ID框位置
		ID.setBounds(45,105,60,23);//ID的位置
		Position.setBounds(45, 65,50, 23);//position位置
		inputPosition.setBounds(145, 65, 250, 25);//inputPosition位置
		PAM.setBounds(45, 140, 30, 23);
		PAMbox.setBounds(145, 140,90,25);
		Sequence.setBounds(45, 175, 60, 23);
		gd.setBounds(145, 175, 400,250);
		submit.setBounds(500, 460, 83,46);
		upLoad.setBounds(390, 460,83,50);
//		shangc.setBounds(230,460, 80, 80);
		advanced.setBounds(720, 320,160,60);
		login.setBounds(600, 460, 100, 70);
		history.setBounds(600,550,70,30);
		history.setVisible(false);
//		this.setUndecorated(true);
		
		 submit.addActionListener(this);
		 advanced.addActionListener(this);
		 upLoad.addActionListener(this);
		 login.addActionListener(this);
		 history.addActionListener(this);
		 germ.addItemListener(this);
		 
		 this.setSize(908, 662);
			this.setTitle("用户窗口");
			 this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
			this.setLocation(300,120);
			 this.setResizable(false);
			 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 this.setVisible(true);
		 
	}
	public Intex(int[] rfc, int r1Value) {
		Intex.rfc=rfc;
		this.r1Value=r1Value;
		Intex.rfcStr=String.valueOf(rfc[0])+String.valueOf(rfc[1])+String.valueOf(rfc[2])+String.valueOf(rfc[3])+String.valueOf(rfc[4])+String.valueOf(rfc[5]);
	}
	public  Intex(int status) throws JSONException {
		this.userstatus=status;
		if(status==1){
			login.setVisible(false);
			history.setVisible(true);
		}
		new Send(7);
		ff=new AnalyzeUserspce().AnalyzeUserspce();
		int length=ff.size();
		System.out.println(length);
		germ.removeAllItems();
		for(int i=0;i<length;i++){
			germ.addItem(ff.get(i).getSpecieName());
		}
	}
	public Intex(String biaoji){
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit){
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
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
			try {
				read();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==login){
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			denlu denlu=new denlu();
			Thread denluxc=new Thread(denlu);
			denluxc.start();
			
		}
		if(e.getSource()==history){
			try {
				new Send(6);
				new Send(2);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}	
	}
	private void read() throws FileNotFoundException, IOException {
		file.showOpenDialog(this);
		blastWJFile=file.getSelectedFile();
		FileInputStream bb=new FileInputStream(blastWJFile);
		int aa;
		aa = bb.read();  
		while (aa!=(-1))   
		{
			aa = bb.read();
			blast.append((char)aa);
		}
		bb.close();
		inputSequence.append(blast.toString());
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			String TargetGenomeValue=(String)germ.getSelectedItem();
			if(TargetGenomeValue.equals("Saccharomyces-cerevisiae")||TargetGenomeValue.equals("E.coli-K12-MG1655")||
				TargetGenomeValue.equals("Drosophila melanogaster")||TargetGenomeValue.equals("Caenorhabditis elegans")||
				TargetGenomeValue.equals("Arabidopsis thaliana")){
				PAMbox.removeAllItems();
				PAMbox.addItem("NGG");
				PAMbox.addItem("NNNNGMTT");
				PAMbox.addItem("NNAGAAW");
				PAMbox.addItem("NRG");
			}else{
				PAMbox.removeAllItems();
				int length=ff.size();
				String specieName=(String) germ.getSelectedItem();
				for(int i=0;i<length;i++){
					String[] pams=ff.get(i).getPamsOther(specieName);
					if(pams!=null){					
						int pamslength=pams.length;
						for(int j=0;j<pamslength;j++){
//							System.out.print(pams[j]);
							PAMbox.addItem(pams[j]);
						}
					}					
				}
				
			}
		}
		
	}
}