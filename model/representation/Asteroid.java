package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing a circular asteroid dealing with
 * position, velocity, radius, density and mass.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */

public class Asteroid extends MinorPlanet {
	
	/**
	 * Initialize this new asteroid with given position, velocity and radius.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new asteroid.
	 * @param yComPos
	 * 			The yComponent of the position of this new asteroid.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new asteroid.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new asteroid.
	 * @param radius
	 * 			The radius of this new asteroid.
	 * @effect This new asteroid is initialized with the given position as its position, the given velocity as its velocity and
	 * 			the given radius as its radius.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius)
	 */
	@Raw 
	public Asteroid(double xComPos, double yComPos, double xComVel, double yComVel, double radius)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, MINIMAL_DENSITY);
	}
	
	/**
	 * Initialize this new asteroid with given position, velocity and radius.
	 * 
	 * @param position
	 * 			The position of this new asteroid.
	 * @param velocity
	 * 			The velocity of this new asteroid.
	 * @param radius
	 * 			The radius of this new asteroid.
	 * @effect This new asteroid is initialized with the given position as its position, the given velocity as its velocity and
	 * 			the given radius as its radius.
	 * 			| this(position.getxComponent(), position.getyComponent(), velocity.getxComponent(), velocity.getyComponent(), radius)
	 * @throws NullPointerException
	 * 			The given position or the given velocity is not effective.
	 * 			| position == null || velocity == null
	 */
	@Raw 
	public Asteroid(Position position, Velocity velocity, double radius)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException, NullPointerException {
		this(position.getxComponent(), position.getyComponent(), velocity.getxComponent(), velocity.getyComponent(), radius);
	}
	
	/**
	 * Constant registering the minimal density of any asteroid. 
	 */
	public static final double MINIMAL_DENSITY = 2.65e12;
	
//	/**
//	 * Return a copy of this asteroid.
//	 * 
//	 * @return A copy of this asteroid.
//	 * 			| @see implementation
//	 * @throws TerminatedException
//	 * 			| this.isTerminated()
//	 */
//	@Override
//	public Asteroid copy() throws TerminatedException {
//		if (isTerminated())
//			throw new TerminatedException();
//		return new Asteroid(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
//				getVelocity().getyComponent(), getRadius());
//	}
	

	/**
	 * Check whether this asteroid can have the given radius as its radius.
	 * 
	 * @param  radius
	 *         The radius to check.
	 * @return true iff this asteroid can have the given radius as its initial radius and the given radius is equal to the initial radius
	 * 			of this asteroid.
	 * 			| super.canHaveAsRadius(radius) && (radius == getInitialRadius())
	 */
	@Override
	public boolean canHaveAsRadius(double radius) {
		return super.canHaveAsRadius(radius) && (radius == getInitialRadius());
	}
	
	/**
	 * Resolve a collision between this asteroid and another entity.
	 * 
	 * @param  other
	 * 			The entity to resolve a collision with.
	 * @effect	| super.resolveCollision(other)
	 * @effect	| if (other instanceof Ship)
	 * 			|	then other.terminate()
	 * @effect	| if (!(other instanceof Ship) && !(other instanceof Bullet) && !(other instanceof MinorPlanet))
	 * 			|	then other.resolveCollision(this)
	 * @throws IllegalMethodCallException
	 * 			Either this asteroid or the other entity is not associated to a world, this asteroid and the other entity are not associated
	 *			to the same world or this asteroid and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			This asteroid or the other entity is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 * @throws NullPointerException
	 * 			The given other entity is not effective.
	 * 			| other == null
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException, NullPointerException {
		super.resolveCollision(other);
		if (other instanceof Ship)
			other.terminate();
		else if (! (other instanceof Bullet) && ! (other instanceof MinorPlanet))
			other.resolveCollision(this);
	}
	
}
