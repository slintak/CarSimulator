package simulator.agents;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;
import simulator.core.Highway;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.WrongActionException;

/**
 * <p>This simple agent moves to left shoulder as soon as possible
 * and remains there until the end.</p>
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class ShoulderAgent extends BaseAgent {

	@Override
	public AgentActions run(AgentPerception ap) throws WrongActionException {
		// What lane is on our left?
		int leftLane = Highway.getLane(ap.getLane(), AgentActions.LEFT);
		
		// Are we in shoulder?
		if(ap.getLane() == Highway.LEFT_SHOULDER) {
			System.out.println("On left shoulder. All ok.");
			// Stay in shoulder forever.
			return new AgentActions(AgentActions.CURRENT);
		}
		
		// How far is nearest car in left lane?
		// Just for example, we do not use this information.
		try {
			int nearestCarLeft = ap.getNearestCar(leftLane);
			System.out.println("Nearest car on the left is: " + nearestCarLeft);
		} catch (NotLaneException e) {}		
		
		// Steer to left.
		System.out.println("Steering left.");
		return new AgentActions(AgentActions.LEFT);
	}

	@Override
	public void onCrash(AgentPerception ap) {
		System.out.println("Oops! I crashed.");
	}

	@Override
	public void onExit() {
		// Do nothing.
	}
}
