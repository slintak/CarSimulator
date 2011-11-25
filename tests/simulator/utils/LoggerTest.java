package simulator.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.core.Highway;
import simulator.exceptions.WrongActionException;

public class LoggerTest {
	@Test
	public void testLogging() throws WrongActionException, IOException {
		String logFile = "./logs/testLogging.log";
		
		File f = new File(logFile);
		if(f.exists())
			f.delete();
		f = null;
		
		// Create AgentPerception object
		HashMap<Integer, Integer> cars = new HashMap<Integer, Integer>();
		cars.put(Highway.LEFT_LANE, 12);
		cars.put(Highway.CENTER_LANE, 10);
		cars.put(Highway.RIGHT_LANE, 8);
		
		AgentPerception ap = new AgentPerception();
		ap.setCars(cars);
		ap.setPosition(100);
		ap.setSpeed(10);
		
		// New agent's actions
		AgentActions left = new AgentActions(AgentActions.LEFT);
		AgentActions right = new AgentActions(AgentActions.CURRENT);
		
		// New logger object
		Logger lw = new Logger(logFile);
		
		// Save this informations into log.
		lw.log(ap, left);
		lw.log(ap, right);
		
		// Was logg file created?
		f = new File(logFile);
		assertTrue(f.exists());
		
		// Read saved info from log
		//Logger lr = new Logger(logFile);
		//lr.loadLog();
		
		lw.close();
	}
}
