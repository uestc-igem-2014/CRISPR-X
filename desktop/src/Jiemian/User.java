package Jiemian;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import net.sf.json.JSONObject;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONException;

import Model.AnalyzeHistory;
import Model.BulidJson;
import Model.Send;
import Model.Specie;

public class User implements ActionListener,MouseListener,MouseMotionListener{
	JFrame user=new JFrame();
	
	JPanel Mysp,usermb;
	JLabel myhistory,myspiecies,addnew,gene,PAM,locus,Sequese;
	JLabel logo,mylab,about,help,username;
	JTextField geneInput,PAMInput,locousInput;
	JTable history,file,fileSon;
	JButton submit,upload,importFile;
	JTextArea sequeseInput;
	static JScrollPane fileT;
	String[] hander={"ID","status"};
	String[] fileshander={"myhistory","note"};
	JFileChooser geneImportW=new JFileChooser();
//	JProgressBar uploadresult;
	static File[] geneFile;
	static List<Specie> ff;
	static int fileNum ,filestate;
	static String[][] fileNames;
	DefaultTableModel dtm = null;
	
	static String[][] histroy={{"0","1"},{"0","1"},{"0","1"}};
	static String[][] files={{"上传文件","备注"}};
	static String[][] files1={{"上传文件","备注"}};
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new User();
	}
	public User(String[][] history){
		this.histroy=history;
	}
	public User(String[][] history,String[][] ff){
		this.histroy=history;
		this.files=ff;
	}
	
