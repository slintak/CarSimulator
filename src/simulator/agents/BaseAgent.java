package simulator.agents;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;

/**
 * <p>This is base abstract class of agent. Every agent in simulator has
 * to be descendant of this class and implement three methods:
 * <code>run(AgentPerception ap)</code>, <code>onCrash(AgentPerception)</code>
 * and <code>onExit()</code>.
 * </p>
 * <p>First method, <code>run()</code> is called by simulator in every step.
 * First and only parameter if this method is AgentPerception and contains
 * all information about agent's neighborhood.</p>
 * <p>Second method, <code>onCrash()</code> is called once by simulator, when agent make
 * mistake and crash into other car on highway. This method can do nothing.</p>
 * <p>Third method is called once -- before end of simulation. This is the best
 * place for cleaning (closing files, sockets, etc.).</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
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
	 * This method is called when agent crash into other car. It can print some
	 * info into stdout, or can do nothing.
	 * 
	 * @param ap Perception of agent.
	 */
	public abstract void onCrash(AgentPerception ap);
	
	/**
	 * <p>This method is called only once -- at the end of simulation.
	 * Regardless the result (suscessful or crash) this method is called
	 * at the end.</p>
	 * <p>It can contain
	 * some cleaning (like closing files, sockets, etc.), or can print some
	 * info into stdout, or can do nothing.</p>
	 */
	public abstract void onExit();
}
