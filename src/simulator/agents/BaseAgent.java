package simulator.agents;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;

/**
 * <p>This is base abstract class of agent. Every agent in simulator has
 * to be descendant of this class and implement two methods:
 * <code>run(AgentPerception ap)</code> and <code>onCrash(AgentPerception)</code>
 * </p>
 * <p>First method, <code>run()</code> is called by simulator in every step.
 * First and only parameter if this method is AgentPerception and contains
 * all information about agent's neighborhood.</p>
 * <p>Second method, <code>onCrash()</code> is called once by simulator, when agent make
 * mistake and crash into other car on highway. This method can do nothing.</p>
 * 
 * @author "Vlastimil Slintak, xslint01@stud.feec.vutbr.cz"
 *
 */
public abstract class BaseAgent {
	/**
     * This method is called every time step and
     * should contain agent's logic.
     * 
     * @param ap Perception of agent.
     * @return Actions of agent.
	 * @throws NotLaneException 
	 * @throws WrongActionException 
     */
	public abstract AgentActions run(AgentPerception ap) throws NotLaneException, WrongActionException;
	
	/**
	 * This method is called when agent crash into other car. It can contain
	 * come cleaning (like closing files, sockets, etc.), or can print come
	 * info into stdout, or can do nothing.
	 * 
	 * @param ap Perception of agent.
	 */
	public abstract void onCrash(AgentPerception ap);
}
