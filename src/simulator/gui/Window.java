package simulator.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import simulator.agents.ShoulderAgent;
import simulator.core.Simulator;

public class Window extends JFrame {

	public Window(Simulator s) {
		add(new Board(s));
        setTitle("Highway Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShoulderAgent a = new ShoulderAgent();
		Simulator s = null;
		try {
			s = new Simulator("tests/simulator.conf", a);
		} catch (FileNotFoundException e) {
			System.out.println("Configuration file not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Problem when reading configuration file!");
			e.printStackTrace();
		}
		
		new Window(s);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6760755035750911881L;

}
