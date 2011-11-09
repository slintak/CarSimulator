package simulator.core;

import java.util.Map;

import simulator.exceptions.NotLaneException;

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
}
