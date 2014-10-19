package Jiemian;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Advanced extends JFrame implements ActionListener,ItemListener{
	JCheckBox rfc10,rfc12,rfc12a,rfc21,rfc23,rfc25,region_EXON,region_INTRON,region_UTR,region_INTERGENIC;
	JRadioButton nt17,nt18,nt19,nt20;
	ButtonGroup bg;
	Box sliderBox=new Box(BoxLayout.Y_AXIS);
	JTextField showVal = new JTextField(); 
	ChangeListener listener;
	JButton submit;
	JLabel tisi1,tisi2,tisi3,tisi4;
	JPanel rfcpanel,regionpanel,ntpanel;
	static int type,r1Value,ntlength;
	static int[] rfc={0,0,0,0,0,0};
	static int[] region={0,0,0,0};
	static int[] ntstates={0,0,0,0};
	Advanced(){
		
		Font font=new Font("Arial",1,12);
		
		this.setLayout(null);//…Ë÷√≤ºæ÷
		submit=new JButton("submit");
		rfc10=new JCheckBox("RFC10",change(rfc[0]));
		rfc10.setFont(font);
		rfc10.setForeground(new Color(153,153,153));
		rfc12=new JCheckBox("RFC12",change(rfc[1]));
		rfc12.setFont(font);
		rfc12.setForeground(new Color(153,153,153));
		rfc12a=new JCheckBox("RFC12a",change(rfc[2]));
		rfc12a.setFont(font);
		rfc12a.setForeground(new Color(153,153,153));
		rfc21=new JCheckBox("RFC21",change(rfc[3]));
		rfc21.setFont(font);
		rfc21.setForeground(new Color(153,153,153));
		rfc23=new JCheckBox("RFC23",change(rfc[4]));
		rfc23.setFont(font);
		rfc23.setForeground(new Color(153,153,153));
		rfc25=new JCheckBox("RFC25",change(rfc[5]));
		rfc25.setFont(font);
		rfc25.setForeground(new Color(153,153,153));
		rfcpanel=new JPanel(null);
		region_EXON=new JCheckBox("EXON",change(region[0]));
		region_EXON.setFont(font);
		region_EXON.setForeground(new Color(153,153,153));
		region_INTRON=new JCheckBox("INTRON",change(region[1]));
		region_INTRON.setFont(font);
		region_INTRON.setForeground(new Color(153,153,153));
		region_UTR=new JCheckBox("UTR",change(region[2]));
		region_UTR.setFont(font);
		region_UTR.setForeground(new Color(153,153,153));
		region_INTERGENIC=new JCheckBox("INTERGENIC",change(region[3]));
		region_INTERGENIC.setFont(font);
		region_INTERGENIC.setForeground(new Color(153,153,153));
		regionpanel=new JPanel(null);
		nt17=new JRadioButton("17");
		nt17.setFont(font);
		nt17.setForeground(new Color(153,153,153));
		nt18=new JRadioButton("18");
		nt18.setFont(font);
		nt18.setForeground(new Color(153,153,153));
		nt19=new JRadioButton("19");
		nt19.setFont(font);
		nt19.setForeground(new Color(153,153,153));
		nt20=new JRadioButton("20");
		nt20.setFont(font);
		nt20.setForeground(new Color(153,153,153));
		ntpanel=new JPanel(null);
		tisi1=new JLabel("RFC Standard:");
		tisi1.setFont(font);
		tisi1.setForeground(new Color(153,153,153));
		tisi2=new JLabel("Region:");
		tisi2.setFont(font);
		tisi2.setForeground(new Color(153,153,153));
		tisi3=new JLabel("nt length:");
		tisi3.setFont(font);
		tisi3.setForeground(new Color(153,153,153));
		tisi4=new JLabel("Weight Between Specficity & Active");
		tisi4.setFont(font);
		tisi4.setForeground(new Color(153,153,153));
		bg=new ButtonGroup();
		listener=new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                r1Value=slider.getValue();
			}
		};
		JSlider slider = new JSlider(0,100);
		slider.setValue(r1Value);
		slider.setSnapToTicks(true);  
		slider.setPaintTicks(true); 
		slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        addSlider(slider, "rl");
        
        bg.add(nt17);
        bg.add(nt18);
        bg.add(nt19);
        bg.add(nt20);
        
        this.add(tisi1);
        rfcpanel.add(rfc10);
        rfcpanel.add(rfc12);
        rfcpanel.add(rfc12a);
        rfcpanel.add(rfc21);
        rfcpanel.add(rfc23);
        rfcpanel.add(rfc25);
        this.add(rfcpanel);
        this.add(tisi2);
        regionpanel.add(region_EXON);
        regionpanel.add(region_INTRON);
        regionpanel.add(region_UTR);
        regionpanel.add(region_INTERGENIC);
        this.add(regionpanel);
        this.add(tisi3);
        ntpanel.add(nt17);
        ntpanel.add(nt18);
        ntpanel.add(nt19);
        ntpanel.add(nt20);
        this.add(ntpanel);
        this.add(tisi4);
		this.add(sliderBox);
		this.add(submit);
		tisi1.setBounds(0, 0, 200, 20);
		rfc10.setBounds(10,10,90,25);
		rfc12.setBounds(110, 10, 90, 25);
		rfc12a.setBounds(210,10,90, 25);
		rfc21.setBounds(10, 50, 90, 25);
		rfc23.setBounds(110, 50, 90, 25);
		rfc25.setBounds(210, 50, 90, 25);
		rfcpanel.setBounds(0, 20,330, 75);
		tisi2.setBounds(0, 95, 200, 20);
		region_EXON.setBounds(10, 0, 90, 25);
		region_INTRON.setBounds(110, 0, 90, 25);
		region_UTR.setBounds(10, 40, 90, 25);
		region_INTERGENIC.setBounds(110, 40,130, 25);
		regionpanel.setBounds(0, 115, 330, 75);
		tisi3.setBounds(0, 190, 200, 20);
		nt17.setBounds(10,0, 50,25);
		nt18.setBounds(60,0, 50,25);
		nt19.setBounds(110,0, 50,25);
		nt20.setBounds(160,0, 50,25);
		ntpanel.setBounds(0, 210, 330, 25);
		tisi4.setBounds(0, 235, 250, 20);
		sliderBox.setBounds(10, 255, 300,50);
		submit.setBounds(135, 310,60, 25);
		
