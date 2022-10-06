//made by falkush
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class fpsdisplay {
	public static void main(String args[]) throws AWTException, InterruptedException
	{
		
		BufferedImage capture;
		
		int cr,cg,cb,j,k=0,rgb,nf=0;
		boolean ndif=true;
		final Robot robot = new Robot();
		int[][][] frame = new int[320][240][3];
        
		//start
		long starttime, temps;
		Rectangle screenRect = new Rectangle(630,59,995,742); //resize your streaming rectangle here
		starttime=System.currentTimeMillis();
		
		JFrame jframe = new JFrame("JFrame Example");  
        JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        JLabel label = new JLabel("FPS");  
        JLabel labelfps = new JLabel("0");  
 
        panel.add(label);  
        panel.add(labelfps);  

        jframe.add(panel);  
        jframe.setSize(200, 200);  
        jframe.setLocationRelativeTo(null);  
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        jframe.setVisible(true);  
		
		while(true)
		{
			 capture = robot.createScreenCapture(screenRect);
			 ndif=true;
			 for(j=0;j<320;j++)
			 {
				 for(k=0;k<240;k++)
				 {
					 rgb=capture.getRGB(3*j, 3*k); //due to low resolution
					 cr = (rgb >> 16) & 0xFF;
					 cg = (rgb >> 8) & 0xFF;
					 cb = rgb & 0xFF;

					 if(ndif)
					 {
						 if(compare(cr,cg,cb,frame[j][k][0],frame[j][k][1],frame[j][k][2]))
						 {
							 ndif=false;
							 nf++;
							 frame[j][k][0]=cr;
							 frame[j][k][1]=cg;
							 frame[j][k][2]=cb;
						 }					 
					 }
					 else
					 {
						 frame[j][k][0]=cr;
						 frame[j][k][1]=cg;
						 frame[j][k][2]=cb;
					 }
						 
				 }
				 
				 temps=System.currentTimeMillis();
				 if(temps-starttime >= 1000)
				 {
					 labelfps.setText(""+nf);
					 nf=0;
					 starttime=temps;
				 }
			 }
		}
    }
	
	public static boolean compare(int a, int b, int c, int d, int e, int f) //return true if different
	{
		if(Math.abs(a-d)>30) return true;
		else if(Math.abs(b-e)>30) return true;
		else if(Math.abs(c-f)>30) return true;
		return false;
	}
}
