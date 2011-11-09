package simulator.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import simulator.core.Simulator;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.OnCrashException;
import simulator.exceptions.WrongActionException;

/**
 * This class is proof of concept. It is temporary and will be
 * rewritten.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class Board extends JPanel implements ActionListener {
	public Board(Simulator s) {
		addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        this.simulator = s;
	}
	
	public void paint(Graphics g)
    {
      super.paint(g);

      Graphics2D g2 = (Graphics2D) g;

      RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);

      rh.put(RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_QUALITY);

      g2.setRenderingHints(rh);

      Dimension size = getSize();
      double w = size.getWidth();
      double h = size.getHeight();
      
      
      g2.setStroke(new BasicStroke(1));
      g2.setColor(Color.gray);
      for(int i = 0; i < 5; i++) {
    	  Line2D l = new Line2D.Double(w/5*i, 0, w/5*i, h);
    	  g2.draw(l);
      }
      
      int lane = this.simulator.getAgentPerception().getLane();
      int pos = this.simulator.getAgentPerception().getPosition();
      int car = 0;
      for(int i = -3; i < 4; i++) {
    	  if(i == 0) continue;
    	  for(int j = -1; j <= 1; j++) {
		    car = this.simulator.getHighway().getCarPosition(j, pos, i);
		    RoundRectangle2D rc = new RoundRectangle2D.Double(w/2+w/5*j-10, h/2-car*8+20, 18, 20, 8, 8);
	        g2.draw(rc);
    	  }
	  }
      RoundRectangle2D ra = new RoundRectangle2D.Double(w/2+w/5*lane-10, h/2, 18, 20, 8, 8);
      
      g2.draw(ra);
    }
	
	private void run() {
		try {
			// Run one step in simulator.
			this.simulator.run();
		} catch (NotLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OnCrashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Now we repaint all canvas.
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	
	}

	private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
        	int key = e.getKeyCode();
        	
        	if (key == KeyEvent.VK_Q) {
        		System.exit(0);
            }
        	
        	if(key == KeyEvent.VK_SPACE) {
        		run();
        	}
        }
    }

	/**
	 * Serialization Version UID.
	 */
	private static final long serialVersionUID = -1933730511007693399L;
	private Simulator simulator = null;
}
