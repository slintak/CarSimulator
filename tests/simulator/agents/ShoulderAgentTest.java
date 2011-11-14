package simulator.agents;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import simulator.core.Highway;
import simulator.core.Simulator;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.OnCrashException;
import simulator.exceptions.WrongActionException;

public class ShoulderAgentTest {
	@Test
	public void testRun() throws NotLaneException, WrongActionException,
			OnCrashException, FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream("tests/simulator.conf"));
		
		Simulator s = new Simulator(prop, new ShoulderAgent());
		
		for(int i = 0; i < 3; i++)
			s.run();
		
		assertEquals(Highway.LEFT_SHOULDER, s.getAgentPerception().getLane());
	}
	
	@Test(expected=OnCrashException.class)
	public void testRunCollide() throws FileNotFoundException, IOException,
			NotLaneException, WrongActionException, OnCrashException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream("tests/simulator-slow.conf"));
		
		Simulator s = new Simulator(prop, new ShoulderAgent());
		s.run();
	}
}
