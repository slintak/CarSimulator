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
		// What lane is on the left?
		int leftLane = Highway.getLane(ap.getLane(), AgentActions.LEFT);
		
		// Are we in shoulder?
		if(ap.getLane() == Highway.LEFT_SHOULDER) {
			System.out.println("On left shoulder. All ok.");
			return new AgentActions(AgentActions.CURRENT);
		}
		
		int nearestCarLeft;
		try {
			nearestCarLeft = ap.getNearestCar(leftLane);
			System.out.println("Nearest car on the left is: " + nearestCarLeft);
		} catch (NotLaneException e) {}		
		
		System.out.println("Steering left.");
		return new AgentActions(AgentActions.LEFT);
	}

	@Override
	public void onCrash(AgentPerception ap) {
		System.out.println("Oops! I crashed.");
	}
}
