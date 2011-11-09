package simulator.core;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import simulator.exceptions.NotLaneException;


public class AgentPerceptionTest {
	private Map<Integer, Integer> cars;
	
	@Before
	public void setUp() {
		cars = new HashMap<Integer, Integer>();
		cars.put(Highway.LEFT_LANE, 12);
		cars.put(Highway.CENTER_LANE, 10);
		cars.put(Highway.RIGHT_LANE, 8);
	}
	
	@Test
	public void testPerceptions() {
		AgentPerception ap = new AgentPerception();
		
		ap.setLane(Highway.CENTER_LANE);
		ap.setPosition(100);
		ap.setSpeed(10);
		
		assertEquals(Highway.CENTER_LANE, ap.getLane());
		assertEquals(100, ap.getPosition());
		assertEquals(10, ap.getSpeed());
	}
	
	@Test
	public void testCars() throws NotLaneException {
		AgentPerception ap = new AgentPerception();
		ap.setCars(cars);
		
		assertEquals(10, ap.getNearestCar(Highway.CENTER_LANE));	
	}
	
	@Test(expected=NotLaneException.class)
	public void testWrongCars() throws NotLaneException {
		AgentPerception ap = new AgentPerception();
		ap.setCars(cars);
		
		ap.getNearestCar(123456789);
	}
}
