package simulator.exceptions;

/**
 * This exception is thrown when agent ride beyond left/right
 * shoulder in highway.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class NotLaneException extends Exception {
	private static final long serialVersionUID = 2396876689765643097L;

	public NotLaneException() { }

	public NotLaneException(String msg) {
	    super(msg);
	}
}
