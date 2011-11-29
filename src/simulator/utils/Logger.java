package simulator.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
	
	private PrintWriter fileHandler = null;

	public Logger(String filename) {
		this.filename = filename;
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
		if(fileHandler == null) {
			this.makeFilename(this.filename);
			FileWriter outFile = new FileWriter(this.filename);
			this.fileHandler = new PrintWriter(outFile);
		}
		
		// Write objects into file
		this.fileHandler.println(this.gson.toJson(new PerceptionActionContainer(perception, action)));
	}
	
	public ArrayList<PerceptionActionContainer> load() throws IOException {
		  FileInputStream fstream = new FileInputStream(this.filename);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  
		  ArrayList<PerceptionActionContainer> log = new ArrayList<PerceptionActionContainer>();
		  String strLine;
		  Gson gson = new Gson();
		  for(int i = 0; (strLine = br.readLine()) != null; i++) {
			  log.add(gson.fromJson(strLine, PerceptionActionContainer.class));
		  }
		  
		return log;
		
	}

	/**
	 * This method has to be called in the end!
	 */
	public void close() {
		this.fileHandler.close();
		this.fileHandler = null;
	}
	
	private void makeFilename(String filename) {
		// If filename already exists, create new
		// name -- filename-1 or filename-2...
		int i = 1;
		this.filename = filename;
		while(new File(this.filename).exists()) {
			this.filename = filename + "-" + i;
			i++;
		}
	}
}
