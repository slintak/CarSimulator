package simulator.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HighwayTest {
	@Test
	public void testCars() {
		Highway h = new Highway();
		h.setCars(Highway.LEFT_LANE,   50, 20);
		h.setCars(Highway.CENTER_LANE, 30, 15);
		h.setCars(Highway.RIGHT_LANE,  10, 10);
		
		assertEquals(0, h.getCarPosition(Highway.LEFT_LANE, 100));
		assertEquals(2, h.getCarPosition(Highway.CENTER_LANE, 28));
		assertEquals(9, h.getCarPosition(Highway.RIGHT_LANE, 11));
		
		assertEquals(0, h.getCarPosition(Highway.RIGHT_LANE, 0));
	}
	
	@Test
	public void testCarPosition() {
		Highway h = new Highway();
		h.setCars(Highway.LEFT_LANE,   32, 20);
		h.setCars(Highway.CENTER_LANE, 16, 15);
		h.setCars(Highway.RIGHT_LANE,  8, 10);
		
		h.run();
		
		assertEquals(12, h.getCarPosition(Highway.LEFT_LANE, 100));
		assertEquals(3, h.getCarPosition(Highway.CENTER_LANE, 28));
		assertEquals(7, h.getCarPosition(Highway.RIGHT_LANE, 11));
		
		assertEquals(12, h.getCarPosition(Highway.LEFT_LANE, 0));
		assertEquals(8, h.getCarPosition(Highway.RIGHT_LANE, 0));
		assertEquals(1, h.getCarPosition(Highway.CENTER_LANE, 0));
	}
	
	/**
	 * Test cars sequence in given lane, position is relative
	 * to agent's position.
	 */
	@Test
	public void testMoreCarsPosition() {
		Highway h = new Highway();
		h.setCars(Highway.LEFT_LANE,   32, 20);
		h.setCars(Highway.CENTER_LANE, 16, 15);
		h.setCars(Highway.RIGHT_LANE,  8, 10);
		
		assertEquals(0, h.getCarPosition(Highway.LEFT_LANE, 100, 1));
		assertEquals(2, h.getCarPosition(Highway.CENTER_LANE, 28, 1));
		assertEquals(9, h.getCarPosition(Highway.RIGHT_LANE, 11, 1));
		
		assertEquals(100, h.getCarPosition(Highway.LEFT_LANE, 100, 5));
		assertEquals(-28, h.getCarPosition(Highway.CENTER_LANE, 28, -2));
		assertEquals(0, h.getCarPosition(Highway.RIGHT_LANE, 11, 0));
	}
	
	/**
	 * Get number of nearest car (in front of).
	 */
	@Test
	public void testNearestCar() {
		Highway h = new Highway();
		h.setCars(Highway.LEFT_LANE,   32, 20);
		h.setCars(Highway.CENTER_LANE, 16, 15);
		h.setCars(Highway.RIGHT_LANE,  8, 10);
		
		assertEquals(5, h.getNearestCar(Highway.CENTER_LANE, 69));
		assertEquals(4, h.getNearestCar(Highway.LEFT_LANE, 69));
		
		h.run();
		
		assertEquals(6, h.getNearestCar(Highway.CENTER_LANE, 100));
		assertEquals(4, h.getNearestCar(Highway.LEFT_LANE, 100));
	}
	
	@Test
	public void testGetLane() {
		assertEquals(
				Highway.CENTER_LANE,
				Highway.getLane(Highway.CENTER_LANE, AgentActions.CURRENT)
		);
		assertEquals(
				Highway.LEFT_LANE,
				Highway.getLane(Highway.CENTER_LANE, AgentActions.LEFT)
		);
		assertEquals(
				Highway.RIGHT_LANE,
				Highway.getLane(Highway.CENTER_LANE, AgentActions.RIGHT)
		);
		assertEquals(
				Highway.LEFT_SHOULDER,
				Highway.getLane(Highway.LEFT_LANE, AgentActions.LEFT)
		);
		assertEquals(
				Highway.NOT_LANE,
				Highway.getLane(Highway.RIGHT_SHOULDER, AgentActions.RIGHT)
		);
		assertEquals(
				Highway.NOT_LANE,
				Highway.getLane(123456789, AgentActions.CURRENT)
		);
		assertEquals(
				Highway.NOT_LANE,
				Highway.getLane(Highway.CENTER_LANE, 456789)
		);
	}
}
