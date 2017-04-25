package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular minor planet dealing with
 * position, velocity, radius, density and mass.
 * 
 * @invar  The minimal radius of each minor planet must be a valid minimal radius for any minor planet.
 *       | isValidMinimalRadius(getMinimalRadius())
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 */

public abstract class MinorPlanet extends Entity {
	
	/**
	 * Initialize this new minor planet with given position, velocity, radius and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position this new minor planet.
	 * @param yComPos
	 * 			The yComponent of the position this new minor planet.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new minor planet.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new minor planet.
	 * @param radius
	 * 			The radius of this new minor planet.
	 * @param mass
	 * 			The mass of this new minor planet.
	 * @effect This new minor planet is initialized with the given position as its position, the given velocity as its velocity,
	 * 			the given radius as its radius and the given mass as its mass.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius, mass)
	 */
	// TODO: specs in orde brengen.
	@Raw 
	public MinorPlanet(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double mass, double minimalDensity)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass, minimalDensity, MINIMAL_RADIUS);
	}
	
	/**
	 * Constant registering the minimal radius of any minor planet.
	 */
	private static double MINIMAL_RADIUS = 5;
	
	/**
	 * Return a copy of this minor planet.
	 * 
	 * @return A copy of this minor planet.
	 * @throws TerminatedException
	 * 			This minor planet is terminated.
	 * 			| this.isTerminated()
	 */
	@Override
	public abstract MinorPlanet copy() throws TerminatedException;
	
	/**
	 * Check whether this minor planet can have the given density as its density.
	 *  
	 * @param  density
	 *         The density to check.
	 * @return True iff the given density is equal to getMinimalDensity().
	 *       | result == (density == getMinimalDensity())
	 */
	@Override
	public boolean canHaveAsDensity(double density) {
		return density == getMinimalDensity();
	}
	
	/**
	 * Check whether this minor planet can have the given radius as its initial radius.
	 * 
	 * @param radius
	 * 		  The radius to check.
	 * @return True iff the given radius is greater than or equal to the minimal radius of this minor planet.
	 * 			| result == (radius >= getMinimalRadius())
	 */
	@Override
	public boolean canHaveAsInitialRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	
	/**
	 * Resolve a collision between this minor planet and another entity.
	 * 
	 * @throws IllegalMethodCallException
	 * 			Either this minor planet or the other entity is not associated to a world, this minor planet and the other entity are not associated
	 *			to the same world or this minor planet and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			This minor planet or the other entity is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	@Override
	public abstract void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException;
	
	/**
	 * Check whether, if a collision between this minor planet and the given other entity occurs, it must be shown.
	 * This method does not check if this minor planet and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 * @return	Always true.
	 * 			| result == true
	 * 
	 */
	@Override
	public boolean mustShowCollisionWith(Entity other) {
		return true;
	}
	
	/**
	 * Determine whether this minor planet can be removed from its world.
	 * 
	 * @return	True iff this minor planet is associated with a world
	 * 			| result == (getWorld() != null)
	 */
	@Override
	public boolean canBeRemovedFromWorld() {
		return getWorld() != null;
	}
}