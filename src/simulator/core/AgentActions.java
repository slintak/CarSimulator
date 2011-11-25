package simulator.core;

import simulator.exceptions.WrongActionException;

/**
 * <p>All possible agent's actions. Agent return one of those
 * action in method <code>run()</code>:
 * 
 * <ul>
 * <li>AgentActions.LEFT -- Left side of agent.</li>
 * <li>AgentActions.CURRENT -- Direction won't change.</li>
 * <li>AgentActions.RIGHT -- Right side of agent.</li>
 * <li>AgentActions.NONE -- No action.</li>
 * </ul></p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
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
	/**
	 * <p>No action.</p>
	 * 
	 * </p>When agent returns this action,
	 * <code>WrongActionException</code> is thrown.</p>
	 * <p>This action can be usefull in
	 * <code>AgentPerception.getHint()</code>,
	 * when no hint for agent is given.</p>
	 */
	public static final int NONE = 32;
}
