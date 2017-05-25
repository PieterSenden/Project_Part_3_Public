package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular bullet dealing with position, velocity, radius, density, mass and number
 * of bounces. A bullet can also be loaded on a ship and fired by that ship.
 * 
 * @invar The minimal radius of each bullet must be a valid minimal radius for any bullet.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar Each bullet can have its number of bounces as number of bounces.
 * 		 | canHaveAsNbOfBounces(getNbOfBounces())
 * @invar Each bullet can have its maximal number of bounces as maximal number of bounces.
 *       | canHaveAsMaximalNbOfBounces(this.getMaximalNbOfBounces())
 * @invar Each bullet must have a proper containing ship.
 * 		 | hasProperContainingShip()
 * @invar Each bullet must have a proper source ship.
 * 		 | hasProperSourceShip()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 */

public class Bullet extends Entity {
	/**
	 * Initialize this new Bullet with given position, velocity, radius and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new bullet.
	 * @param yComPos
	 * 			The yComponent of the position of this new bullet.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new bullet.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new bullet.
	 * @param radius
	 * 			The radius of this new bullet.
	 * @param mass
	 * 			The mass of this new bullet.
	 * @effect	| super(xComPos, yComPos, xComVel, yComVel, radius, mass, MINIMAL_DENSITY, MINIMAL_RADIUS)
	 */
	@Raw
	public Bullet(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double mass) throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass, MINIMAL_DENSITY, MINIMAL_RADIUS);
	}
	
	/**
	 * Initialize this new Bullet with given position, velocity and radius.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new bullet.
	 * @param yComPos
	 * 			The yComponent of the position of this new bullet.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new bullet.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new bullet.
	 * @param radius
	 * 			The radius of this new bullet.
	 * @effect	| this(xComPos, yComPos, xComVel, yComVel, radius, 10e20)
	 */
	@Raw
	public Bullet(double xComPos, double yComPos, double xComVel, double yComVel, double radius) throws IllegalComponentException, 
																				IllegalPositionException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, 10e20);
		//The value of the mass appears to be set to 10e20. However, this value is changed to the only possible mass for a bullet
		//with a given radius in the constructor of Entity.
	}
	
	/**
	 * A constant registering the minimal density for any bullet.
	 */
	public static final double MINIMAL_DENSITY = 7.8e12;
	
	/**
	 * A constant registering the minimal radius for any bullet. 
	 */
	public static final double MINIMAL_RADIUS = 1;
	
	/**
	 * Terminate this bullet.
	 * 
	 * @effect	| if (!isTerminated())
	 * 			| 	then super.terminate()
	 * @effect	| if (!isTerminated() && getContainingShip != null)
	 * 			|	then getContainingShip().removeBullet(this)
	 */
	@Override
	public void terminate() {
		if (!isTerminated()) {
			if (getContainingShip() != null)
				getContainingShip().removeBullet(this);
			super.terminate();
		}
	}
	
	/** 
	 * Check whether this bullet can have the given density as its density.
	 * 
	 * @return True iff the given density is equal to getMinimalDensity()
	 * 			| @see implementation
	 */
	@Override @Raw
	public boolean canHaveAsDensity(double density) {
		return density == getMinimalDensity();
	}
	
	/**
	 * Check whether this bullet can have the given radius as its radius.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return True iff this bullet can have the given radius as its initial radius and
	 * 			the given radius is equal to the initial radius of this bullet.
	 * 		  |	super.canHaveAsRadius(radius) && (radius == getInitialRadius())
	 */
	@Override
	public boolean canHaveAsRadius(double radius) {
		return super.canHaveAsRadius(radius) && (radius == getInitialRadius());
	}
	
	/**
	 * Return the number of bounces of this bullet against a boundary of its world.
	 * 
	 * If a bullet is not associated to a world, this value will be equal to zero. Also, if a bullet is loaded on a ship, this value is
	 * reset to zero.
	 */
	@Basic @Raw
	public int getNbOfBounces() {
		return nbOfBounces;
	}
	
	/**
	 * Check whether this bullet can have its number of bounces as number of bounces.
	 * 
	 * @param number
	 * 			The number to check.
	 * @return | @see implementation.
	 */
	@Raw
	public boolean canHaveAsNbOfBounces(int number) {
		return (0 <= number) && (number <= getMaximalNbOfBounces());
	}
	
	/**
	 * Set the number of bounces of this bullet to the given number.
	 * 
	 * @param number
	 * 			The new number of bounces for this bullet.
	 * @post	| new.getNbOfBounces() == number
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 * @throws IllegalArgumentException
	 * 			| ! canHaveAsNbOfBounces(number)
	 */
	private void setNbOfBounces(int number) throws TerminatedException, IllegalArgumentException {
		if (isTerminated())
			throw new TerminatedException();
		if (!canHaveAsNbOfBounces(number))
			throw new IllegalArgumentException();
		nbOfBounces = number;
	}
	
	/**
	 * Increment the number of bounces of this bullet with one.
	 * 
	 * @effect	| setNbOfBouces(getNbOfBounces() + 1)
	 */
	@Model
	private void stepNbOfBounces() throws TerminatedException, IllegalArgumentException {
		setNbOfBounces(getNbOfBounces() + 1);
	}
	
	/**
	 * Reset the number of bounces of this bullet.
	 * 
	 * @effect	| setNbOfBounces(0)
	 */
	private void resetNbOfBounces() throws TerminatedException {
		setNbOfBounces(0);
	}
	
	/**
	 * Variable registering the number of times this bullet has collided with the boundary of its world.
	 */
	private int nbOfBounces = 0;
	
	
	/**
	 * Return the maximal number of bounces of this bullet.
	 * 
	 * If this bullet has reached its maximal number of bounces and collides with the boundary of its world, this bullet is destroyed.
	 */
	@Basic @Raw @Immutable
	public int getMaximalNbOfBounces() {
		return this.maximalNbOfBounces;
	}
	
	/**
	 * Check whether this bullet can have the given maximal number of bounces as its maximal number of bounces.
	 *  
	 * @param  maximalNbOfBounces
	 *         The maximal number of bounces to check.
	 * @return 
	 *       | result == (0 <= maximalNbOfBounces)
	*/
	@Raw
	public boolean canHaveAsMaximalNbOfBounces(int maximalNbOfBounces) {
		return 0 <= maximalNbOfBounces;
	}
	
	/**
	 * Variable registering the maximal number of bounces of this bullet.
	 */
	private final int maximalNbOfBounces = 2;
	
	
	/**
	 * Make this bullet bounce off the boundary of its world.
	 * 
	 * @effect	| if (getNbOfBouces() >= getMaximalNbOfBounces())
	 * 			|	then terminate()
	 * @effect	| if (getNbOfBouces() < getMaximalNbOfBounces())
	 * 			|	then (stepNbOfBounces() && super.bounceOffBoundary())
	 * @throws	TerminatedException
	 * 			| isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			| getWorld() == null || !apparentlyCollidesWithBoundary()
	 */
	@Override
	public void bounceOffBoundary() throws TerminatedException, IllegalMethodCallException {
		if (isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || !apparentlyCollidesWithBoundary())
			throw new IllegalMethodCallException();
		if (getNbOfBounces() >= getMaximalNbOfBounces())
			terminate();
		else {
			stepNbOfBounces();
			super.bounceOffBoundary();
		}
	}
	
	/**
	 * Resolve a collision between a bullet and another entity.
	 * 
	 * @param other
	 * 			The entity to resolve a collision with.
	 * @effect	| if (other instanceof Ship)
	 * 			|	then if ((Ship)other).hasFired(this))
	 * 			|		then ((Ship)other).loadBullet(this)
	 * 			|	else
	 * 			|		this.terminate() && other.terminate()
	 * 			| else
	 * 			|	this.terminate() && other.terminate()
	 * @throws	TerminatedException
	 * 			| this.isTerminated() || other.isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws NullPointerException
	 * 			The given other entity is not effective.
	 * 			| other == null
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException, NullPointerException {
		if (isTerminated() || other.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || getWorld() != other.getWorld() || !Entity.apparentlyCollide(this, other))
			throw new IllegalMethodCallException();
		if (other instanceof Ship)
			if (((Ship)other).hasFired(this))
				((Ship)other).loadBullet(this);
			else {
				terminate();
				other.terminate();
			}
		else {
			terminate();
			other.terminate();
		}
	}
	
	/**
	 * Check whether, if a collision between this entity and the given other entity occurs, it must be shown.
	 * This method does not check if this entity and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 * @return	| !(other instanceof Ship) || !((Ship)other).hasFired(this)
	 */
	@Override
	public boolean mustShowCollisionWith(Entity other) {
		if (other instanceof Ship)
			return !((Ship)other).hasFired(this);
		return true;
	}
	
	
	/**
	 * Determine whether this bullet can be removed from its world.
	 * 
	 * @return	| result == (getWorld() != null)
	 */
	@Override
	public boolean canBeRemovedFromWorld() {
		if (getWorld() == null)
			return false;
		return true;
	}
	
	/**
	 * Return the ship containing this bullet in its magazine.
	 */
	@Basic @Raw
	public Ship getContainingShip() {
		return this.containingShip;
	}
	
	/**
	 * Return the ship that fired this bullet.
	 */
	@Basic @Raw
	public Ship getSourceShip() {
		return this.sourceShip;
	}
	
	/**
	 * Check whether this bullet can have the given ship as its containing ship.
	 * 
	 * @param ship
	 * 		The ship to check.
	 * @return | result == (ship == null) || (ship.canHaveAsLoadedBullet(this) && getWorld() == null)
	 */
	@Raw
	public boolean canHaveAsContainingShip(Ship ship) {
		return (ship == null) || (ship.canHaveAsLoadedBullet(this) && getWorld() == null);
	}
	
	/**
	 * Check whether this bullet can have the given ship as its source ship.
	 * 
	 * @param ship
	 * 		The ship to check.
	 * @return | result == ((ship == null) || ship.canHaveAsFiredBullet(this))
	 */
	@Raw
	public boolean canHaveAsSourceShip(Ship ship) {
		return ((ship == null) || ship.canHaveAsFiredBullet(this));
	}
	
	/**
	 * Check whether this bullet has a proper containing ship.
	 * 
	 * @return	| if (canHaveAsContainingShip(getContainingShip())
	 * 			|	if (getContainingShip() == null)
	 * 			|		then result == true
	 * 			|	else
	 * 			|		then result == getContainingShip().hasLoadedInMagazine(this) && getSourceShip() == null
	 * 			| else
	 * 			|	then result == false
	 */
	@Raw
	public boolean hasProperContainingShip() {
		if (! canHaveAsContainingShip(getContainingShip()) )
			return false;
		else if (getContainingShip() == null)
			return true;
		else
			return getContainingShip().hasLoadedInMagazine(this) && getSourceShip() == null;
	}
	
	/**
	 * Check whether this bullet has a proper source ship.
	 * 
	 * @return 	| if (canHaveAsSourceShip(getSourceShip())
	 * 			|	if (getSourceShip() == null)
	 * 			|		then result == true
	 * 			|	else
	 * 			|		then result == getSourceShip().hasFired(this) && getContainingShip() == null
	 * 			| else
	 * 			|	then result == false
	 * 
	 */
	@Raw
	public boolean hasProperSourceShip() {
		if (! canHaveAsSourceShip(getSourceShip()) )
			return false;
		else if (getSourceShip() == null)
			return true;
		else
			return getSourceShip().hasFired(this) && getContainingShip() == null;
	}
	
	/**
	 * Set this bullet to fire configuration.
	 * 
	 * @post | if (!isTerminated() && getContainingShip() != null && getContainingShip().hasLoadedInMagazine(this))
	 * 		 | 	then Entity.getDistanceBetweenCentres(new, getContainingShip()) == (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getContainingShip())
	 * 		 | 		&& (new.getPosition().getyComponent() - getContainingShip().getPosition().getyComponent() ==
	 * 		 |			Math.tan(getContainingShip().getOrientation()) * ((new.getPosition().getxComponent() - getContainingShip().getPosition().getxComponent() )
	 * 		 |		&& (new.getVelocity().getSpeed() == 250 && new.getVelocity().getyComponent() ==
	 * 		 |			Math.tan(getContainingShip().getOrientation()) * new.getVelocity().getxComponent()
	 * @note This method must only be invoked in the method fireBullet() of the class Ship
	 * @throws IllegalPositionException
	 * 		 | let
	 * 		 |	newDistanceBetweenCentres = (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getContainingShip());
	 *		 |	angle = getContainingShip().getOrientation();
	 *		 | in
	 *		 | 	! canHaveAsPosition(new Position(getContainingShip().getPosition().getxComponent() + newDistanceBetweenCentres * Math.cos(angle),
	 *		 |		getContainingShip().getPosition().getyComponent() + newDistanceBetweenCentres * Math.sin(angle)));
	 */
	@Model
	void setToFireConfiguration() throws IllegalPositionException {
		if (!isTerminated() && getContainingShip() != null && getContainingShip().hasLoadedInMagazine(this)) {
			double newDistanceBetweenCentres = (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getContainingShip());
			double angle = getContainingShip().getOrientation();
			setPosition(getContainingShip().getPosition().getxComponent() + newDistanceBetweenCentres * Math.cos(angle),
					getContainingShip().getPosition().getyComponent() + newDistanceBetweenCentres * Math.sin(angle));
			setVelocity(250 * Math.cos(angle), 250 * Math.sin(angle));
		}
	}
	
	/**
	 * Set this bullet to the load configuration.
	 * 
	 * @post | if (!isTerminated() && (getShip() != null) && getShip().hasLoadedInMagazine(this))
	 * 		 | 	then new.getPosition().equals(getShip().getPosition()) &&
	 * 		 |			new.getVelocity().equals(new Velocity(0, 0)) && (new.getNbOfBounces() == 0)
	 * @note This method must only be invoked in the method loadBullet() of the class Ship
	 */
	@Model
	void setToLoadConfiguration() {
		if (!isTerminated() && (getContainingShip() != null) && getContainingShip().hasLoadedInMagazine(this)) {
			//This method must only be invoked in the method loadBullet() of the class Ship, therefore the containing ship of this bullet
			//is already set.
			setPosition(getContainingShip().getPosition());
			//Cannot throw IllegalComponentException or IllegalPositionException because getContainingShip() is on a legal position (and if this
			//bullet is associated to a world, getContainingShip() is associated to the same world because of the class invariant).
			setVelocity(0, 0);
			resetNbOfBounces();
		}
	}
	
	/**
	 * Set the containing ship of this bullet to the given ship.
	 * 
	 * @param ship
	 * 		The new containing ship for this bullet.
	 * @post    | new.getContainingShip() == ship
	 * @throws IllegalMethodCallException
	 * 			| ( (ship != null && (! ship.hasLoadedInMagazine(this) || getSourceShip() != null)) ||
				|	(ship == null && getContainingShip() != null && getContainingShip().hasLoadedInMagazine(this)))
	 * @note If this method is invoked with an effective ship and does not throw an exception,
	 * 			then the world of this bullet must be set to null.
	 */
	@Raw @Model
	void setContainingShip(Ship ship) throws IllegalMethodCallException {
		if (isTerminated() && ship != null)
			throw new TerminatedException();
		if ((ship != null && (!ship.hasLoadedInMagazine(this) || getSourceShip() != null)) ||
				(ship == null && getContainingShip() != null && getContainingShip().hasLoadedInMagazine(this)))
			throw new IllegalMethodCallException();
		this.containingShip = ship;
	}
	
	/**
	 * Set the source ship of this bullet to the given ship.
	 * 
	 * @param ship
	 * 		The new ship for this bullet.
	 * @post    | new.getSourceShip() == ship
	 * @throws IllegalMethodCallException
	 * 			| ( (ship != null && (! ship.hasFired(this) || getContainingShip() != null)) ||
	 *			|	(ship == null && getSourceShip() != null && getSourceShip().hasFired(this)))
	 */
	@Raw @Model
	void setSourceShip(Ship ship) throws IllegalMethodCallException {
		if (isTerminated() && ship != null)
			throw new TerminatedException();
		if ((ship != null && (!ship.hasFired(this) || getContainingShip() != null)) ||
				(ship == null && getSourceShip() != null && getSourceShip().hasFired(this)))
			throw new IllegalMethodCallException();
		this.sourceShip = ship;
	}
	
	/**
	 * A variable registering the ship in which this bullet is contained.
	 */
	private Ship containingShip;
	
	/**
	 * A variable registering the ship by which this bullet was fired.
	 */
	private Ship sourceShip;
}