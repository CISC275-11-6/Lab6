
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Animation4Thread extends JFrame{

	final int frameCount = 10;
    BufferedImage[] pics;
    int xloc = 100;
    int yloc = 100;
    final int xIncr = 1;
    final int yIncr = 1;
    final int picSize = 165;
    final int frameStartSize = 800;
    final int drawDelay = 30; //msec
    Timer t;
    boolean moving = true;
    
    DrawPanel drawPanel = new DrawPanel();
    Action drawAction;

    public Animation4Thread() {
    	drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			drawPanel.repaint();
    		}
    	};
      JButton s = new JButton("Start/Stop");
      JButton r = new JButton("Random Direction Change");
      JPanel buttons = new JPanel();
      JPanel animate = new JPanel();
      s.addActionListener( new ActionListener()
         {@Override
         public void actionPerformed(ActionEvent e)
         {if(moving){t.stop();
         moving = false;}
         else if(!moving){t.start();
         moving = true;}}});
      r.addActionListener( new ActionListener()
         {@Override
         public void actionPerformed(ActionEvent e)
         {System.out.println("Random");}});
      buttons.add(s);
      buttons.add(r);
    	animate.add(drawPanel);
      add(buttons, BorderLayout.NORTH);
      add(animate, BorderLayout.CENTER);
    	BufferedImage img = createImage();
    	pics = new BufferedImage[frameCount];//get this dynamically
    	for(int i = 0; i < frameCount; i++)
    		pics[i] = img.getSubimage(picSize*i, 0, picSize, picSize);
   
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(frameStartSize, frameStartSize);
    	setVisible(true);
    	pack();
      t = new Timer(this.drawDelay, this.drawAction);
    }
	
    @SuppressWarnings("serial")
	private class DrawPanel extends JPanel {
    	int picNum = 0;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
	    	picNum = (picNum + 1) % frameCount;
	    	g.drawImage(pics[picNum], xloc+=xIncr, yloc+=yIncr, Color.gray, this);
		}

		public Dimension getPreferredSize() {
			return new Dimension(frameStartSize, frameStartSize);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Animation4Thread a = new Animation4Thread();
				a.t.start();
			}
		});
	}
    
    //Read image from file and return
    private BufferedImage createImage(){
    	BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_southeast.png"));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}

