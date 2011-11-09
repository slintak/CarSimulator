package simulator.core;

import simulator.exceptions.WrongActionException;

public class AgentActions {
	public AgentActions(int d) throws WrongActionException {
		if(new Integer(d) == null)
			throw new WrongActionException();
		this.direction = d;
	}

	public int getDirection() {
		return this.direction;
	}

	private int direction;
	
	/**
	 * Direction won't change.
	 */
	public static final int CURRENT = 4;
	/**
	 * Right side of agent.
	 */
	public static final int RIGHT = 8;
	/**
	 * Left side of agent.
	 */
	public static final int LEFT = 16;
}
