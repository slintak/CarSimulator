package simulator.exceptions;

/**
 * When agent crash into other car, <code>OnCrashException</code> is thrown.
 * 
 * @author Vlastimil Slintak, xslint01@stud.feec.vutbr.cz
 *
 */
public class OnCrashException extends Exception {
	private static final long serialVersionUID = 2069710674738542570L;

	public OnCrashException() { }

	public OnCrashException(String msg) {
	    super(msg);
	}
}
