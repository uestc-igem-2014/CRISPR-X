package Jiemian;

import javax.swing.JFrame;

public class IDseacher extends JFrame{
	int ID;
	public IDseacher(int ID){
		this.ID=ID;
	}
	public void idSeacherWindow(){
		
		this.setSize(360,180);
		this.setResizable(false);
		this.setLocation(300,280);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
