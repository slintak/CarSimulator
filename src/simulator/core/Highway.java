package simulator.core;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class represents highway on which simulation of agent
 * take place. Every instance of highway has 3 lanes and 2 shoulders
 * named:
 * <ul>
 * <li>Highway.LEFT_SHOULDER</li>
 * <li>Highway.LEFT_LANE</li>
 * <li>Highway.CENTER_LANE</li>
 * <li>Highway.RIGHT_LANE</li>
 * <li>Highway.RIGHT_SHOULDER</li>
 * <li>Highway.NOT_LANE</li>
 * </ul></p>
 * 
 * <p>This constants should be used for definition of place
 * in highway.</p>
 * 
 * <p>In every highway are cars. These cars can be in every lane
 * or shoulder and
 * have their constant speed and gaps between them. These
 * values has to be set by method
 * <code>setCars(int, int, int)</code>.
 * </p>
 * 
 * @author "Vlastimil Slintak, xslint01@stud.feec.vutbr.cz"
 *
 */
public class Highway {
	
	public Highway() {
		this.timeStep = 0;
		this.speeds = new HashMap<Integer, Integer>();
		this.gaps = new HashMap<Integer, Integer>();
	}
	
	/**
	 * <p>Cars in lanes are moved according to their speed in
	 * every turn (everytime this mathod is called).</p>
	 */
	public void run() {
		this.timeStep++;
	}

	/**
	 * <p>Set cars and their speed and gaps to given lane.
	 * For set cars to center lane with speed 20 and gaps 45:
	 * <code>setCars(Highway.CENTER_LANE, 20, 45)</code></p>
	 * 
	 * <p>Speed is defined as position per turn. This means
	 * if speed is 20, in 10th turn (determined by
	 * <code>timeStep</code>) is car in position 200, in 11th
	 * turn it has position 220.</p>
	 * 
	 * @param lane
	 * @param speed
	 * @param gap
	 */
	public void setCars(int lane, int speed, int gap) {
		this.speeds.put(lane, speed);
		this.gaps.put(lane, gap);
	}
	
	/**
	 * <p>Return the next higher number of car nearest to
	 * position.</p>
	 * 
	 * <p>Every car is numbered absolutely by position 0.
	 * This means, if gap between cars in given lane is
	 * 20, car at position 100 has number 5. So, if
	 * we call this method
	 * <code>getNearestCar(Highway.CENTER_LANE, 109)</code>
	 * we get number 6.</p>
	 */
	public int getNearestCar(int lane, int position) {
		if(lane == Highway.LEFT_SHOULDER || lane == Highway.RIGHT_SHOULDER)
			return 0;
		
		int gap = this.gaps.get(lane);
		int speed = this.speeds.get(lane);
		// Get nearest car behind the position
		int pos = (position - timeStep * speed);
		return (int)(pos / gap + 1);
	}
	
	public Map<Integer, Integer> getCarPosition(int position) {
		Map<Integer, Integer> cars = new HashMap<Integer, Integer>();
		cars.put(Highway.LEFT_LANE, getCarPosition(Highway.LEFT_LANE, position));
		cars.put(Highway.CENTER_LANE, getCarPosition(Highway.CENTER_LANE, position));
		cars.put(Highway.RIGHT_LANE, getCarPosition(Highway.RIGHT_LANE, position));
		return cars;
	}
	
	/**
	 * <p>Returns distance of nearest car from given position in
	 * given lane.</p>
	 * 
	 * <p>Similar to function
	 * <code>getCarPosition(int, int, int)</code>,
	 * but returns only nearest car in front of agent.</p>
	 * 
	 * @param lane 
	 * @param position Agent's position/ 
	 * @return Distance from agent.
	 */
	public int getCarPosition(int lane, int position) {
		return this.getCarPosition(lane, position, 1);
		/*int gap = this.gaps.get(lane);
		int speed = this.speeds.get(lane);
		// Get nearest car behind the position
		int pos = (position - timeStep * speed);
		int carPosition = (pos / gap) * gap + timeStep * speed;
		
		// If nearest car behind is on the same position
		if(carPosition == position)
			return 0;
		else
			// Return nearest car in front of the position
			if(pos < 1)
				return (carPosition) - position;
			return (carPosition + gap) - position;*/
	}
	
	/**
	 * <p>Returns distance of n-th car in given lane relative to
	 * agents position.</p>
	 * 
	 * <p>For example, we have agent on position 128 and we want to
	 * know how far is 5th car in center lane:
	 *   <code>
	 *   int distance = getCarPosition(Highway.CENTER_LANE, 128, 5);
	 *   </code>
	 *   
	 * The result can be positive or negative integer.
	 * </p>
	 * 
	 * @param lane 
	 * @param position Agent's position
	 * @param carSequence N-th car in given lane in front of car
	 * @return Distance from agent.
	 */
	public int getCarPosition(int lane, int position, int carSequence) {
		if(carSequence == 0)
			return 0;
		if(carSequence < 0)
			carSequence++;
		
		// Get gap and speed of cars in given lane.
		int gap = this.gaps.get(lane);
		//int speed = this.speeds.get(lane);
		
		// Get number of nearest car behind the agent
		int carBehind = (int)(position / gap);
		int carBehindPosition = carBehind * gap;

		if(carBehindPosition == position && carSequence == 1)
			return 0;
		// Get car's position
		int carPosition = carBehindPosition + gap * carSequence;
		return carPosition - position;
	}

	/**
	 * <p>Based on current lane and direction, return
	 * new lane.</p>
	 * 
	 * <p>For example code
	 *   <code>
	 *   Highway.getLane(Highway.CENTER_LANE, AgentActions.LEFT)
	 *   </code>
	 * returns new lane <code>Highway.LEFT_LANE</code>.
	 * </p>
	 * 
	 * @param lane Current lane.
	 * @param direction Direction -- right/left.
	 * @return New lane or Highway.NOT_LANE.
	 */
	public static int getLane(int lane, int direction) {
		int new_lane = lane;
		
		switch(direction) {
		  case AgentActions.RIGHT:
			  new_lane++;
			  break;
		  case AgentActions.LEFT:
			  new_lane--;
			  break;
		  case AgentActions.CURRENT:
			  new_lane = lane;
			  break;
		  default:
			  new_lane = Highway.NOT_LANE;
		}
		
		if(Math.abs(new_lane) >= Highway.NOT_LANE)
			new_lane = Highway.NOT_LANE;
		
		return new_lane;
	}

	/**
	 * Speeds of cars in every lane.
	 */
	Map<Integer, Integer> speeds = null;
	
	/**
	 * Gaps between cars in every lane.
	 */
	Map<Integer, Integer> gaps = null;
	
	private int timeStep;
	
	// Lane constants
	public static final int LEFT_SHOULDER = -2;
	public static final int LEFT_LANE = -1;
	public static final int CENTER_LANE = 0;
	public static final int RIGHT_LANE = 1;
	public static final int RIGHT_SHOULDER = 2;
	public static final int NOT_LANE = 3;
	
	/**
	 * Direction in front of agent.
	 */
	public static final int FRONT = 8;
	
	/**
	 * Direction behind the agent.
	 */
	public static final int BEHIND = 16;

}
