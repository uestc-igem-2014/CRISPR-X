package Jiemian;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONException;

import Model.Send;

public class IDseacher extends JFrame implements ActionListener{
	JLabel IDStr;
	JTextField IDinput;
	JButton seacher;
	static String ID;
	public String getID() {
		return ID;
	}
	public static void main(String[] args){
		new IDseacher().idSeacherWindow();
	}
	public IDseacher() {
		// TODO 自动生成的构造函数存根
	}
	public void idSeacherWindow(){
		this.setLayout(null);
		this.setBackground(Color.white);
		
		Font font1=new Font("Arial",1,20);
		
		IDStr=new JLabel("ID:");
		IDStr.setFont(font1);
		IDinput=new JTextField(20);
		IDinput.setFont(font1);
		seacher=new JButton("seacher");
		
		this.add(IDStr);
		this.add(IDinput);
		this.add(seacher);
		
		IDStr.setBounds(20,50,40, 40);
		IDinput.setBounds(60,55, 230,28);
		seacher.setBounds(220,120,100, 30);
		
		seacher.addActionListener(this);
		
		this.setSize(360,200);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==seacher){
			ID=IDinput.getText();
			new Send(ID);
			try {
				new Send(3);
			} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		
	}
}
