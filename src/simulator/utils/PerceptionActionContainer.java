package simulator.utils;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;

/**
 * Just simple container for AgentPerception and AgentActions
 * for serialization purposes.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class PerceptionActionContainer {
	public AgentPerception perception;
	public AgentActions action;
	
	public PerceptionActionContainer(AgentPerception ap, AgentActions ac) {
		this.perception = ap;
		this.action = ac;
	}
}