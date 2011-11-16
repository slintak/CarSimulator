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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.Timer;

import simulator.agents.BaseAgent;
import simulator.agents.HintAgent;
import simulator.core.AgentActions;
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
	public Board(String configuration) throws FileNotFoundException, IOException {
		// Load configuration from file.
		Properties prop = new Properties();
		prop.load(new FileInputStream(configuration));
		
		// Create instance of agent
		String agentString = prop.getProperty("agent");
		BaseAgent a = this.loadAgent(agentString);

		this.simulator  = new Simulator(prop, a);
		
		// Events from keyboard
		addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        
        ActionListener taskPerformer = new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		run();
        	}
        };
        
        boolean autoStep = Boolean.parseBoolean(prop.getProperty("auto-step"));
        int autoStepDelay = new Integer(prop.getProperty("auto-step-delay"));
        if(autoStep) {
        	this.timer = new Timer(autoStepDelay, taskPerformer);
        	this.timer.start();
        }
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
    		// TODO: Ugly code, rewrite.
		    car = this.simulator.getHighway().getCarPosition(j, pos, i);
		    double x = w/2+w/5*j-10;
		    double y = h/2-car*20;
		    RoundRectangle2D rc = new RoundRectangle2D.Double(x, y, 18, 20, 8, 8);
		    g2.drawString(new Integer(car).toString(), (float)x+2, (float)y+14);
	        g2.draw(rc);
    	  }
	  }
      
      RoundRectangle2D ra = new RoundRectangle2D.Double(w/2+w/5*lane-10, h/2, 18, 20, 8, 8);
      g2.fill(ra);
      g2.draw(ra);
      
      String ts = "TimeStep: " + this.simulator.getTimeStep()
      			+ " CarPos: " + this.simulator.getAgentPerception().getPosition();
      g2.drawString(ts, (float)2.0, (float)15.0);
    }
	
	private void run() {
		try {
			// Run one step in simulator.
			this.simulator.run();
		} catch (NotLaneException e) {
			System.out.println("Agent went off the road!");
			System.exit(1);
		} catch (WrongActionException e) {
			System.out.println("Agent return wrong action!");
			System.exit(1);
		} catch (OnCrashException e) {
			System.out.println("Agent crashed!");
			System.exit(1);
		}
		
		// Now we repaint all canvas.
		repaint();
		this.simulator.getAgentPerception().setHint(AgentActions.NONE);
	}
	
	private BaseAgent loadAgent(String agentString) {
		// TODO: Rewrite this ClassLoader stuff.
		ClassLoader classLoader = BaseAgent.class.getClassLoader();
		Class aClass = null;
		try {
	        aClass = classLoader.loadClass("simulator.agents." + agentString);
	    } catch (ClassNotFoundException e) {
	        System.out.println("Givent agent not found! Agent's name was: " + agentString);
	        System.exit(1);
	    }
	    
	    BaseAgent a = null;
		try {
			a = (BaseAgent)aClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}

	@Override
	public void actionPerformed(ActionEvent evnt) {
		
	}

	private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
        	int key = e.getKeyCode();
        	
        	switch(key) {
        	// Quit application
        	case KeyEvent.VK_Q:
        		System.exit(0);
        		break;
        		
        	// Toggle between auto step and manual stepping
        	case KeyEvent.VK_P:
        		if(timer.isRunning())
        			timer.stop();
        		else
        			timer.start();
        		break;
        		
        	// Run one step.
        	case KeyEvent.VK_SPACE:
        		run();
        		break;
        	
        	// Give some hint to agent
        	case KeyEvent.VK_RIGHT:
        		simulator.getAgentPerception().setHint(AgentActions.RIGHT);
        		break;
        	case KeyEvent.VK_LEFT:
        		simulator.getAgentPerception().setHint(AgentActions.LEFT);
        		break;
        	}
        }
    }

	/**
	 * Serialization Version UID.
	 */
	private static final long serialVersionUID = -1933730511007693399L;
	private Timer timer;
	private Simulator simulator = null;
}
