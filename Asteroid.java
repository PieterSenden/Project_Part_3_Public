package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.IllegalPositionException;
import asteroids.model.exceptions.IllegalRadiusException;
import asteroids.model.exceptions.TerminatedException;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing a circular asteroid dealing with
 * position, velocity, radius, density and mass.
 * 
 * @invar  The minimal radius of each asteroid must be a valid minimal radius for any asteroid.
 *       | isValidMinimalRadius(getMinimalRadius())
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */

public class Asteroid extends MinorPlanet {
	
	/**
	 * Initialize this new asteroid with given position, velocity, radius and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position this new asteroid.
	 * @param yComPos
	 * 			The yComponent of the position this new asteroid.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new asteroid.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new asteroid.
	 * @param radius
	 * 			The radius of this new asteroid.
	 * @param mass
	 * 			The mass of this new asteroid.
	 * @effect This new asteroid is initialized with the given position as its position, the given velocity as its velocity,
	 * 			the given radius as its radius and the given mass as its mass.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius, mass)
	 */
	@Raw 
	public Asteroid(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double mass)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass);
	}
	
	/**
	 * Return a copy of this asteroid.
	 * 
	 * @return A copy of this bullet.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 */
	@Override
	public Asteroid copy() throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		return new Asteroid(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius(), getMass());
	}

	@Override
	public double getMinimalDensity() {
		return minimalDensity;
	}
		
		
	/**
	 * Variable registering the minimal density of this ship. 
	 */
	private final double minimalDensity = 1.42e12;

	@Override
	public boolean canHaveAsRadius(double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException {
		// TODO Auto-generated method stub

	}

}
