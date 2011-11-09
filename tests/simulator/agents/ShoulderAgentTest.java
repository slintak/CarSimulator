package simulator.agents;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

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
		Simulator s = new Simulator("tests/simulator.conf", new ShoulderAgent());
		
		for(int i = 0; i < 3; i++)
			s.run();
		
		assertEquals(Highway.LEFT_SHOULDER, s.getAgentPerception().getLane());
	}
	
	@Test(expected=OnCrashException.class)
	public void testRunCollide() throws FileNotFoundException, IOException,
			NotLaneException, WrongActionException, OnCrashException {
		Simulator s = new Simulator("tests/simulator-slow.conf", new ShoulderAgent());
		s.run();
	}
}