//		this.setUndecorated(true);
//		AWTUtilities.setWindowOpaque(this, false);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.setTitle("advanced");
		 this.setSize(330,390);
		 //this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		 this.setLocation(500,280);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 this.setVisible(true);
		 submit.addActionListener(this);
		 rfc10.addItemListener(this);
		 rfc12.addItemListener(this);
		 rfc12a.addItemListener(this);
		 rfc21.addItemListener(this);
		 rfc23.addItemListener(this);
		 rfc25.addItemListener(this);
		 region_EXON.addItemListener(this);
		 region_INTRON.addItemListener(this);
		 region_UTR.addItemListener(this);
		 region_INTERGENIC.addItemListener(this);
		 nt17.addItemListener(this);
		 nt18.addItemListener(this);
		 nt19.addItemListener(this);
		 nt20.addItemListener(this);
	}
	public void addSlider(JSlider slider, String description)  
    {          
        slider.addChangeListener(listener);  
        Box box = new Box(BoxLayout.X_AXIS);  
//        box.add(new JLabel(description + "£∫"));  
        box.add(slider);  
        sliderBox.add(box);
    }
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit){
			new Intex(rfc,r1Value,region,ntlength);
			this.setVisible(false);
		}
	}
	public void itemStateChanged(ItemEvent e) {
		Object source=e.getItemSelectable();
		if(source==rfc10){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[0]=1;
			}else rfc[0]=0;
		} if(source==rfc12){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[1]=1;
			}else rfc[1]=0;
		} if(source==rfc12a){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[2]=1;
			}else rfc[2]=0;
		} if(source==rfc21){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[3]=1;
			}else rfc[3]=0;
		} if(source==rfc23){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[4]=1;
			}else rfc[4]=0;
		} if(source==rfc25){
			if(e.getStateChange()==ItemEvent.SELECTED){
				rfc[5]=1;
			}else rfc[5]=0;
		}
		
		if(source==region_EXON){
			if(e.getStateChange()==ItemEvent.SELECTED){
				region[0]=1;
			}else region[0]=0;
		}
		if(source==region_INTERGENIC){
			if(e.getStateChange()==ItemEvent.SELECTED){
				region[3]=1;
			}else region[3]=0;
		}
		if(source==region_INTRON){
			if(e.getStateChange()==ItemEvent.SELECTED){
				region[1]=1;
			}else region[1]=0;
		}
		if(source==region_UTR){
			if(e.getStateChange()==ItemEvent.SELECTED){
				region[2]=1;
			}else region[2]=0;
		}
		if(source==nt17){
			ntlength=17;
			ntstates[0]=1;
		} if(source==nt18){
			ntlength=18;
			ntstates[1]=1;
		} if(source==nt19){
			ntlength=19;
			ntstates[2]=1;
		} if(source==nt20){
			ntlength=20;
			ntstates[3]=1;
		}
		
	}
	boolean change(int i){
		if(i==1){
			return true;
		}else{
			return false;
		}
//		if(i==17){
//			
//		}
	}
}
