package simulator.core;

import java.util.Map;

import simulator.exceptions.NotLaneException;

/**
 * <p>All agent's perception are stored in this class. In every step,
 * simulator updates this perception and call agent's <code>BaseAgent.run()</code>.
 * The only parameter of this method is <code>AgentPerception</code><p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class AgentPerception {
	
	public AgentPerception() {	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setHint(int hint) {
		this.directionHint = hint;
	}

	/**
	 * <p>Returns suggestion of direction from simulator. This
	 * suggestion can be <code>LEFT</code>, <code>RIGHT</code>,
	 * <code>NONE</code> or <code>CURRENT</code> from
	 * <code>AgentActions</code>.</p>
	 * <p>Agent can or cannot act based on this hint.</p>
	 * 
	 * @return Direction hint from simulator/user/environment.
	 */
	public int getHint() {
		return this.directionHint;
	}

	public void setCars(Map<Integer, Integer> cars) {
		this.cars = cars;
	}

	public int getNearestCar(int lane) throws NotLaneException {
		if(this.cars.get(lane) == null)
			throw new NotLaneException();
		else
			return this.cars.get(lane);
	}

	private Map<Integer, Integer> cars;
	private int lane;
	private int position;
	private int speed;
	
	/**
	 * <p>Sometimes, user or simulator can give suggestion what
	 * directions should agent ride.</p>
	 * <p><code>directionHint</code> can be:
	 * <ul>
	 * <li>AgentActions.LEFT</li>
	 * <li>AgentActions.CURRENT</li>
	 * <li>AgentActions.RIGHT</li>
	 * <li>AgentActions.NONE</li>
	 * </ul></p> 
	 */
	private int directionHint = AgentActions.NONE;
}
