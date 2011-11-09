package simulator.core;

import static org.junit.Assert.*;

import org.junit.Test;

import simulator.exceptions.WrongActionException;


public class AgentActionsTest {
	@Test
	public void testActions() throws WrongActionException {
		AgentActions left = new AgentActions(AgentActions.LEFT);
		AgentActions right = new AgentActions(AgentActions.RIGHT);
		
		assertEquals(AgentActions.LEFT, left.getDirection());
		assertEquals(AgentActions.RIGHT, right.getDirection());
	}
}
