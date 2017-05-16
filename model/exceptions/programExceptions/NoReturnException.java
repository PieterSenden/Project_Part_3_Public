package asteroids.model.exceptions.programExceptions;


/**
 * A class for signaling the absence of a return statement in a function definition.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *  
 */

public class NoReturnException extends RuntimeException {
	
	/**
	 * Initialize this new ReturnException
	 */
	public NoReturnException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}
