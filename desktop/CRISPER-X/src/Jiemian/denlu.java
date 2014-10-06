package Jiemian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.print.DocFlavor.STRING;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import Model.Send;
import sun.security.jgss.LoginConfigImpl;
import sun.security.provider.MD5;

public class denlu extends JFrame implements ActionListener,Runnable{
	JLabel name,passward;
	JTextField nameInput,passwardInput;
	JButton  login,signUp;
	String key;
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new denlu();
	}
	public void login(){
		
		this.setLayout(null);
		name=new JLabel("name :");
		passward=new JLabel("passward :");
		nameInput=new JTextField(20);
		passwardInput=new JTextField();
		login=new JButton("login");
		signUp=new JButton("Sign Up");
		
		this.add(name);
		this.add(passward);
		this.add(nameInput);
		this.add(passwardInput);
		this.add(login);
		this.add(signUp);
//		this.setUndecorated(true);
		name.setBounds(20, 20, 40, 20);
		passward.setBounds(20, 60, 80, 20);
		nameInput.setBounds(90, 20, 120, 20);
		passwardInput.setBounds(90,60,120,20);
		login.setBounds(198,100, 50, 20);
		signUp.setBounds(120, 100, 65, 20);
		
		login.addActionListener(this);
		signUp.addActionListener(this);
		
		
		this.setSize(300,180);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login){
			String nameStr=(String)nameInput.getText();
			String passWordStr=passwardInput.getText();
			String pwsd=Md5(passWordStr.trim());
			key=new Send().Send(nameStr,pwsd);
			if(key.toString().equals("-")){
				javax.swing.JOptionPane.showMessageDialog(null,"登录失败");
			}else{
				System.out.println(key);
				javax.swing.JOptionPane.showMessageDialog(null,"登录成功");
			}
			this.setVisible(false);
//			System.out.println("wute");
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
		 kk=buf.toString();//32位的加密 

		} catch (NoSuchAlgorithmException e) { 
		e.printStackTrace(); 
		}
		return kk; 
	}
	@Override
	public void run() {
		login();
	} 
}
