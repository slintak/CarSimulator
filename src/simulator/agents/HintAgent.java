package simulator.agents;

import java.io.IOException;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;
import simulator.utils.Logger;

/**
 * <p>This simple agent check hints from simulator/user
 * and act based on it.</p>
 * <p>If no hint is given, it remains in current lane.</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class HintAgent extends BaseAgent {
	public HintAgent() {
		super();
		
		this.logger = new Logger("./logs/hint.log");
	}
	
	@Override
	public AgentActions run(AgentPerception ap) throws NotLaneException, WrongActionException {
		// Print hint and take action based on it.
		System.out.println("Given hint: " + ap.getHint());
		
		AgentActions ac = new AgentActions(AgentActions.CURRENT);
		
		if(ap.getHint() != AgentActions.NONE)
			ac = new AgentActions(ap.getHint());
		
		try {
			this.logger.log(ap, ac);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ac;
	}

	@Override
	public void onCrash(AgentPerception ap) {
		// Do nothing
	}

	private Logger logger;

	@Override
	public void onExit() {
		this.logger.close();
	}

}
