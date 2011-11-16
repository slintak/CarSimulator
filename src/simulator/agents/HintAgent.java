package simulator.agents;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;

/**
 * <p>This simple agent check hints from simulator/user
 * and act based on it.</p>
 * <p>If no hint is givent, it remains in current lane.</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class HintAgent extends BaseAgent {
	@Override
	public AgentActions run(AgentPerception ap) throws NotLaneException, WrongActionException {
		// Print hint and take action based on it.
		System.out.println("Given hint: " + ap.getHint());
		if(ap.getHint() != AgentActions.NONE)
			return new AgentActions(ap.getHint());
		else
			return new AgentActions(AgentActions.CURRENT);
	}

	@Override
	public void onCrash(AgentPerception ap) {
		// Do nothing.
	}

}
