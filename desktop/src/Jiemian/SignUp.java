package Jiemian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONException;

import Model.Send;


public class SignUp extends JFrame implements ActionListener {
	JLabel name,password,repassword,email;
	JTextField inputName,inputPwd,inputRePwd,inputEmail;
	JButton apply;
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new SignUp();
	}
	SignUp(){
		name=new JLabel("name :");
		password=new JLabel("Initial Password :");
		repassword=new JLabel("Reenter Password :");
		email=new JLabel("Reenter Email :");
		inputName=new JTextField(20);
		inputPwd=new JTextField(20);
		inputRePwd=new JTextField(20);
		inputEmail=new JTextField(20);
		apply=new JButton("apply");
		
		this.setLayout(null);
		this.add(apply);
		apply.setBounds(400, 145, 70,30);
		this.add(inputEmail);
		inputEmail.setBounds(160, 105, 180, 20);
		this.add(inputName);
		inputName.setBounds(160, 20,180, 20);
		this.add(inputPwd);
		inputPwd.setBounds(160, 45, 180, 20);
		this.add(inputRePwd);
		inputRePwd.setBounds(160, 75, 180, 20);
		this.add(name);
		name.setBounds(93, 20, 38, 20);
		this.add(password);
		password.setBounds(20, 45, 120, 20);
		this.add(repassword);
		repassword.setBounds(20, 75, 120, 20);
		this.add(email);
		email.setBounds(39, 105,100, 20);
		
		apply.addActionListener(this);
		
		this.setSize(500,230);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==apply){
			String nameStr=inputName.getText();
			String pswdStr=Md5(inputPwd.getText().trim());
			String email=inputEmail.getText();
			System.out.println(nameStr+":"+pswdStr+":"+email);
			String value=new Send().Send(nameStr, pswdStr, email);
			if(value.equals("Sign In Succeed")){
				javax.swing.JOptionPane.showMessageDialog(null,"signup was successful");
				this.setVisible(false);
			}else{
				javax.swing.JOptionPane.showMessageDialog(null,"signup failure");
			}
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
		 kk=buf.toString();//32λ�ļ��� 

		} catch (NoSuchAlgorithmException e) { 
		e.printStackTrace(); 
		}
		return kk; 
	}
}