//public User() {
//	
//	}
	public User(){
		System.out.print(files[0][0]);
		kaisi();
	}
	public void kaisi(){
		
		ImageIcon logoimage=new ImageIcon("image/logo.png");
		ImageIcon uploadimage=new ImageIcon("image/Upload.jpg");
		ImageIcon addimage=new ImageIcon("image/Add.jpg");
		ImageIcon submitimage=new ImageIcon("image/submit.jpg");
		Font font = new Font(null, 1, 20);
		Font font1 = new Font("Arial", 1, 15);
		Font fontcaidan=new Font("MONACO",1,23);
		
		Mysp=new JPanel(null);
		Mysp.setBackground(new Color(231,240,226));
		usermb=new JPanel(null);
//		Mysp.setBackground(Color.black);
		ImageIcon impor=new ImageIcon("image/impor.png");
		myhistory=new JLabel("My Submition History:");
		myspiecies=new JLabel("My spiecies:");
		addnew=new JLabel("Add new spiecies:");
		gene=new JLabel("gene :");
		PAM=new JLabel("PAM :");
		importFile=new JButton(addimage);
		geneInput=new JTextField(80);
		PAMInput=new JTextField(80);
		locus=new JLabel("Locus :");
		locousInput=new JTextField(80);
		history=new JTable(histroy,hander);
		file=new JTable(files1,fileshander);
		fileSon=new JTable(files,fileshander);
		upload=new JButton(uploadimage);
		submit=new JButton(submitimage);
		Sequese=new JLabel("Sequese :");
		sequeseInput=new JTextArea();
//		uploadresult=new JProgressBar();
//		uploadresult.setMaximum(100);
//		uploadresult.setMinimum(0);
		JScrollPane historyT = new JScrollPane (history);
		fileT = new JScrollPane(file);
		JScrollPane fileSPT = new JScrollPane(fileSon);
		AnalyzeHistory genxin=new AnalyzeHistory();
//		Thread gen=new Thread(genxin);
//		gen.start();
		
		
		logo=new JLabel(logoimage);
		
//		mylab.setForeground(new Color(143,196,31));
		mylab=new JLabel("MY LAB",JLabel.CENTER);
		mylab.setFont(fontcaidan);
		mylab.setOpaque(true);
		mylab.setBackground(new Color(231,240,226));
		mylab.setForeground(new Color(143,196,31));
		about=new JLabel("HELP",JLabel.CENTER);
		about.setFont(fontcaidan);
		about.setOpaque(true);
		about.setBackground(new Color(231,240,226));
		about.setForeground(new Color(143,196,31));
		help=new JLabel("HELP",JLabel.CENTER);
		help.setFont(fontcaidan);
		help.setOpaque(true);
		help.setBackground(new Color(231,240,226));
		help.setForeground(new Color(143,196,31));
		username=new JLabel("LOGOUT",JLabel.CENTER);
		username.setFont(fontcaidan);
		username.setOpaque(true);
		username.setBackground(new Color(231,240,226));
		username.setForeground(new Color(143,196,31));
		
		user.getContentPane().add(usermb);
		user.getContentPane().add(Mysp);
		
		Mysp.add(logo);
		Mysp.add(mylab);
		Mysp.add(about);
		Mysp.add(help);
		Mysp.add(username);
		
		usermb.setLayout(null);
		usermb.add(myhistory);
		usermb.add(myspiecies);
		usermb.add(addnew);
		usermb.add(gene);
		usermb.add(geneInput);
		usermb.add(PAM);
		usermb.add(PAMInput);
		usermb.add(locus);
		usermb.add(locousInput);
//		user.add(Sequese);
//		user.add(sequeseInput);
		usermb.add(historyT);
		usermb.add(fileT);
		usermb.add(fileSPT);
		usermb.add(upload);
		usermb.add(importFile);
		usermb.add(submit);
//		user.add(uploadresult);
		
		
		
		logo.setBounds(0, 0, 200, 70);
		mylab.setBounds(380,0,100, 70);
		about.setBounds(480,0, 100, 70);
		help.setBounds(580,0, 100, 70);
		username.setBounds(680,0, 100, 70);
		myhistory.setBounds(20,21,200,38);
		myhistory.setFont(font1);
		myhistory.setForeground(new Color(153,153,153));
		myspiecies.setBounds(250,21,200,38);
		myspiecies.setFont(font1);
		myspiecies.setForeground(new Color(153,153,153));
		addnew.setBounds(250,200,200,38);
		addnew.setFont(font1);
		addnew.setForeground(new Color(153,153,153));
		gene.setBounds(250, 230, 100, 38);
//		gene.setFont(font);
		geneInput.setBounds(300,240,170,25);
		PAM.setBounds(550, 230, 100, 38);
		PAMInput.setBounds(600, 240,170, 25);
//		locus.setBounds(495,31,100,38);
//		locousInput.setBounds(540, 41, 100, 25);
		Sequese.setBounds(350, 80, 100, 38);
//		sequeseInput.setBounds(x, y, width, height);
		historyT.setBounds(20,50,219,350);
		fileT.setBounds(250, 270, 520,120);
		fileSPT.setBounds(250,50,520, 150);
//		uploadresult.setBounds(700, 280, 230, 20);
		importFile.setBounds(250, 400, 33, 25);
		upload.setBounds(670, 400,100, 25);
		submit.setBounds(690, 400, 80, 25);
		submit.setVisible(false);
		
		Mysp.setBounds(0, 0, 983, 70);
		usermb.setBounds(0,70, 983, 480);
		
		importFile.addActionListener(this);
		upload.addActionListener(this);
		submit.addActionListener(this);
		mylab.addMouseMotionListener(this);
		mylab.addMouseListener(this);
		about.addMouseMotionListener(this);
		about.addMouseListener(this);
		help.addMouseMotionListener(this);
		help.addMouseListener(this);
		username.addMouseMotionListener(this);
		username.addMouseListener(this);
		 
		
		
		user.setSize(800,550);
		user.setTitle("用户窗口");
		 user.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		user.setLocation(300,120);
		 user.setResizable(false);
		 user.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 user.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==importFile){
			/*文件过滤器*/
			final List list=new ArrayList();
			list.add("fna");
			geneImportW.setFileFilter(new javax.swing.filechooser.FileFilter() {
	            public boolean accept(File f) {
	               if(f.isDirectory())return true;
	               String name=f.getName();
	               int p=name.lastIndexOf('.');
	               if(p==-1)return false;
	               String suffix=name.substring(p+1).toLowerCase();
	               return list.contains(suffix);
	            }

	            public String getDescription() {
	                return "fna files";
	            }
	});
		/*文件过滤器*/
			geneImportW.setMultiSelectionEnabled(true);
			geneImportW.showSaveDialog(user);
			geneFile=geneImportW.getSelectedFiles();			
			fileNum=geneFile.length;
//			JTable filName=new JTable();
			fileNames=new String[fileNum][2];
			for(int i=0;i<fileNum;i++){
				String fileName=geneImportW.getName(geneFile[i]);
//				System.out.println(fileName);
				fileNames[i][0]=fileName;
				dtm=new DefaultTableModel(fileNames,fileshander);
				file=new JTable(dtm);
//				TableColumn kk=new TableColumn();
//				file.add(submit);
				fileT.setViewportView(file);
			}
			
		}
		if(e.getSource()==upload){
			if(fileNum!=0){
				for(int i=0;i<fileNum;i++){
					fileNames[i][1]=(String) dtm.getValueAt(i,1);
					new Send().uploadFile(geneFile[i],fileNames[i][1],fileNames[i][0].substring(0,fileNames[i][0].lastIndexOf(".")-1));
					try {
					new Send(4);
					filestate=1;
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			else{
				javax.swing.JOptionPane.showMessageDialog(null,"Files is null");
			}
			submit.setVisible(true);
		}
		if(e.getSource()==submit){
//			String[] kc=fileNames[1][0];		
			String genename=geneInput.getText();
			String PAM=PAMInput.getText();
			if(PAM.isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null,"PAM is null");
			}else{
				new BulidJson(fileNames,PAM,fileNum,genename);
				JSONObject fliesImport=new BulidJson().Json();
				new Send().importFile(fliesImport);
				try {
					new Send(5);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getSource()==mylab){
			mylab.setBackground(new Color(46,48,45));
			
		}
		if(e.getSource()==about){
			about.setBackground(new Color(46,48,45));
			
		}
		if(e.getSource()==help){
			help.setBackground(new Color(46,48,45));
			
		}
		if(e.getSource()==username){
			username.setBackground(new Color(46,48,45));
	
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==username){
			 int rv = JOptionPane.showConfirmDialog(null, "Cancellation of your account", "inform",
		                JOptionPane.YES_NO_OPTION);
			  if (rv == JOptionPane.YES_OPTION) {
				  System.out.println("sadfas");
				  new Send().key=null;
				  histroy=null;
				  files=null;
				  user.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				  user.setVisible(false);
		        } else if (rv == JOptionPane.NO_OPTION) {
		            
		        }
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
		mylab.setBackground(new Color(231,240,226));
		about.setBackground(new Color(231,240,226));
		help.setBackground(new Color(231,240,226));
		username.setBackground(new Color(231,240,226));
		
	}
}
