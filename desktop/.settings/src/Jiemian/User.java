package Jiemian;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class User implements ActionListener,ChangeListener{
	JFrame user=new JFrame();
	
	JLabel name,gene,PAM,locus,Sequese;
	JTextField geneInput,PAMInput,locousInput;
	JTable history,file,fileSon;
	JButton submit,upload,importFile;
	JTextArea sequeseInput;
	static JScrollPane fileT;
	String[] hander={"ID","status"};
	String[] fileshander={"name","note"};
	JFileChooser geneImportW=new JFileChooser();
	JProgressBar uploadresult;
	static File[] geneFile;
	static List<Specie> ff;
	
	static int fileNum ,filestate;
	static String[][] fileNames;
	DefaultTableModel dtm = null;
	
	static String[][] histroy={{"0","1"},{"0","1"},{"0","1"}};
	static String[][] files={{"上传文件","备注"}};
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
		kaisi();
	}
	public void kaisi(){
		
		Font font = new Font(null, 1, 20);
		
		ImageIcon impor=new ImageIcon("image/impor.png");
		name=new JLabel("name :WUTE");
		gene=new JLabel("gene :");
		PAM=new JLabel("PAM :");
		importFile=new JButton("+");
		geneInput=new JTextField(80);
		PAMInput=new JTextField(80);
		locus=new JLabel("Locus :");
		locousInput=new JTextField(80);
		history=new JTable(histroy,hander);
		file=new JTable(files,fileshander);
		fileSon=new JTable(files,fileshander);
		upload=new JButton("upload");
		submit=new JButton("submit");
		Sequese=new JLabel("Sequese :");
		sequeseInput=new JTextArea();
		uploadresult=new JProgressBar();
		uploadresult.setMaximum(100);
		uploadresult.setMinimum(0);
		JScrollPane historyT = new JScrollPane (history);
		fileT = new JScrollPane(file);
		JScrollPane fileSonT = new JScrollPane(fileSon);
		AnalyzeHistory genxin=new AnalyzeHistory();
//		Thread gen=new Thread(genxin);
//		gen.start();
		
		
		user.setLayout(null);
		user.add(name);
		user.add(gene);
		user.add(geneInput);
		user.add(PAM);
		user.add(PAMInput);
		user.add(locus);
		user.add(locousInput);
//		user.add(Sequese);
//		user.add(sequeseInput);
		user.add(historyT);
		user.add(fileT);
		user.add(fileSonT);
		user.add(upload);
		user.add(importFile);
		user.add(submit);
		user.add(uploadresult);
		
		name.setBounds(63,31,150,38);
		name.setFont(font);
		gene.setBounds(350, 31, 100, 38);
//		gene.setFont(font);
		geneInput.setBounds(390,41,80,25);
		PAM.setBounds(495, 31, 100, 38);
		PAMInput.setBounds(530, 41, 80, 25);
//		locus.setBounds(495,31,100,38);
//		locousInput.setBounds(540, 41, 100, 25);
		Sequese.setBounds(350, 80, 100, 38);
//		sequeseInput.setBounds(x, y, width, height);
		historyT.setBounds(66, 77,219, 290);
		fileT.setBounds(350,80, 540, 150);
		fileSonT.setBounds(350, 240, 300,120);
		uploadresult.setBounds(700, 280, 230, 20);
		importFile.setBounds(700, 330, 50, 25);
		upload.setBounds(750, 330, 80, 25);
		submit.setBounds(850,330,80, 25);
		
		
		
		importFile.addActionListener(this);
		upload.addActionListener(this);
		submit.addActionListener(this);
		
		
		
		user.setSize(983,420);
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
				System.out.println(fliesImport.toString());
				new Send().importFile(fliesImport);
				try {
					new Send(5);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public void stateChanged(ChangeEvent e) {
		
		
	}
}
class jingdu implements ActionListener{
	JFrame jingdu=new JFrame();
	public jingdu(){
		jingdu.setSize(100,100);
		jingdu.setTitle("上传角度窗口");
		jingdu.setLocation(400,200);
		jingdu.setResizable(false);
		jingdu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
