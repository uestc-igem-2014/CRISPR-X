package Jiemian;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONException;

import Model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class result extends JFrame implements ActionListener,MouseMotionListener,MouseListener{
	JLabel logolabel,result,history,help,about;
	JTable tableKey;
	JTable tableOfftarget;
	
	JTableHeader keyHeader;
	JPanel mb1,mb2;
	JButton drawImage,MoreInfo;
	Image backeImage;
	ImagePanel backImagePanl;
	String sequence,username;
	MapImage1 image;
	static String minStr,maxStr,strandstates="+";
	static String[] position;
	static String[] location;
	static String[] strand;
	static int count,wei;
	static List<Imageweizhi> imageweizhi;
	static String[][] datakey={{"1","1"}};
	static String[][][] dataofftarget;
	static String[][] dataofftargetx;
	final Object[] columnNames = {"key", "sequence","position","score"};
	final Object[] coloumnSonNames={"sequence","score","mms","strand","position","region"};
	static DefaultTableModel  tableModel,moreinfodate;
	public static void main(String[] args) 
	{
		try{
			UIManager.put("RootPane.setupButtonVisible",false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch(Exception e){
		}
			new result();
	}
	public result(String[][] datakey,String[][][] dataofftarget){
		try{
			this.datakey=datakey;
			this.dataofftarget=dataofftarget;
		}
		catch(Exception e){
			
		}
	}
	public result(String[] position,String location,String[] strand,int count,List<Imageweizhi> imageweizhi){
		this.position=position;
//		this.location=location;
		this.location=location.split(":",2);
		String ss=this.location[1];
		String[] ks=ss.split("\\..");
		this.minStr=ks[0];
		this.maxStr=ks[1];		
		this.count=count;
		this.strand=strand;
		this.imageweizhi=imageweizhi;	
		new MapImage1(this.minStr,this.maxStr);
	}
	public  result() {
		image=new MapImage1();
		
		this.setLayout(null);
		mb1=new JPanel(null){
			public void paint(Graphics g) 
			{
				super.paint(g);
//				g.setColor(Color.gray);
//				g.fillRect(50,65,800,10);
//				System.out.println(imageweizhi.size());
				for(int i=imageweizhi.size()-1;i>=0;i--){
					g.setColor(imageweizhi.get(i).getDescription());
					g.fillRect(50, 65,(int) image.Mapspace(imageweizhi.get(i).getEndpoint()), 10);
//					System.out.println(image.Mapspace(imageweizhi.get(i).getEndpoint()));
				}
				if(strandstates.equals("+")){
					g.setColor(Color.black);
					g.fillRect(wei,30,10,10);
//					System.out.println(wei);
					g.setColor(new Color(143,196,31));
					g.fill3DRect(wei+2,30,12,10, true);
				}else{
//					System.out.println(strand[i]);
					g.setColor(Color.black);
					g.fillRect(wei,100,10,10);
					g.setColor(new Color(143,196,31));
					g.fill3DRect(wei+2,100,12,10, true);
					
				}
			}
		};
		mb2=new JPanel(null);
		mb2.setBackground(new Color(231,240,226));
//		DefaultTableModel  tableModel = new DefaultTableModel(datakey,columnNames);
		tableKey=new JTable(datakey,columnNames);
		tableKey.setOpaque(true);
		
		
		Font fontcaidan=new Font("MONACO",1,20);
		final Font fonttable=new Font("Arial",1,10);
		
		tableKey.setFont(fonttable);
		
		logolabel=new JLabel(new ImageIcon("image/logo.png"));
		result=new JLabel("RESULT",JLabel.CENTER);
		result.setFont(fontcaidan);
		result.setOpaque(true);
		result.setBackground(Color.lightGray);
		history=new JLabel("MY LAB",JLabel.CENTER);
		history.setFont(fontcaidan);
		history.setOpaque(true);
		history.setBackground(Color.lightGray);
		about=new JLabel("ABOUT",JLabel.CENTER);
		about.setFont(fontcaidan);
		about.setOpaque(true);
		about.setBackground(Color.lightGray);
		help=new JLabel("HELP",JLabel.CENTER);
		help.setFont(fontcaidan);
		help.setOpaque(true);
		help.setBackground(Color.lightGray);
		ImageIcon MoreInfoimage=new ImageIcon("image/MoreInfo.jpg");
		MoreInfo=new JButton(MoreInfoimage);
		image=new MapImage1();
		
		//tableKey
		TableColumn firsetColumn = tableKey.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(29);//设置第一行宽度
		TableColumn secondColumn = tableKey.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(180);
		TableColumn thirdColumn = tableKey.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(70);
		TableColumn fourthColumn = tableKey.getColumnModel().getColumn(3);
		fourthColumn.setPreferredWidth(45);
		//tableKey.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		
//		tableKey.setOpaque(false);
		final JScrollPane pane1 = new JScrollPane (tableKey);
//		pane1.setOpaque(false);                                            /*边框透明效果*/
		pane1.setBorder(new CompoundBorder(new TitledBorder(""),
                new EmptyBorder(8, 8, 8, 8)));
		pane1.setBounds(20,130,345,300);
		
//		DrawImage xx= new DrawImage(position,location,strand,count);
//        int min=xx.getminNum();
//        int max=xx.getmaxNum();
		
		mb2.add(logolabel);
		mb2.add(result);
		mb2.add(history);
		mb2.add(about);
		mb2.add(help);
		logolabel.setBounds(0, 0, 200, 65);
		result.setBounds(498, 0, 100, 65);
		result.setBackground(new Color(231,240,226));
		result.setForeground(new Color(143,196,31));
		history.setBounds(598, 0, 100, 65);
		history.setBackground(new Color(231,240,226));
		history.setForeground(new Color(143,196,31));
		about.setBounds(698, 0, 100, 65);
		about.setBackground(new Color(231,240,226));
		about.setForeground(new Color(143,196,31));
		help.setBounds(798, 0, 100, 65);
		help.setBackground(new Color(231,240,226));
		help.setForeground(new Color(143,196,31));
		MoreInfo.setBounds(790, 460, 85, 32);
		image.setBounds(0, 0, 900, 130);
		
		this.getContentPane().add(mb1);
		this.getContentPane().add(mb2);
		mb1.setBounds(0,65,900,690);
		mb2.setBounds(0, 0,900,65);
		
		result.addMouseListener(this);
		result.addMouseMotionListener(this);
		history.addMouseListener(this);
		history.addMouseMotionListener(this);
		about.addMouseListener(this);
		about.addMouseMotionListener(this);
		help.addMouseListener(this);
		help.addMouseMotionListener(this);
		MoreInfo.addActionListener(this);
		
		tableKey.addMouseListener(new java.awt.event.MouseAdapter(){
             public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
                //得到选中的行列的索引值
//            	 try {
// 					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
// 				} catch (Exception e1) {
// 					e1.printStackTrace();
// 				}
               int r= tableKey.getSelectedRow();
               int c= tableKey.getSelectedColumn();
               sequence=(String) tableKey.getValueAt(r,1) ;
               String positon= (String) tableKey.getValueAt(r,2);
               
               //得到选中的单元格的值，表格中都是字符串
//               Object value= tableKey.getValueAt(r, c);
               int length=dataofftarget[r].length;
              dataofftargetx=new String[length][6];
               for(int i=0;i<5;i++){
            	   for(int j=0;j<6;j++){
            		   dataofftargetx[i][j]=dataofftarget[r][i][j];
            	   }           	   
               }
              tableModel = new DefaultTableModel(dataofftargetx,coloumnSonNames){ public boolean isCellEditable(int row, int column) { return false; }};
              tableOfftarget=new JTable(tableModel);
              tableOfftarget.clearSelection();
               JScrollPane pane2 = new JScrollPane (tableOfftarget);
               TableColumn firsetColumn1=tableOfftarget.getColumnModel().getColumn(0);
               firsetColumn1.setPreferredWidth(200);
               tableOfftarget.setShowGrid(true);
               tableOfftarget.setShowHorizontalLines(false);
               tableOfftarget.setFont(fonttable);
  //             mb1.add(pane2);
//             pane2.setOpaque(false);
     			pane2.setBorder(new CompoundBorder(new TitledBorder(""),
                     new EmptyBorder(8, 8, 8, 8)));
       			backImagePanl.add(pane2);
       			mb1.add(pane2);
               pane2.setBounds(360,130, 520, 300);
//             pane2.setOpaque(false);
//            String info=r+"行"+c+"列值 : "+value.toString();
            //javax.swing.JOptionPane.showMessageDialog(null,info);
               wei=image.MapImage1(positon, "+");
               mb1.repaint();
               strandstates=strand[r];
             }
         });
		ImageIcon kk=new ImageIcon("image/image.png");
		drawImage=new JButton(kk);
		drawImage.setBounds(720, 460, 70,70);
//		drawImage.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		drawImage.addActionListener(this);
		try{
			backeImage=ImageIO.read(new File("image/tableImage.png"));
		}catch(Exception e){
		}
		backImagePanl=new ImagePanel(backeImage);
		this.backImagePanl.setLayout(null);
		backImagePanl.add(pane1);
		backImagePanl.add(drawImage);
		mb1.add(pane1);
		mb1.add(MoreInfo);
		mb1.add(image);
		
		
		
//		this.setUndecorated(true);
//		AWTUtilities.setWindowOpaque(this, false);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.setTitle("Results page");
		this.setSize(910,650);
		 //this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		 this.setLocation(280,135);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==MoreInfo){
			try {
				String jieguo=new Send().sendgetM(sequence);
				AnalyzeM analyzeM=new AnalyzeM(jieguo);
				new MoreInfo(analyzeM);
			} catch (IOException | JSONException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getSource()==result){
			result.setBackground(new Color(46,48,45));
		}
		if(e.getSource()==history){
			history.setBackground(new Color(46,48,45));
		}
		if(e.getSource()==about){
			about.setBackground(new Color(46,48,45));
		}
		if(e.getSource()==help){
			help.setBackground(new Color(46,48,45));
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		result.setBackground(new Color(231,240,226));
		history.setBackground(new Color(231,240,226));
		about.setBackground(new Color(231,240,226));
		help.setBackground(new Color(231,240,226));
		
	}
	
}
class MapImage1 extends JPanel{
	static String[] position;
	static float site;
	static float spacelength;
	static int length=1000,positionNum;
	static String strand="+";
	static int minNum,maxNum,wei;
	static int count,x,y;
	public MapImage1(String min,String max){
		minNum=Integer.parseInt(min);
		maxNum=Integer.parseInt(max);
	}
	
	public int MapImage1(String position,String strand){
		this.position=position.split(":");
		this.positionNum=Integer.parseInt(this.position[1]);
		this.strand=strand;
		this.length=minNum-minNum;
		this.site=(((float)positionNum-(float)minNum)/((float)maxNum-(float)minNum));
		this.wei=(int)(800*site)+50;
		repaint();
		return wei;
	}
	public float Mapspace(int length) {
		this.spacelength=(((float)length-(float)minNum)/((float)maxNum-(float)minNum))*800;
		return spacelength;
	}
	public int maplength(int length){
		int len=length/800;
		return len;
	}
	public MapImage1() {
		
	}
}