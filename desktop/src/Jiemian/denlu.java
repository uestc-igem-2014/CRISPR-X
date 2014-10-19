package Jiemian;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONException;

import Model.Send;

public class denlu extends JFrame implements ActionListener,Runnable{
	JLabel name,passward;
	JTextField nameInput,passwardInput;
	JButton  login,signUp;
	static String key,nameStr;
	int status;
	static JDialog loginsuccess;
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new denlu().login();
	}
	public void login(){
		
		ImageIcon loginimg=new ImageIcon("image/login.jpg");
		ImageIcon SignUpimg=new ImageIcon("image/SignUp.jpg");
		
		Font font1=new Font("Arial",1,12);
		
		this.setLayout(null);
		name=new JLabel("Name :");
		name.setFont(font1);
		passward=new JLabel("Passward :");
		passward.setFont(font1);
		nameInput=new JTextField(20);
		passwardInput=new JTextField();
		login=new JButton(loginimg);
		signUp=new JButton(SignUpimg);
		
		loginsuccess=new JDialog(this,true);
		loginsuccess.setLayout(null);
		loginsuccess.setSize(300, 200);
		loginsuccess.setLocation(getX() + 50, getY() + 50);
		JLabel messagelogin=new JLabel("Login was successful!");
		JButton loginsuccessclose=new JButton("ok");
		loginsuccess.add(messagelogin);
		loginsuccess.add(loginsuccessclose);
		messagelogin.setBounds(0, 0, 200, 200);
		
		this.add(name);
		this.add(passward);
		this.add(nameInput);
		this.add(passwardInput);
		this.add(login);
		this.add(signUp);
//		this.setUndecorated(true);
		name.setBounds(55, 20, 40, 20);
		passward.setBounds(55, 90, 80, 20);
		nameInput.setBounds(135, 20, 200, 25);
		passwardInput.setBounds(135,90,200,25);
		login.setBounds(220,170, 100, 32);
		signUp.setBounds(80, 170,100, 32);
		
		login.addActionListener(this);
		signUp.addActionListener(this);
		
		
		
		this.setSize(400,250);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login){
			nameStr=(String)nameInput.getText();
			String passWordStr=passwardInput.getText();
			String pwsd=Md5(passWordStr.trim());
			key=new Send().Send(nameStr,pwsd);
			if(key.toString().equals("-")){
				javax.swing.JOptionPane.showMessageDialog(null,"Logon failure");
				status=0;
			}else{
				status=1;
//				loginsuccess.dispose();
				javax.swing.JOptionPane.showMessageDialog(null,"Login was successful");
//				loginsuccess.setVisible(true);
				new Intex().user.setText(denlu.nameStr);
				try {
					new Intex(status);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
				
			this.setVisible(false);
		}
		if(e.getSource()==signUp){
			new SignUp();
		}
	}
	private static String Md5(String plainText ) { 
		String kk = null;
		try { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(plainText.getBytes()); 
		byte b[] = md.digest(); 

		int i; 

		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
		i = b[offset]; 
		if(i<0) i+= 256; 
		if(i<16) 
		buf.append("0"); 
		buf.append(Integer.toHexString(i)); 
		} 
		 kk=buf.toString();//32Î»µÄ¼ÓÃÜ 

		} catch (NoSuchAlgorithmException e) { 
		e.printStackTrace(); 
		}
		return kk; 
	}
	public void run() {
		login();
	}
	public int getstatus(){
		return status;	
	}
}
