package simulator.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import simulator.agents.BaseAgent;
import simulator.exceptions.NotLaneException;
import simulator.exceptions.OnCrashException;
import simulator.exceptions.WrongActionException;

/**
 *  <p>Simulator is main class for simulating agents on
 *  highway. This simulator is step-based, which means,
 *  all simulations runs in one thread.
 *  Every instance can run only one agent -- descendant of
 *  abstract class <code>BaseAgent</code>.</p>
 *  
 *  <p>When creating instance of this class, you have to specify
 *  what agent will be simulating. This is done in constructor:</p>
 *  
 *  <code>Simulator s = new Simulator("basic.conf", new SomeAgent());</code>
 *    
 *  <p>To run a simulator, you have to call its method
 *  <code>run()</code> every time, you want make one step.</p>
 *  
 * @author "Vlastimil Slintak, xslint01@stud.feec.vutbr.cz"
 *
 */
public class Simulator {
	
	/**
	 * Create new instance of Simulator class.
	 * 
	 * @param conf Path to configuration file.
	 * @param agent Instance of agent to simulate.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Simulator(String conf, BaseAgent agent) throws FileNotFoundException, IOException {
		this.agent = agent;
		
		// Load configuration from file.
		Properties prop = new Properties();
		prop.load(new FileInputStream(conf));
		
		this.highway = new Highway();
		// Set cars in left lane
		this.highway.setCars(
				Highway.LEFT_LANE,
				new Integer(prop.getProperty("left-lane-speed")), // speed
				new Integer(prop.getProperty("left-lane-gap"))); // gap
		// Set cars in center lane
		this.highway.setCars(
				Highway.CENTER_LANE,
				new Integer(prop.getProperty("center-lane-speed")), // speed,
				new Integer(prop.getProperty("center-lane-gap"))); // gap
		// Set cars in right lane
		this.highway.setCars(Highway.RIGHT_LANE,
				new Integer(prop.getProperty("right-lane-speed")), // speed,
				new Integer(prop.getProperty("right-lane-gap"))); // gap
		
		// Initial position and speed of agent
		int position = new Integer(prop.getProperty("agent-initial-position"));
		int speed = new Integer(prop.getProperty("agent-speed"));
		
		// Agent's perception.
		this.agentPerception = new AgentPerception();
		this.agentPerception.setLane(Highway.CENTER_LANE);
		this.agentPerception.setPosition(position);
		this.agentPerception.setSpeed(speed);
		this.agentPerception.setCars(this.highway.getCarPosition(position));
		
		this.timeStep = 0;
	}
	
	/**
	 * <p>This method runs one step. It runs agent and
	 * highway and check for collisions between agent
	 * and cars.</p>
	 * 
	 * @throws NotLaneException Thrown when agent rides beyond left/right shoulder.
	 * @throws WrongActionException Thrown if agent returns wrong action. 
	 * @throws OnCrashException When agent collide with other car on highway.
	 */
	public void run() throws NotLaneException, WrongActionException, OnCrashException {
		// Run agent and get his actions.
		AgentActions aa = this.agent.run(this.agentPerception);
		if(aa == null)
			throw new WrongActionException();
		
		// Based on agent's action and current lane get new lane.
		int newLane = Highway.getLane(this.agentPerception.getLane(), aa.getDirection());
		// Get number of nearest car in new agent's lane.
		int nearestCar = this.highway.getNearestCar(newLane, this.agentPerception.getPosition());
		// Run highway -- this updates position of all cars.
		this.highway.run();
		// Save new state of agent (lane, position, ...) based on action.
		this.updateAgentPerception(aa);
		// Get number of nearest car in new agent's lane.
		int newNearestCar = this.highway.getNearestCar(newLane, this.agentPerception.getPosition());
		// Check cars in front of agent. Is there a crash?
		if(newNearestCar != nearestCar) {
			this.agent.onCrash(this.agentPerception);
			throw new OnCrashException();
		}
		
		this.timeStep++;
	}

	/**
	 * This method updates perceptions of agent based on agent's action.
	 * @param aa Agent's action.
	 * @throws NotLaneException Thrown when agent rides beyond left/right shoulder.
	 */
	private void updateAgentPerception(AgentActions aa) throws NotLaneException {
		int pos = this.agentPerception.getPosition() + this.agentPerception.getSpeed();
		
		this.agentPerception.setCars(this.highway.getCarPosition(pos));
		this.agentPerception.setPosition(pos);
		
		int newLane = Highway.getLane(
				this.agentPerception.getLane(),
				aa.getDirection() );
		if(newLane == Highway.NOT_LANE)
			throw new NotLaneException();
		this.agentPerception.setLane(newLane);
	}

	/**
	 * @return Agent's instance
	 */
	public BaseAgent getAgent() {
		return this.agent;
	}

	/**
	 * @return Number of simulator's steps.
	 */
	public int getTimeStep() {
		return this.timeStep;
	}
	
	/**
	 * @return Current agent's perceptions.
	 */
	public AgentPerception getAgentPerception() {
		return this.agentPerception;
	}
	
	public Highway getHighway() {
		return this.highway;
	}

	/**
	 * This contains agent's instance in highway.
	 */
	private BaseAgent agent = null;
	
	/**
	 * State of important agent's variables -- perception of agent.
	 */
	private AgentPerception agentPerception = null;
	
	/**
	 * Every time, when is called agent's method run(),
	 * time step is incremented.
	 */
	private int timeStep = 0;
	
	/**
	 * Highway with cars and their positions.
	 */
	private Highway highway = null;

}
