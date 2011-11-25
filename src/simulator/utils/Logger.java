package simulator.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import simulator.core.AgentActions;
import simulator.core.AgentPerception;

/**
 * <p>This is auxiliary class which can be used for logging of
 * agent actions.</p>
 * 
 * <p>This class can be used in agent for logging its actions
 * and perceptions. In agent's method <code>run()</code> can
 * be called <code>log()</code>. This method save given classes
 * into file as JSON. Every step is saved on new line.</p>
 * 
 * <p>One line in log file can look like this (without white characters):
 * <pre><code>
 * {
 *  "perception":
 *   {
 *    "cars": {"0":10,"1":8,"-1":12},
 *    "lane":0,
 *    "position":100,
 *    "speed":10,
 *    "directionHint":32
 *   },
 *  "action": {"direction":16}
 * }
 * </code></pre>
 * </p>
 * 
 * <p>This can be useful for inteligent, learning agents. Once the log
 * file is created, it can be easily read by method <code>load()</code>.</p>
 * 
 * <p><strong>IMPORTANT:</strong> It is important to call <code>close()</code>
 * as soon as log is not needed. The best place for calling this method
 * is <code>onExit()</code> in agent.</p>
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class Logger {
	private String filename;
	private Gson gson;
	
	private PrintWriter fh = null;

	public Logger(String filename) {
		// If filename already exists, create new
		// name -- filename-1 or filename-2...
		int i = 1;
		this.filename = filename;
		while(new File(this.filename).exists()) {
			this.filename = filename + "-" + i;
			i++;
		}
		
		this.gson = new Gson();
	}

	/**
	 * Save agent's perception and action as JSON string.
	 * 
	 * @param perception Agent's perception
	 * @param action Agent's action
	 * @throws IOException Thrown if log file does not exist or cannot be created.
	 */
	public void log(AgentPerception perception, AgentActions action) throws IOException {
		if(fh == null) {
			FileWriter outFile = new FileWriter(this.filename);
			this.fh = new PrintWriter(outFile);
		}
		
		// Write objects into file
		this.fh.println(this.gson.toJson(new PerceptionActionContainer(perception, action)));
	}
	
	/**
	 * This method has to be called in the end!
	 */
	public void close() {
		this.fh.close();
	}
}

/**
 * Just simple container for AgentPerception and AgentActions
 * for serialization purposes.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
class PerceptionActionContainer {
	private AgentPerception perception;
	private AgentActions action;
	
	public PerceptionActionContainer(AgentPerception ap, AgentActions ac) {
		this.perception = ap;
		this.action = ac;
	}

	public AgentPerception getPerception() {
		return perception;
	}

	public void setPerception(AgentPerception perception) {
		this.perception = perception;
	}

	public AgentActions getAction() {
		return action;
	}

	public void setAction(AgentActions action) {
		this.action = action;
	}
}
