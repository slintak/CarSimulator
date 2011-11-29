package simulator.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.core.Highway;
import simulator.exceptions.WrongActionException;

public class LoggerTest {
	private HashMap<Integer, Integer> cars;
	private AgentPerception ap;
	private AgentActions left, right;
	
	@Before
	public void setUp() throws WrongActionException {
		// Create AgentPerception object
		cars = new HashMap<Integer, Integer>();
		cars.put(Highway.LEFT_LANE, 12);
		cars.put(Highway.CENTER_LANE, 10);
		cars.put(Highway.RIGHT_LANE, 8);
		
		ap = new AgentPerception();
		ap.setCars(cars);
		ap.setPosition(100);
		ap.setSpeed(10);
		
		// New agent's actions
		left = new AgentActions(AgentActions.LEFT);
		right = new AgentActions(AgentActions.CURRENT);
	}
	
	@Test
	public void testLogging() throws WrongActionException, IOException {
		String logFile = "./logs/testLogging.log";
		
		File f = new File(logFile);
		if(f.exists())
			f.delete();
		f = null;
		
		// New logger object
		Logger lg = new Logger(logFile);
		
		// Save this informations into log.
		lg.log(ap, left);
		lg.log(ap, right);
		
		// Was log file created?
		f = new File(logFile);
		assertTrue(f.exists());
		
		lg.close();
	}
	
	@Test
	public void testLoading() throws IOException {
		// New logger object
		Logger lg = new Logger("./logs/testLoading.log");
		lg.log(ap, left);
		lg.log(ap, right);
		lg.close();
		
		ArrayList<PerceptionActionContainer> li = lg.load();
		Iterator<PerceptionActionContainer> l = li.iterator();
		
		assertEquals(left.getDirection(), l.next().action.getDirection());
		assertEquals(right.getDirection(), l.next().action.getDirection());
	}
}
