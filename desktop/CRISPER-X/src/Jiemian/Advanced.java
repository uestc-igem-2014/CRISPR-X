package Jiemian;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.sun.awt.*;
import Jiemian.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Advanced extends JFrame implements ActionListener,ItemListener{
	JCheckBox rfc10,rfc12,rfc12a,rfc21,rfc23,rfc25;
	Box sliderBox=new Box(BoxLayout.Y_AXIS);
	JTextField showVal = new JTextField(); 
	ChangeListener listener;
	JButton submit;
	int r1Value;
	int[] rfc={0,0,0,0,0,0};
	public static void main(String[] args) {
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		   
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
		new Advanced();
	}
	Advanced(){
		this.setLayout(null);//设置布局
		submit=new JButton("submit");
		rfc10=new JCheckBox("rfc10");
		rfc12=new JCheckBox("rfc12");
		rfc12a=new JCheckBox("rfc12a");
		rfc21=new JCheckBox("rfc21");
		rfc23=new JCheckBox("rfc23");
		rfc25=new JCheckBox("rfc25");
		
		listener=new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                r1Value=slider.getValue();
//                rfc10.getItemListeners();
//                showVal.setText("当前滑动条的值为：" + so.getValue()); 
			}
		};
		JSlider slider = new JSlider(0,100);
		slider.setSnapToTicks(true);  
		slider.setPaintTicks(true); 
//		slider.setPaintTrack(false); 
		slider.setMajorTickSpacing(20);  
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        addSlider(slider, "rl");
        
        
		this.add(rfc10);
		this.add(rfc12);
		this.add(rfc12a);
		this.add(rfc21);
		this.add(rfc23);
		this.add(rfc25);
		this.add(sliderBox);
		this.add(submit);
		rfc10.setBounds(10,10,90,25);
		rfc12.setBounds(110, 10, 90, 25);
		rfc12a.setBounds(210,10,90, 25);
		rfc21.setBounds(10, 60, 90, 25);
		rfc23.setBounds(110, 60, 90, 25);
		rfc25.setBounds(210, 60, 90, 25);
		sliderBox.setBounds(10, 100, 280,50);
		submit.setBounds(125, 150,60, 25);
		
//		this.setUndecorated(true);
//		AWTUtilities.setWindowOpaque(this, false);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.setTitle("advanced");
		 this.setSize(310,230);
		 //this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		 this.setLocation(500,280);
		 this.setResizable(false);
//		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 submit.addActionListener(this);
		 rfc10.addItemListener(this);
		 rfc10.addActionListener(this);
		 rfc12.addItemListener(this);
		 rfc12a.addItemListener(this);
		 rfc21.addItemListener(this);
		 rfc23.addItemListener(this);
		 rfc25.addItemListener(this);
	}
	public void addSlider(JSlider slider, String description)  
    {          
        slider.addChangeListener(listener);  
        Box box = new Box(BoxLayout.X_AXIS);  
        box.add(new JLabel(description + "："));  
        box.add(slider);  
        sliderBox.add(box);
    }
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit){
			new Inter(rfc,r1Value);
			this.setVisible(false);
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source=e.getItemSelectable();
//		System.out.println(source.toString());
		if(source==rfc10){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[0]=1;
			}else rfc[0]=0;
		}else if(source==rfc12){
			if(e.getStateChange()==ItemEvent.SELECTED){
//				System.out.println("gaibian");
				rfc[1]=1;
			}else rfc[1]=0;
		}else if(source==rfc12a){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[2]=1;
			}else rfc[2]=0;
		}else if(source==rfc21){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[3]=1;
			}else rfc[3]=0;
		}else if(source==rfc23){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[4]=1;
			}else rfc[4]=0;
		}else if(source==rfc25){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[5]=1;
			}else rfc[5]=0;
		}
	}
}
