package asteroids.model.exceptions.programExceptions;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling return statements.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @note This exception may only be caught during the execution of a function and thrown in a ReturnStatement. 
 */


public class ReturnException extends RuntimeException {
	/**
	 * Initialize this new ReturnException
	 * 
	 * @param returnValue
	 * 			The value to return.
	 */
	public ReturnException(Object returnValue) {
		super();
		this.returnValue = returnValue;
	}
	
	@Basic
	public Object getReturnValue() {
		return returnValue;
	}
	
	private final Object returnValue;
	
	private static final long serialVersionUID = 1L;
}