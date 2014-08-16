package Jiemian;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONException;

import Model.Send;

public class IDsearch extends JFrame implements ActionListener{
	JLabel ID,IDdiplay,tishi;
	JButton close,game;
	static String IDStr;
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new IDsearch();
		
	}
	public IDsearch(String ID){
		this.IDStr=ID;
	}
	public IDsearch() {
		this.setLayout(null);
		
		Font font = new Font(null, 1, 25);
		
		ID=new JLabel("ID :");
		IDdiplay=new JLabel(IDStr);
		tishi=new JLabel("正在计算中。。。");
		close=new JButton("立即查询");
		game=new JButton("小游戏");
		this.add(ID);
		ID.setBounds(30, 24, 50, 30);
		ID.setFont(font);
		IDdiplay.setBounds(80, 24, 50, 30);
		IDdiplay.setFont(font);
		this.add(IDdiplay);
		this.add(close);
		close.setBounds(250, 100,85,30);
		close.setFont(null);
		this.add(game);
		game.setBounds(150, 100, 80, 30);
		this.setFont(null);
		this.add(tishi);
		tishi.setBounds(30, 56, 105, 30);
		close.addActionListener(this);
		
		this.setSize(360,180);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==close){			
			try {
				new Send(3);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
//			this.setVisible(false);
		}
		
	}
}
