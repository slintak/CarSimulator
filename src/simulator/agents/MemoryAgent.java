package simulator.agents;

import java.io.IOException;
import java.util.Iterator;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;
import simulator.utils.Logger;
import simulator.utils.PerceptionActionContainer;

/**
 * <p>This example agent reads action log (it can be created by
 * HintAgent agent, this agent save new log into <code>./logs/ directory</code>)
 * and based on this log, it steer on highway.</p>
 * 
 * <p>No learning, no intelligence, just repeat actions from
 * log file.</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class MemoryAgent extends BaseAgent {
	Iterator<PerceptionActionContainer> memory;
	
	public MemoryAgent() {
		// Firstly, call parent's constructor
		super();
		
		// Now, we try to load log.
		Logger log = new Logger("./logs/hint.log");
		try {
			memory = log.load().iterator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public AgentActions run(AgentPerception ap) throws NotLaneException,
			WrongActionException {
		
		// If we have some action in log file, ...
		if(memory != null && memory.hasNext())
			// ...we return it.
			return memory.next().action;
		
		// If we have no memory (log file is empty, does not exist or we
		// have no more actions in it), return NONE action.
		return new AgentActions(AgentActions.NONE);
	}

	@Override
	public void onCrash(AgentPerception ap) {
		// Do nothing.
	}

	@Override
	public void onExit() {
		// Do nothing.
		// In this agent, we just read log file, so we
		// do not need to close it.
		// this.memory.close() -- not necessary.
	}

}
