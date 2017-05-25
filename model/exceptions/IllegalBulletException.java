package asteroids.model.exceptions;

/**
 * A class for signaling exceptions regarding bullets.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 */
public class IllegalBulletException extends RuntimeException {
	/**
	 * Initialize this new IllegalBulletException.
	 */
	public IllegalBulletException() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
}