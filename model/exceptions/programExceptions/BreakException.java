package asteroids.model.exceptions.programExceptions;

/**
 * A class for signaling break statements.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @note This exception may only be caught in a whileStatement and thrown in a breakStatement. 
 */


public class BreakException extends RuntimeException {
	/**
	 * Initialize this new BreakException
	 */
	public BreakException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}
