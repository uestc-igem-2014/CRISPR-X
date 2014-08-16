package Jiemian;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import Jiemian.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch5_table.BETableHeaderUI;

import com.sun.awt.AWTUtilities;

import Model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

public class result extends JFrame implements ActionListener{
	JTable tableKey;
	JTable tableOfftarget;
	JTableHeader keyHeader;
	JPanel mb1,mb2;
	JButton drawImage;
	Image backeImage;
	ImagePanel backImagePanl;
	static String[] position;
	static String location;
	static String[] strand;
	static int count;
	static String[][] datakey;
	static String[][][] dataofftarget;
	static String[][] dataofftargetx;
	final Object[] columnNames = {"key", "sequence","position","score"};
	final Object[] coloumnSonNames={"sequence","score","mms","strand","position","region"};
	public result(String[][] datakey,String[][][] dataofftarget){
		try{
			this.datakey=datakey;
			this.dataofftarget=dataofftarget;
		}
		catch(Exception e){
			
		}		
	}
	public result(String[] position,String location,String[] strand,int count){
		this.position=position;
		this.location=location;
		this.count=count;
		this.strand=strand;
//		System.out.print(this.strand[1]);
	}
	public  result() {
		this.setLayout(null);
		mb1=new JPanel(null);
		mb1.setOpaque(false);
		tableKey=new JTable(datakey,columnNames);
		tableKey.setOpaque(true);
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
//		pane1.setOpaque(false);                                                /*边框透明效果*/
		pane1.setBorder(new CompoundBorder(new TitledBorder(""),
                new EmptyBorder(8, 8, 8, 8)));
		
//		tableKey.getTableHeader().setVisible(false);
//		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  
//        renderer.setPreferredSize(new Dimension(0, 0));  
//        tableKey.getTableHeader().setDefaultRenderer(renderer);  
//		JViewport view = new JViewport(){
//			public void paintComponent(Graphics g){
//			super.paintComponent(g);
//			g.setColor(Color.red);
//			g.drawLine(0,0,getWidth(), getHeight());
//			}
//			};
//			pane1.setViewport(view);
//			tableKey.setOpaque(false);
//			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
//			render.setOpaque(false);
//			tableKey.setDefaultRenderer(Object.class,render);
//			pane1.setViewportView(tableKey);
//		mb1.add(pane1);
		pane1.setBounds(20,130,345,300);
//		tableKey.setPreferredScrollableViewportSize(new Dimension(550, 30));
		this.getContentPane().add(mb1);
		mb1.setBounds(0,0,900,620);

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
               //得到选中的单元格的值，表格中都是字符串
//               Object value= tableKey.getValueAt(r, c);
               int length=dataofftarget[r].length;
              dataofftargetx=new String[length][6];
               for(int i=0;i<5;i++){
            	   for(int j=0;j<6;j++){
            		   dataofftargetx[i][j]=dataofftarget[r][i][j];
            	   }           	   
               }
              tableOfftarget=new JTable(dataofftargetx,coloumnSonNames);
              tableOfftarget.clearSelection();
               JScrollPane pane2 = new JScrollPane (tableOfftarget);
               TableColumn firsetColumn1=tableOfftarget.getColumnModel().getColumn(0);
               firsetColumn1.setPreferredWidth(200);
               tableOfftarget.setShowGrid(true);
               tableOfftarget.setShowHorizontalLines(false);
  //             mb1.add(pane2);
//             pane2.setOpaque(false);
     			pane2.setBorder(new CompoundBorder(new TitledBorder(""),
                     new EmptyBorder(8, 8, 8, 8)));
       			backImagePanl.add(pane2);
               pane2.setBounds(360,130, 520, 300);
//             pane2.setOpaque(false);
//            String info=r+"行"+c+"列值 : "+value.toString();
            //javax.swing.JOptionPane.showMessageDialog(null,info);
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
		mb1.add(backImagePanl);
		
//		this.setUndecorated(true);
//		AWTUtilities.setWindowOpaque(this, false);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		this.setTitle("结果窗口");
		this.setSize(910,587);
		 //this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		 this.setLocation(300,155);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==drawImage){
			new DrawImage(position,location,strand,count);
//			System.out.print(strand[1]);

		}
	}
}
