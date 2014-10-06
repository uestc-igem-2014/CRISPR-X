package Jiemian;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class User {
	JFrame user=new JFrame();
	JLabel name;
	JTable history;
	JButton submit;
	String[] hander={"ID","status"};
	static String[][] histroy;
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
	public User(){
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		Font font = new Font(null, 1, 20);
		
		name=new JLabel("name :WUTE");
		history=new JTable(histroy,hander);
		 JScrollPane historyT = new JScrollPane (history);
		
		user.setLayout(null);
		user.add(name);
		user.add(historyT);
		
		name.setBounds(63,31,150,38);
		name.setFont(font);
		historyT.setBounds(66, 77,219, 290);
		
		user.setSize(983,417);
		user.setTitle("ÓÃ»§´°¿Ú");
		 user.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		user.setLocation(300,120);
		 user.setResizable(false);
		 user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 user.setVisible(true);
	}
}
