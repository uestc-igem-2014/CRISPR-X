package Jiemian;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import Model.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.json.JSONException;
public class Intex extends JFrame implements ActionListener,ItemListener,MouseMotionListener,MouseListener
{
	JTextField inputId;
	JTextField inputPosition;
	JPanel panel1,panel2,panelabout,panelhelp;
	
	static JComboBox<String> germ,PAMbox;
	static JButton submit=null,upLoad,advanced,history;
	JLabel TargetGenome,ID,PAM,logolabel;
	JLabel Position,Sequence,tisiSequence;
	JTextArea inputSequence; 
	JScrollPane gd;
	JFileChooser file=new JFileChooser();
	DynGifLabel shangc;
	File blastWJFile;
	static int[] rfc={0,0,0,0,0,0};
	static String rfcStr="000000";
	static String region="0000";
	static int ntlength;
	static double r1Value;
	StringBuffer blast=new StringBuffer();
	int userstatus=0;
	static List<Specie> ff;
	static JLabel fansubmit,fanhistory,fanhelp,IDseacher,fanabout,user;
	public static void main(String[] args) 
	{
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			
		}
		catch(Exception e){
		}
			new Intex().inter();
	}
	Intex()
	{
		
	}
	public void inter(){

		Font font=new Font("Arial",1,12);
		Font fontcaidan=new Font("MONACO",1,23);
		Font font1=new Font("Arial",1,12);
		
		
		ImageIcon logoimage=new ImageIcon("image/logo.png");
		ImageIcon submitimage=new ImageIcon("image/submit.jpg");
		ImageIcon importimage=new ImageIcon("image/import.jpg");
		ImageIcon advanceimage=new ImageIcon("image/advanced.jpg");
		
		panel1=new JPanel();
		panel2=new JPanel();
		panel2.setBackground(new Color(231,240,226));
		
		logolabel=new JLabel(logoimage);
		TargetGenome=new JLabel("Target Genome");
		TargetGenome.setFont(font1);
		TargetGenome.setForeground(new Color(153,153,153));
		inputSequence=new JTextArea();
		ID=new JLabel("Locus Tag");
		ID.setFont(font1);
		ID.setForeground(new Color(153,153,153));
		Position=new JLabel("Gene");
		Position.setFont(font1);
		Position.setForeground(new Color(153,153,153));
		PAM=new JLabel("PAM");
		PAM.setFont(font1);
		PAM.setForeground(new Color(153,153,153));
		inputPosition=new JTextField("thrA",100);
		inputPosition.setFont(font1);
		inputPosition.setForeground(new Color(153,153,153));
		Sequence=new JLabel("Sequence");
		Sequence.setFont(font);
		Sequence.setForeground(new Color(153,153,153));
		tisiSequence=new JLabel("or  input  in  the  box");
		tisiSequence.setFont(font);
		tisiSequence.setForeground(new Color(153,153,153));
		
		inputId=new JTextField("NC_001134-chromosome2:200..2873",20);
		inputId.setFont(font1);
		inputId.setForeground(new Color(153,153,153));
		gd=new JScrollPane(inputSequence);
		String[] list1={"Saccharomyces-cerevisiae","E.coli-K12-MG1655","Drosophila melanogaster","Caenorhabditis elegans","Arabidopsis thaliana"};
		String[] list2={"NGG","NNNNGMTT","NNAGAAW","NRG"};
		germ=new JComboBox<String>(list1);
		germ.setFont(font1);
		germ.setForeground(new Color(153,153,153));
		PAMbox=new JComboBox<String>(list2);
		PAMbox.setFont(font1);
		PAMbox.setForeground(new Color(153,153,153));
		
		advanced=new JButton(advanceimage);
		submit=new JButton(submitimage);
		submit.setFont(font);
		submit.setForeground(Color.white);
		submit.setBorderPainted(false);
		
		user=new JLabel("LOG IN",JLabel.CENTER);
		user.setFont(fontcaidan);
		user.setOpaque(true);
		user.setBackground(Color.lightGray);
		user.setForeground(new Color(143,196,31));
		fansubmit=new JLabel("SUBMIT",JLabel.CENTER);
		fansubmit.setFont(fontcaidan);
		fansubmit.setOpaque(true);
		fansubmit.setBackground(new Color(46,48,45));
		fansubmit.setForeground(new Color(143,196,31));
		fanhistory=new JLabel("MY LAB",JLabel.CENTER);
		fanhistory.setFont(fontcaidan);
		fanhistory.setOpaque(true);
		fanhistory.setBackground(new Color(231,240,226));
		fanhistory.setForeground(new Color(143,196,31));
		fanhelp=new JLabel("HELP",JLabel.CENTER);
		fanhelp.setFont(fontcaidan);
		fanhelp.setOpaque(true);
		fanhelp.setBackground(new Color(231,240,226));
		fanhelp.setForeground(new Color(143,196,31));
		IDseacher=new JLabel("IDSEACHER",JLabel.CENTER);
		IDseacher.setFont(fontcaidan);
		IDseacher.setOpaque(true);
		IDseacher.setBackground(new Color(231,240,226));
		IDseacher.setForeground(new Color(143,196,31));
		fanabout=new JLabel("ABOUT",JLabel.CENTER);
		fanabout.setFont(fontcaidan);
		fanabout.setOpaque(true);
		fanabout.setBackground(new Color(231,240,226));
		fanabout.setForeground(new Color(143,196,31));
		
		
		
		upLoad=new JButton(importimage);
		upLoad.setFont(font);
		upLoad.setForeground(Color.white);
		shangc = new DynGifLabel(new ImageIcon("image/import.gif").getImage());
		history=new JButton("history");
//		submit.setBorder(null);
//		submit.setOpaque(false);
//		submit.setContentAreaFilled(false);//透明效果
		
		
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
		panel1.setLayout(null);
		panel2.setLayout(null);
		
		this.setLayout(null);//设置布局
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		panel1.add(ID);
		panel1.add(inputId);
		panel1.add(Position);
		panel1.add(inputPosition);
		panel1.add(TargetGenome);
		panel1.add(germ);
		panel1.add(PAM);
		panel1.add(PAMbox);
		panel1.add(Sequence);
		panel1.add(gd);
		panel1.add(submit);
		panel1.add(upLoad);
		panel1.add(advanced);
		panel1.add(shangc);
		panel1.add(history);
		panel1.add(tisiSequence);
		submit.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		upLoad.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
//		advanced.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		
		panel2.add(logolabel);
		panel2.add(user);
		panel2.add(fansubmit);
		panel2.add(fanhistory);
		panel2.add(IDseacher);
		panel2.add(fanhelp);
		panel2.add(fanabout);
		
		
		panel1.setBounds(200,0,908,662);
		this.getContentPane().add(panel1);
		panel2.setBounds(0, 0, 200, 662);
		this.getContentPane().add(panel2);
		
		logolabel.setBounds(0, 0, 200, 70);
		user.setBounds(0, 140, 200, 40);
		fansubmit.setBounds(0, 220, 200,60);
		fanhistory.setBounds(0, 280, 200, 60);
		IDseacher.setBounds(0, 340, 200, 60);
		fanhelp.setBounds(0, 400, 200, 60);
		fanabout.setBounds(0, 460, 200, 60);
		TargetGenome.setBounds(45, 30,93, 23);
		germ.setBounds(165, 30,250, 25);//germ的位置
		inputId.setBounds(165, 100, 250, 25);//输入ID框位置
		ID.setBounds(45,100,60,23);//ID的位置
		Position.setBounds(45, 65,93, 23);//position位置
		inputPosition.setBounds(165, 65, 250, 25);//inputPosition位置
		PAM.setBounds(45, 135, 30, 23);
		PAMbox.setBounds(165, 135,250,25);
		Sequence.setBounds(45, 175, 90, 23);
		gd.setBounds(165, 210, 400,250);
		submit.setBounds(480, 480,85,30);
		upLoad.setBounds(165, 170,85,30);
		tisiSequence.setBounds(265, 177, 120, 30);
//		shangc.setBounds(230,460, 80, 80);
		advanced.setBounds(165, 480,90,30);
		
//		history.setBounds(600,550,70,30);
		history.setVisible(false);
//		this.setUndecorated(true);
		
		 submit.addActionListener(this);
		 advanced.addActionListener(this);
		 upLoad.addActionListener(this);
		 history.addActionListener(this);
		 germ.addItemListener(this);
		 
		 user.addMouseMotionListener(this);
		 user.addMouseListener(this);
		 fansubmit.addMouseMotionListener(this);
		 fansubmit.addMouseListener(this);
		 fanhistory.addMouseMotionListener(this);
		 fanhistory.addMouseListener(this);
		 IDseacher.addMouseMotionListener(this);
		 IDseacher.addMouseListener(this);
		 fanhelp.addMouseMotionListener(this);
		 fanhelp.addMouseListener(this);
		 fanabout.addMouseMotionListener(this);
		 fanabout.addMouseListener(this);
		 
		 this.setUndecorated(true); 
//		 AWTUtilities.setWindowOpaque(this,false);
//		 this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		 
		 this.setSize(908, 662);
			this.setTitle("");
			 this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
			this.setLocation(250,60);
			 this.setResizable(false);
			 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 this.setVisible(true);
		 
	}
	public Intex(int[] rfc, int r1Value,int[] reg,int ntlength) {
		this.rfc=rfc;
		this.r1Value=r1Value;
		this.rfcStr=String.valueOf(rfc[0])+String.valueOf(rfc[1])+String.valueOf(rfc[2])+String.valueOf(rfc[3])+String.valueOf(rfc[4])+String.valueOf(rfc[5]);
		this.region=String.valueOf(reg[0])+String.valueOf(reg[1])+String.valueOf(reg[2])+String.valueOf(reg[3]);
		this.ntlength=ntlength;
	}
	public  Intex(int status) throws JSONException {
		this.userstatus=status;
		if(status==1){
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
				Send send=new Send(TargetGenomeValue, PAMValue, IDValue, PositionValue,rfcStr,region,ntlength,r1Value/100);
				System.out.println(r1Value);
				Thread sendkais=new Thread(send);
				sendkais.start();
			}else{
				javax.swing.JOptionPane.showMessageDialog(null,"Do you have a parameter is not filled！");
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
		final List list=new ArrayList();
		list.add("fna");
		list.add("xml");	
		file.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
               if(f.isDirectory())return true;
               String name=f.getName();
               int p=name.lastIndexOf('.');
               if(p==-1)return false;
               String suffix=name.substring(p+1).toLowerCase();
               return list.contains(suffix);
            }
            public String getDescription() {
                return "gene files";
            }
});
		file.showOpenDialog(this);
		blastWJFile=file.getSelectedFile();
		String houzuiming=blastWJFile.getName().substring(blastWJFile.getName().lastIndexOf(".")+1);
		System.out.println(houzuiming);
		if(houzuiming.equals("xml")){
			List<HashMap<String, String>> jieguo=new ToolXmlBySAX().jiexisbol(blastWJFile);
			HashMap<String, String>kk=jieguo.get(0);
			String kc=kk.get("s:nucleotides");
			blast.append(kc);
		}else{
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
	@Override
	public void mouseDragged(MouseEvent e) {	
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getSource()==user){
			user.setBackground(Color.GRAY);
			
		}
		if(e.getSource()==fansubmit){
			fansubmit.setBackground(new Color(46,48,45));
			
		}
		if(e.getSource()==fanhistory){
			fanhistory.setBackground(new Color(46,48,45));
			
		}
		if(e.getSource()==IDseacher){
			IDseacher.setBackground(new Color(46,48,45));
	
		}
		if(e.getSource()==fanhelp){
			fanhelp.setBackground(new Color(46,48,45));
		}
		if(e.getSource()==fanabout){
			fanabout.setBackground(new Color(46,48,45));
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==user){
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			denlu denlu=new denlu();
			Thread denluxc=new Thread(denlu);
			denluxc.start();
			System.out.print(denlu.nameStr);
		}
		if(e.getSource()==fansubmit){
//			fansubmit.setBackground(Color.black);
		}
		if(e.getSource()==fanhistory){
			if(true){
				try {
					new Send(6);
					new Send(2);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}	
		}
		if(e.getSource()==IDseacher){
			new IDseacher().idSeacherWindow();
		}
		if(e.getSource()==fanhelp){
			
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		user.setBackground(new Color(231,240,226));
//		fansubmit.setBackground(new Color(231,240,226));
		fanhistory.setBackground(new Color(231,240,226));
		IDseacher.setBackground(new Color(231,240,226));
		fanhelp.setBackground(new Color(231,240,226));
		fanabout.setBackground(new Color(231,240,226));
		
	}
}