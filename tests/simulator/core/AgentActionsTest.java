package simulator.core;

import static org.junit.Assert.*;

import org.junit.Test;

import simulator.exceptions.WrongActionException;


public class AgentActionsTest {
	@Test
	public void testActions() throws WrongActionException {
		AgentActions left = new AgentActions(AgentActions.LEFT);
		AgentActions right = new AgentActions(AgentActions.RIGHT);
		AgentActions none = new AgentActions(AgentActions.NONE);
		AgentActions current = new AgentActions(AgentActions.CURRENT);
		
		assertEquals(AgentActions.LEFT, left.getDirection());
		assertEquals(AgentActions.RIGHT, right.getDirection());
		assertEquals(AgentActions.NONE, none.getDirection());
		assertEquals(AgentActions.CURRENT, current.getDirection());
	}
}
