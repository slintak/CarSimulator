package simulator.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * Main window of simulator.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class Window extends JFrame {

	public Window(String s) {
		try {
			add(new Board(s));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		String conf = "tests/simulator-gui.conf";
		new Window(conf);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6760755035750911881L;

}
