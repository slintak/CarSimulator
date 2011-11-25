package simulator.agents;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;

/**
 * Simple agent for test purpose only.
 * This agent rides in the same lane in every step.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 */
public class TestAgent extends BaseAgent {

	@Override
	public AgentActions run(AgentPerception ap) throws NotLaneException,
			WrongActionException {
		// Do nothing
		return new AgentActions(AgentActions.CURRENT);
	}

	@Override
	public void onCrash(AgentPerception ap) {
		// Do nothing
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
