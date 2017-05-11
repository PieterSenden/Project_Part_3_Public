package asteroids.model.exceptions.programExceptions;

/**
 * A class for signaling that there is not enough time to execute an action.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @note This exception may only be caught in a TODO. 
 */


public class HoldException extends RuntimeException {
	/**
	 * Initialize this new HoldException
	 */
	public HoldException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}