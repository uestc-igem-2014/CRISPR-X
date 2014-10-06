package Model;

import java.awt.*;
import javax.swing.*;
public class ImagePanel extends JPanel{

	Image im;
	public ImagePanel(Image im)
	{
		this.im=im;
//		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
//		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		int width=900;
		int height=550;
		this.setSize(width,height);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(100,460,700,2);
		g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
	}
}

