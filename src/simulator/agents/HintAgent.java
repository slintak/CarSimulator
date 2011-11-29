package simulator.agents;

import java.io.IOException;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;
import simulator.utils.Logger;

/**
 * <p>This simple agent check hints from simulator/user
 * and act based on it. If no hint is given, it remains
 * in current lane.</p>
 * 
 * <p>You can use this agent to create log file for another
 * agents. You have to manually drive agent on highway and every
 * step is saved into file <code>./logs/hint.log</code>.</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class HintAgent extends BaseAgent {
	public HintAgent() {
		super();
		// Every step will be saved into this file.
		this.logger = new Logger("./logs/hint.log");
	}
	
	@Override
	public AgentActions run(AgentPerception ap) throws NotLaneException, WrongActionException {
		// Print hint.
		System.out.println("Given hint: " + ap.getHint());
		// If no hint was given from simulator, stay in current lane.
		AgentActions ac = new AgentActions(AgentActions.CURRENT);
		
		// If hint was given, read it.
		if(ap.getHint() != AgentActions.NONE)
			ac = new AgentActions(ap.getHint());
		
		// Log our action in this step.
		try {
			this.logger.log(ap, ac);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ac;
	}

	@Override
	public void onCrash(AgentPerception ap) {
		// Do nothing
	}

	@Override
	public void onExit() {
		// This is important!
		// We have to close log file before the end.
		this.logger.close();
	}

	private Logger logger;

}
