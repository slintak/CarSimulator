package simulator.exceptions;

/**
 * When agent returns wrong action in <code>AgentActions</code>,
 * this exception is thrown.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class WrongActionException extends Exception {
	private static final long serialVersionUID = 4814893117728913005L;

	public WrongActionException() { }

	public WrongActionException(String msg) {
		super(msg);
	}
}
