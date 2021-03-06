package asteroids.model.representation;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import asteroids.model.exceptions.*;
import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.exceptions.programExceptions.NoReturnException;
import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.Program;
import asteroids.model.programs.ProgramExecutor;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular space ship dealing with
 * position, velocity, orientation, radius, density, mass, total mass and bullets.
 * 
 * @invar  The orientation of each ship must be a valid orientation for any ship.
 *       | isValidOrientation(getOrientation())
 * @invar  The minimal radius of each ship must be a valid minimal radius for any ship.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar  The thruster force for this ship is a valid thruster force for any ship.
 *       | isValidThrusterForce(this.getThrusterForce())
 * @invar  Each ship must have proper bullets.
 * 		 | hasProperBullets()
 * @invar  Each ship must have a proper program executor.
 * 		 | hasProperProgramExecutor()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */

public class Ship extends Entity {
	
	/**
	 * Initialize this new ship with given position, velocity , radius, orientation, mass and thruster status.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position this new ship.
	 * @param yComPos
	 * 			The yComponent of the position this new ship.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new ship.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new ship.
	 * @param radius
	 * 			The radius of this new ship.
	 * @param orientation
	 * 			The orientation of this new ship.
	 * @param mass
	 * 			The mass of this new ship.
	 * @param thrusterStatus
	 * 			The thruster status of this new ship.
	 * @effect The super constructor is called to initialize this new ship.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius, mass, MINIMAL_DENSITY, MINIMAL_RADIUS)
	 * @effect The orientation of this new ship is set to the given orientation.
	 * 			| setOrientation(orientation)
	 * @effect The thruster status of this new ship is set to the given thruster status.
	 * 			| setThrust(thrusterStatus)
	 */
	@Raw
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double orientation,
			double mass, boolean thrusterStatus) throws IllegalComponentException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass, MINIMAL_DENSITY, MINIMAL_RADIUS);
		setThrust(thrusterStatus);
		setOrientation(orientation);
	}
	
	/**
	 * Constant registering the minimal density of any ship. 
	 */
	public static final double MINIMAL_DENSITY = 1.42e12;

	/**
	 * Constant registering the minimal radius of any ship. 
	 */
	public static final double MINIMAL_RADIUS = 10;
	
	/**
	 * Initialize this new ship with given position, velocity, radius, orientation and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new ship.
	 * @param yComPos
	 * 			The yComponent of the position of this new ship.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new ship.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new ship.
	 * @param radius
	 * 			The radius of this new ship.
	 * @param orientation
	 * 			The orientation of this new ship.
	 * @param mass
	 * 			The mass of this new ship.
	 * @effect This new ship is initialized with the given position as its position, the given velocity as its velocity,
	 * 			the given radius as its radius, the given orientation as its orientation, the given mass as its mass and 
	 * 			with deactivated thruster.
	 * 			| this(xComPos, yComPos, xComVel, yComVel, radius, orientation, mass, false)
	 */
	@Raw 
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double orientation, double mass) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, orientation, mass, false);
	}
	
	/**
	 * Initialize this new ship with given position, velocity, radius and orientation.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position this new ship.
	 * @param yComPos
	 * 			The yComponent of the position this new ship.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new ship.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new ship.
	 * @param radius
	 * 			The radius of this new ship.
	 * @param orientation
	 * 			The orientation of this new ship.
	 * @effect This new ship is initialized with the given position as its position, the given velocity as its velocity,
	 * 			the given radius as its radius, the given orientation as its orientation and the default mass as its mass.
	 * 			| this(xComPos, yComPos, xComVel, yComVel, radius, orientation, 0)
	 * @note The default mass is the volume of this new ship multiplied by the minimal density of this new ship.
	 */
	@Raw 
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double orientation) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, orientation, 0);
		// The 0 argument for the mass ensures that the mass of this new ship is set to the volume of this new ship
		// times the minimal density of this new ship.
	}
	
	
	/**
	 * Initialize a new ship with given position and radius.
	 * 
	 * @param xComponent
	 * 			The xComponent of the position of this new ship.
	 * @param yComponent
	 * 			The yComponent of the position of this new ship.
	 * @param radius
	 * 			radius of this new ship
	 * @effect This new ship is initialized with the given xComponent and yComponent as its position, the given radius as its radius,
	 * 			zero velocity and right-pointing orientation.
	 * 			| this(xComponent, yComponent, 0, 0, radius, 0)
	 */
	@Raw
	public Ship(double xComPos, double yComPos, double radius) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, 0, 0, radius, 0);
	}
	
	/**
	 * Terminate this ship.
	 * 
	 * @post All loaded bullets have been removed from the magazine of this ship.
	 * 		| new.getMagazine().isEmpty()
	 * @post All fired bullets have been removed from the collection of fired bullets of this ship.
	 * 		| new.getFiredBullets().isEmpty()
	 * @post If the program executor of this ship was effective, said executor is terminated.
	 * 		| if (getProgramExecutor() != null)
	 * 		|	then getProgramExecutor().isTerminated()
	 * @post This new ship has no program executor associated to it.
	 * 		| new.getProgramExecutor() == null
	 * @effect The super method is called to terminate this ship.
	 * 		| super.terminate()
	 */
	@Override
	public void terminate() {
		if (!isTerminated()) {
			Set<Bullet> magazineClone = new HashSet<>(getMagazine());
			for (Bullet bullet: magazineClone) {
				removeBullet(bullet);
			}
			Set<Bullet> firedBulletsClone = new HashSet<>(getFiredBullets());
			for (Bullet bullet: firedBulletsClone) {
				removeBullet(bullet);
			}
			if (getProgramExecutor() != null) {
				getProgramExecutor().terminate();
				setProgramExecutor(null);
			}
			super.terminate();
		}
	}
	
	/**
	 * Set the position of this ship to the given position.
	 * 
	 * @param position
	 * 			The new position for this ship.
	 * @effect The position of this ship is set to the given position.
	 * 		| super.setPosition(position) 
	 * @post Each bullet in the magazine of this ship has the given position as its position.
	 * 		| for each bullet in new.getMagazine():
	 * 		|	(new bullet).getPosition().equals(position)
	 */
	@Override
	protected void setPosition(Position position) throws IllegalPositionException, IllegalComponentException {
		super.setPosition(position);
		if (this.magazine != null) {
			// When initializing this ship, it is possible that magazine == null and we still want to invoke this method.
			for (Bullet bullet: getMagazine()) {
				bullet.setPosition(position);
			}
		}
	}
	
	/**
	 * Return the orientation of this ship in radians.
	 */
	@Basic @Raw
	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Check whether the given orientation is a valid orientation for any ship.
	 * 
	 * @param  orientation
	 *         The orientation to check.
	 * @return true iff the value of orientation is contained in the interval [0, 2*Pi]
	 *       | result == (0 <= orientation) && (orientation <= 2*Math.PI)
	*/
	public static boolean isValidOrientation(double orientation) {
		return (0 <= orientation) && (orientation <= 2*Math.PI);
	}
	
	/**
	 * Turn this ship over a given angle.
	 * 
	 * @param angle
	 * 			The angle over which this ship must be turned.
	 * @effect The new orientation of this ship is set to the current orientation plus the given angle.
	 * 			| setOrientation(getOrientation() + angle)
	 */
	public void turn(double angle) {
		setOrientation(getOrientation() + angle);
	}
	
	/**
	 * Set the orientation of this ship to the given orientation.
	 * 
	 * @param  orientation
	 *         The new orientation for this ship.
	 * @pre    The given orientation must be a valid orientation for any ship.
	 *       | isValidOrientation(orientation)
	 * @post   The orientation of this ship is equal to the given orientation.
	 *       | new.getOrientation() == orientation
	 */	
	@Raw @Model
	private void setOrientation(double orientation) {
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
	/**
	 * Variable registering the orientation of this ship in radians.
	 */
	private double orientation;
	
	/**
	 * Check whether this ship can have the given radius as its radius.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return True iff this ship can have the given radius as its initial radius and
	 * 			the given radius is equal to the initial radius of this ship.
	 * 		  |	super.canHaveAsRadius(radius) && (radius == getInitialRadius())
	 */
	@Override
	public boolean canHaveAsRadius(double radius) {
		return super.canHaveAsRadius(radius) && (radius == getInitialRadius());
	}
	
	/**
	 * Return the total mass of this ship.
	 * The total mass of a ship is the sum of its mass and the mass of the objects carried by that ship.
	 * 
	 * @return The total mass of this ship.
	 * 			| result = getMass() + sum( { x in getMagazine() | true : x.getMass() } )
	 */
	@Override
	public double getTotalMass() {
		double result = getMass();
		for(Bullet bullet: getMagazine()) {
			result += bullet.getMass();
		}
		return result;
	}
	
	/**
	 * Return the thruster force of this ship.
	 */
	@Basic @Raw
	public double getThrusterForce() {
		return this.thrusterForce;
	}
	
	/**
	 * Return the acceleration of this ship.
	 * 
	 * @return 0 if the thruster of this ship is not activated, and the quotient of this ship's thruster force and total mass if the
	 * 				thruster is activated.
	 * 			| if (hasThrusterActivated())
	 * 			|	then result == getThrusterForce() / getTotalMass()
	 * 			| else result == 0
	 */
	@Raw
	public double getAcceleration() {
		if (hasThrusterActivated())
			return getThrusterForce() / getTotalMass();
		else 
			return 0;
	}
	
	/**
	 * Check whether the given thruster force is a valid thruster force for any ship.
	 *  
	 * @param  force
	 *         The thruster force to check.
	 * @return True iff the given thruster force is non-negative.
	 *       | result == (force >= 0)
	 */
	public static boolean isValidThrusterForce(double force) {
		return force >= 0;
	}
	
	/**
	 * Set the thruster force of this ship to the given force.
	 * 
	 * @param force
	 * 			The new thruster force for this ship.
	 * @post If the given force is a valid thruster force for any ship, the new thruster force of this ship is equal to the given force.
	 * 			| if (isValidThrusterForce(force))
	 * 			|	then new.getThrusterForce() == force
	 */
	@Raw
	public void setThrusterForce(double force) {
		if (isValidThrusterForce(force))
			this.thrusterForce = force;
	}
	
	/**
	 * Variable registering the thruster force of this ship.
	 */
	private double thrusterForce = 1.1e18;
	
	
	/**
	 * Return the thruster status of this ship.
	 */
	@Basic @Raw
	public boolean hasThrusterActivated() {
		return thrusterStatus;
	}
	
	/**
	 * Set the thruster status of this ship to the given flag.
	 * 
	 * @param flag
	 * 		The new thruster status of this ship.
	 * @post The new thruster status of this ship is equal to the given flag.
	 * 		| new.hasThrusterActivated() == flag
	 */
	@Raw
	public void setThrust(boolean flag) {
		thrusterStatus = flag;
	}
	
	/**
	 * Activate the thruster of this ship.
	 * 
	 * @effect The thruster status of this ship is set to true.
	 * 			| setThrust(true)
	 */
	@Raw
	public void thrustOn() {
		setThrust(true);
	}
	
	/**
	 * Deactivate the thruster of this ship.
	 * 
	 * @effect The thruster status of this ship is set to false.
	 * 			| setThrust(false)
	 */
	@Raw
	public void thrustOff() {
		setThrust(false);
	}
	
	/**
	 * A variable registering the thruster status of this ship.
	 */
	private boolean thrusterStatus;
	
	/**
	 * Accelerate this ship during the given time interval.
	 * 
	 * @param duration
	 * 		The length of the time interval over which the ship has to be accelerated.
	 * @effect If duration is non-negative, the x-component (resp. y-component) of the new
	 * 			velocity of this ship is set to the sum of the current component
	 * 			plus duration times acceleration of this ship times the cosine (resp. sine) of the orientation of this ship.
	 * 			| if (duration >= 0)
	 * 			|	then setVelocity(getVelocity().getxComponent() + duration * getAcceleration() * Math.cos(getOrientation()),
	 * 			|						getVelocity().getyComponent() + duration * getAcceleration() * Math.sin(getOrientation()))
	 * 			
	 */
	public void thrust(double duration) {
		if (duration >= 0)
			setVelocity(getVelocity().getxComponent() + duration * getAcceleration() * Math.cos(getOrientation()),
					 	getVelocity().getyComponent() + duration * getAcceleration() * Math.sin(getOrientation()));
	}
	
	
	/**
	 * Resolve a collision between this ship and another entity.
	 * 
	 * @param other
	 * 			The entity to resolve a collision with.
	 * @effect	If the other entity is a ship, this ship bounces off the other ship.
	 * 			| if (other instanceof Ship)
	 * 			|	then this.bounceOff(other)
	 * @effect	If the other entity is not a ship, then the collision between the other entity and this ship is resolved.
	 * 			| if (!(other instanceof Ship))
	 * 			|	then other.resolveCollision(this)
	 * @throws IllegalMethodCallException
	 * 			Either this ship or the other entity is not associated to a world, or this ship and the other entity are not associated to
	 * 			the same world or this ship and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			One of the entities is terminated.
	 * 			| this.isTerminated() || other.isTerminated()
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
			this.bounceOff(other);
			//The method bounceOff() only throws an exception under the conditions specified in the throws clauses
			// in the documentation of this method.
		else {
			other.resolveCollision(this);
		}
	}
	
	/**
	 * Check whether, if a collision between this ship and the given other entity occurs, it must be shown.
	 * This method does not check if this ship and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 * @return	True iff the other entity is not a bullet or (the other entity is a bullet and this ship has not fired said bullet)
	 * 			| !(other instanceof Bullet) || !hasFired((Bullet)other)
	 */
	@Override
	public boolean mustShowCollisionWith(Entity other) {
		if (other instanceof Bullet)
			return !hasFired((Bullet)other);
		return true;
	}
	
	/**
	 * Determine whether this ship can be removed from its world.
	 * 
	 * @return	True iff this ship is associated with a world and does not have fired bullets in this world anymore (all associations between
	 * 			this ship and its fired bullets must have been torn down).
	 * 			| result == (getWorld() != null) && (getNbOfFiredBullets() == 0)
	 */
	@Override
	public boolean canBeRemovedFromWorld() {
		if (getWorld() == null)
			return false;
		if (getNbOfFiredBullets() != 0)
			return false;
		return true;
	}
	
	/**
	 * Check whether this ship has loaded this bullet in its magazine.
	 */
	@Basic @Raw
	public boolean hasLoadedInMagazine(@Raw Bullet bullet) {
		return magazine.contains(bullet);
	}
	
	/**
	 * Add the given bullet to the magazine of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to be added to the magazine of this ship.
	 * @post If this ship can have the given bullet as loaded bullet, then the bullet is added to the magazine of this ship.
	 * 		| if (canHaveAsLoadedBullet(bullet))
	 * 		|	then new.hasLoadedInMagazine(bullet)
	 * @throws IllegalBulletException
	 * 		This ship cannot have the given bullet as loaded bullet.
	 * 		| ! canHaveAsLoadedBullet(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void addAsLoadedBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsLoadedBullet(bullet))
			throw new IllegalBulletException();
		this.magazine.add(bullet);
	}
	
	/**
	 * Remove the given bullet from the magazine of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to remove from the magazine of this ship.
	 * @post If the given bullet is effective and is loaded on this ship, then
	 * 			the given bullet is removed from the magazine of this ship.
	 * 		| if (bullet != null && hasLoadedInMagazine(bullet))
	 * 		|	then ! new.hasLoadedInMagazine(bullet)
	 * @throws IllegalBulletException
	 * 			The given bullet is not loaded on this ship.
	 * 		| ! hasLoadedInMagazine(bullet)
	 * @throws TerminatedException
	 * 			This ship is terminated
	 * 			| this.isTerminated()
	 */
	@Raw
	private void removeAsLoadedBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! hasLoadedInMagazine(bullet))
			throw new IllegalBulletException();
		this.magazine.remove(bullet);
	}
	
	/**
	 * Check whether this ship has fired the given bullet. 
	 */
	@Basic @Raw
	public boolean hasFired(@Raw Bullet bullet) {
		return firedBullets.contains(bullet);
	}
	
	/**
	 * Add the given bullet to the collection of fired bullets of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to be added to the collection of fired bullets of this ship.
	 * @post If this ship can have the given bullet as fired bullet, then the bullet is added to the collection of fired bullets of this ship.
	 * 		| if (canHaveAsFiredBullet(bullet))
	 * 		|	then new.hasFired(bullet)
	 * @throws IllegalBulletException
	 * 		This ship cannot have the given bullet as fired bullet.
	 * 		| ! canHaveAsFiredBullet(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void addAsFiredBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsFiredBullet(bullet))
			throw new IllegalBulletException();
		this.firedBullets.add(bullet);
	}
	
	/**
	 * Remove the given bullet from the collection of fired bullets of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to remove from the collection of fired bullets of this ship.
	 * @post If the given bullet is effective and has been fired by this ship, then
	 * 			the given bullet is removed from the collection of fired bullets of this ship.
	 * 		| if (bullet != null && hasFired(bullet))
	 * 		|	then ! new.hasFired(bullet)
	 * @throws IllegalBulletException
	 * 			The given bullet has not been fired by this ship.
	 * 		| ! hasFired(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void removeAsFiredBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! hasFired(bullet))
			throw new IllegalBulletException();
		this.firedBullets.remove(bullet);
	}
	
	/**
	 * Remove the given bullet from this ship.
	 * 
	 * @param bullet
	 * 			The bullet to remove from this ship.
	 * @post	This ship does not contain the given bullet as bullet.
	 * 			| ! new.hasAsBullet(bullet)
	 * @effect	Both the source ship and the containing ship of the given bullet are set to null.
	 * 			| bullet.setContainingShip(null) && bullet.setSourceShip(null)
	 * @throws IllegalArgumentException
	 * 			This ship does not have the given bullet as bullet.
	 * 			| ! hasAsBullet(bullet)
	 * @throws TerminatedException
	 * 			This ship is terminated
	 * 			| this.isTerminated()
	 */
	@Model
	public void removeBullet(Bullet bullet) throws IllegalArgumentException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! hasAsBullet(bullet))
			throw new IllegalArgumentException();
		else {
			if (hasLoadedInMagazine(bullet))
				removeAsLoadedBullet(bullet);
			else if (hasFired(bullet))
				removeAsFiredBullet(bullet);
			bullet.setContainingShip(null);
			bullet.setSourceShip(null);
			//Cannot throw IllegalMethodCallException because at this point certainly !hasAsBullet(bullet).
		}
	}
	
	/**
	 * Check whether this ship is associated to the given bullet.
	 * 
	 * @param bullet
	 * 			The bullet to check.
	 * @return True iff this ship has fired the given bullet or has the given bullet in its magazine.
	 * 			| @see implementation
	 */
	public boolean hasAsBullet(@Raw Bullet bullet) {
		return hasFired(bullet) || hasLoadedInMagazine(bullet);
	}
	
	/**
	 * Check whether this ship can be associated to the given bullet.
	 * 
	 * @param bullet
	 * 		The bullet to check.
	 * @return True iff this ship is not terminated, the given bullet is effective, not terminated, and, if the bullet is associated to a world,
	 * 			this ship must be associated to the same world.
	 * 			| if (this.isTerminated())
	 * 			|	then result == false
	 * 			| if (bullet == null)
	 * 			|	then result == false
	 * 			| if (! canSurround(bullet))
	 * 			|	then result == false
	 * 			| else if (bullet.getWorld() == null)
	 * 			|	then result == true
	 * 			| else if (bullet.getWorld() == this.getWorld())
	 * 			|	then result == true
	 * 			| else
	 * 			|	result == false
	 */
	@Raw
	public boolean canHaveAsBullet(Bullet bullet) {
		return bullet != null && (bullet.getWorld() == null || getWorld() == bullet.getWorld()) && !isTerminated() && canSurround(bullet);
	}
	
	/**
	 * Check whether this ship can have the given bullet in its magazine.
	 * 
	 * @param bullet
	 * 		The bullet to check.
	 * @return True iff this ship can have the given bullet as bullet and the given bullet is not terminated.
	 * 		| @see implementation
	 */
	@Raw
	public boolean canHaveAsLoadedBullet(Bullet bullet) {
		return canHaveAsBullet(bullet) && !bullet.isTerminated();
	}
	
	/**
	 * Check whether this ship can have the given bullet as fired bullet.
	 * 
	 * @param bullet
	 * 		The bullet to check.
	 * @return True iff this ship can have the given bullet as bullet.
	 * 		| @see implementation
	 */
	@Raw
	public boolean canHaveAsFiredBullet(Bullet bullet) {
		return canHaveAsBullet(bullet);
	}
	
	/**
	 * Check whether this ship has proper bullets associated to it.
	 * 
	 * @return If this ship is terminated, true iff there are no bullets in the magazine and the collection of all bullets fired by this ship is empty.
	 * 			| if (this.isTerminated())
	 * 			|	result == (getMagazine().isEmpty() && getFiredBullets().isEmpty())
	 * @return If this ship is not terminated, true iff each bullet in the magazine is effective, has not been fired by this ship, is not associated to any world
	 * 			and references this ship as its containing ship, 
	 * 			and each bullet that has been fired by this ship, is effective, is not loaded in the magazine of this ship,
	 * 			references this ship as its source ship and (is terminated or is associated to the same world as this ship).
	 * 			| if (!this.isTerminated())
	 * 			| 	then result == 
	 * 			| 	 	(for each bullet in getMagazine():
	 * 			|			canHaveAsLoadedBullet(bullet) && bullet.getContainingShip() == this && ! hasFired(bullet) && bullet.getWorld() == null)
	 * 			|		&&
	 * 			|		(for each bullet in getFiredBullets():
	 * 			|			canHaveAsFiredBullet(bullet) && bullet.getSourceShip() == this && ! hasLoadedInMagazine(bullet) && (bullet.isTerminated() || bullet.getWorld() == getWorld()) 			
	 */
	public boolean hasProperBullets() {
		if (!isTerminated()) {
			for (Bullet bullet : getMagazine()) {
				if (!canHaveAsLoadedBullet(bullet) || bullet.getContainingShip() != this || hasFired(bullet)
						|| bullet.getWorld() != null)
					return false;
			}
			for (Bullet bullet : getFiredBullets()) {
				if (!canHaveAsFiredBullet(bullet) || bullet.getSourceShip() != this || hasLoadedInMagazine(bullet)
						|| (!bullet.isTerminated() && bullet.getWorld() != getWorld()))
					return false;
			}
			return true;
		}
		else {
			return (getMagazine().isEmpty() && getFiredBullets().isEmpty());
		}
	}
	
	/**
	 * Return the number of bullets loaded in the magazine of this ship.
	 * 
	 * @return The number of bullets loaded in the magazine of this ship.
	 * 			| @see implementation
	 */
	@Raw
	public int getNbOfBulletsInMagazine() {
		return getMagazine().size();
	}
	
	/**
	 * Return the number of bullets fired by this ship. 
	 */
	@Basic @Raw
	public int getNbOfFiredBullets() {
		return getFiredBullets().size();
	}
	
	/**
	 * Return the magazine with the loaded bullets of this ship.
	 */
	@Basic
	public Set<Bullet> getMagazine() {
		return new HashSet<Bullet>(this.magazine);
	}
	
	/**
	 * Return the set of all fired bullets by this ship.
	 */
	@Basic
	public Set<Bullet> getFiredBullets() {
		return new HashSet<Bullet>(this.firedBullets);
	}
	
	/**
	 * Fire a bullet from the magazine of this ship.
	 * 
	 * @post If this ship is not terminated and if the magazine of this ship is not empty and this ship is contained in a world,
	 * 			then a random bullet randomBullet is removed from the magazine
	 * 			and added to the world containing this ship and hasFired(randomBullet) is true.
	 * 			| if (getNbOfBulletsInMagazine() != 0 && getWorld() != null)
	 * 			|	then for precisely one bullet randomBullet in getMagazine():
	 * 			|		new.hasFired((new randomBullet)) && ! new.hasLoadedInMagazine((new randomBullet) &&
	 * 			|		(new randomBullet).getWorld() == this.getWorld())
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty, said random bullet is set to fire configuration.
	 * 			| randomBullet.setToFireConfiguration()
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty and
	 * 			if said random bullet is placed partially outside the world of this ship after setting it to fire configuration,
	 * 			then that random bullet is immediately destroyed.
	 * 			| if (! getWorld().boundariesSurround((new randomBullet)))
	 * 			|	then (new randomBullet).terminate();
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty and
	 * 			if said random bullet is overlapping with another entity in the world of this ship after setting it to fire configuration,
	 * 			then that random bullet and said entity are immediately destroyed.
	 * 			| if ( for some entity in getWorld().getEntities() : overlap(new randomBullet, entity)) 
	 * 			|	then entity.terminate()
	 * 			|		and (new randomBullet).terminate()
	 * @effect	The source ship of said random bullet is set to the containing ship of said random bullet.
	 * 			| randomBullet.setSourceShip(randomBullet.getContainingShip())
	 * @effect	The containing ship of said random bullet is set to null.
	 * 			| randomBullet.setContainingShip(null)
	 */
	public void fireBullet() {
		if (! this.isTerminated()) {
			if (getNbOfBulletsInMagazine() != 0 && getWorld() != null) {
				Bullet bulletToFire = (Bullet)getMagazine().toArray()[0];
				try {
					bulletToFire.setToFireConfiguration();
					// Cannot throw IllegalPositionException because this bullet is not contained in a world yet.
					removeAsLoadedBullet(bulletToFire);
					// Cannot throw IllegalBulletException, since bulletToFire was loaded in the magazine. 
					Ship containingShip = bulletToFire.getContainingShip();
					bulletToFire.setContainingShip(null);
					addAsFiredBullet(bulletToFire);
					// Cannot throw IllegalBulletException, since canHaveAsLoadedBullet(bulletToFire) was already true by class invariant
					// and canHaveAsLoadedBullet(bulletToFire) implies canHaveAsFiredBullet(bulletToFire).
					bulletToFire.setSourceShip(containingShip);
					try {
						getWorld().addEntity(bulletToFire);
					} catch (IllegalArgumentException exc) {
						//The bullet to fire is placed outside of the boundaries of the world containing this ship. The bullet to fire is
						//therefore destroyed.
						bulletToFire.terminate();
					} catch (OverlapException exc) {
						//The bullet to fire overlaps with another entity in the world of this ship. Both the bullet and that entity are destroyed.
						exc.getFirstEntity().terminate();
						exc.getSecondEntity().terminate();
					}
				} catch (IllegalComponentException exc) {
					/* An IllegalComponentException can only thrown if the method setToFireConfiguration tried to set a component of the position
					 * of the bullet to fire to infinity or NaN. This can never be a valid position inside a world so the bullet to fire must be
					 * destroyed (note that a bullet can only be fired by a ship that is contained in a world).
					 */
					bulletToFire.terminate();
				}
			} 
		}
	}
	
	/**
	 * Load a bullet in the magazine of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to be loaded in the magazine this ship.
	 * @effect The containing ship of the given bullet is set to this ship.
	 * 		| bullet.setContainingShip(this)
	 * @effect The bullet is set to the load configuration.
	 * 		| bullet.setToLoadConfiguration()
	 * @post This ship has the given bullet loaded in its magazine and this ship has not fired this bullet.
	 * 		| new.hasLoadedInMagazine(new bullet) && ! new.hasFired(new bullet)
	 * @post The given bullet is not associated to any world.
	 * 		| (new bullet).getWorld() == null
	 * @throws IllegalBulletException
	 * 			This ship cannot have the given bullet as loaded bullet,
	 * 				or (this ship does not fully surround the given bullet and the given bullet has not been fired by this ship),
	 * 				or (the bullet has been fired by this ship but does not apparently collide with this ship), 
	 * 				or (the ship associated to the given bullet is effective but different from this ship).
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	public void loadBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsLoadedBullet(bullet) || 
				(hasFired(bullet) && ! Entity.apparentlyCollide(this, bullet)) ||
				(bullet.getSourceShip() != null && bullet.getSourceShip() != this) ||
				(!hasFired(bullet) && !surrounds(bullet)))
			throw new IllegalBulletException();
		if (hasFired(bullet))
			removeAsFiredBullet(bullet);
		addAsLoadedBullet(bullet);
		bullet.setSourceShip(null);
		bullet.setContainingShip(this);
		//Cannot throw IllegalMethodCallException because this ship is effective and the given bullet has been loaded in the magazine.
		//Cannot throw TerminatedException because canHaveAsLoadedBullet(bullet) implies !bullet.isTerminated().
		if (bullet.getWorld() != null)
			getWorld().removeEntity(bullet);
			//The method removeEntity() cannot throw an exception because all conditions to throw exceptions are false in this case.
		bullet.setToLoadConfiguration();
	}
	
	/**
	 * Load multiple bullets in the magazine of this ship.
	 * 
	 * @param bullets
	 * 		The bullets to be loaded in the magazine of this ship.
	 * @effect Every single bullet is loaded in the magazine of this ship.
	 * 			| for each bullet in bullets:
	 * 			|	loadBullet(bullet)
	 */
	public void loadBullets(Bullet... bullets) throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		for (Bullet bullet : bullets) {
			loadBullet(bullet);
		}
	}
	
	/**
	 * Set representing the bullets loaded on this ship.
	 * 
	 * @invar The set of bullets loaded on this ship is effective.
	 * 		| magazine != null
	 * @invar Each element in the magazine references a bullet
	 * 			that is an acceptable loaded bullet for this ship.
	 * 		| for each bullet in magazine: canHaveAsLoadedBullet(bullet)
	 * @invar Each bullet in the magazine references this ship as the ship on which it is loaded.
	 * 		| for each bullet in magazine: bullet.getContainingShip() == this
	 */
	private Set<Bullet> magazine = new HashSet<>();
	
	/**
	 * Set representing the bullets fired by this ship.
	 * 
	 * @invar The set of bullets fired by this ship is effective
	 * 		| firedBullets != null
	 * @invar Each element in the set of fired bullets references a bullet
	 * 			that is an acceptable fired bullet for this ship.
	 * 		| for each bullet in firedBullets: canHaveAsFiredBullet(bullet)
	 * @invar Each bullet in the set of fired bullets references this ship
	 * 			as the ship by which it has been fired.
	 * 		| for each bullet in firedBullets: bullet.getSourceShip() == this
	 */
	private Set<Bullet> firedBullets = new HashSet<>();
	
	
	/**
	 * Return the program executor of this ship.
	 */
	@Basic @Raw
	public ProgramExecutor getProgramExecutor() {
		return this.programExecutor;
	}
	
	/**
	 * Check whether this ship can have the given program executor as its program executor.
	 * 
	 * @param programExecutor
	 * 			The program executor to check.
	 * @return True if the given program executor is not effective.
	 * 			| if (programExecutor == null)
	 * 			|	then result == true
	 * @return If the given program executor is effective, the result is true iff this ship is not terminated and the given program executor is not terminated.
	 * 			| if (programExecutor != null)
	 * 			|	then result == isTerminated() ? false : !programExecutor.isTerminated()
	 */
	public boolean canHaveAsProgramExecutor(ProgramExecutor programExecutor) {
		return (programExecutor == null) || (isTerminated() ? false : !programExecutor.isTerminated());
	}
	
	/**
	 * Check whether this ship has a proper program executor associated to it.
	 * 
	 * @return True iff this ship can have its program executor as its program executor and
	 * 			(the program executor of this ship is null or the ship associated to the program executor of this ship, is this ship).
	 * 			| @see implementation
	 */
	public boolean hasProperProgramExecutor() {
		return canHaveAsProgramExecutor(getProgramExecutor()) && (getProgramExecutor() == null || getProgramExecutor().getShip() == this);
	}
	
	
	/**
	 * Set the program executor of this ship to the given program executor.
	 * 
	 * @param programExecutor
	 * 			The new program executor for this ship.
	 * @post The new program executor of this ship is equal to the given program executor.
	 * 			| new.getProgramExecutor() == programExecutor
	 * @throws TerminatedException
	 * 			This ship is terminated.
	 * 			| isTerminated()
	 * @throws IllegalArgumentException
	 * 			This ship cannot have the given program executor as its program executor.
	 * 			| ! canHaveAsProgramExecutor(programExecutor)
	 * @throws IllegalMethodCallException
	 * 			The given program executor is effective, the ship associated to the given program executor is effective but is not this ship.
	 * 			| programExecutor != null && programExecutor.getShip() != null && programExecutor.getShip() != this
	 */
	public void setProgramExecutor(ProgramExecutor programExecutor) throws TerminatedException, IllegalArgumentException,
																			IllegalMethodCallException {
		if (isTerminated())
			throw new TerminatedException();
		if (! canHaveAsProgramExecutor(programExecutor))
			throw new IllegalArgumentException();
		if (programExecutor != null && programExecutor.getShip() != null && programExecutor.getShip() != this)
			throw new IllegalMethodCallException();
		this.programExecutor = programExecutor;
	}
	
	/**
	 * Load the given program on this ship.
	 * 
	 * @param program
	 * 			The program to load on this ship.
	 * @post The program associated to this ship is equal to the given program.
	 * 			| new.getProgram() == program
	 * @throws IllegalArgumentException
	 * 			The given program is not a valid program for any ship.
	 * 			| ! isValidProgram(program)
	 * @throws TerminatedException
	 * 			This ship is terminated.
	 * 			| isTerminated()
	 */
	public void loadProgram(Program program) throws IllegalArgumentException, TerminatedException {
		if (! isValidProgram(program))
			throw new IllegalArgumentException();
		if (isTerminated())
			throw new TerminatedException();
		ProgramExecutor executor = new ProgramExecutor(program);
		setProgramExecutor(executor);
		executor.setShip(this);
	}
	
	/**
	 * Return the program associated to this ship, if any.
	 * 
	 * @return Null if the program executor associated to this ship is not effective.
	 * 			Otherwise, the program associated to the program executor associated to this ship.
	 * 			| @see implementation
	 */
	public Program getProgram() {
		if (getProgramExecutor() == null)
			return null;
		return getProgramExecutor().getProgram();
	}
	
	/**
	 * Check whether the given program is a valid program for any ship.
	 * 
	 * @param program
	 * 			The program to check
	 * @return	True iff the given program is a valid program for any program executor.
	 * 			| result == ProgramExecutor.isValidProgram(program)
	 */
	public static boolean isValidProgram(Program program) {
		return ProgramExecutor.isValidProgram(program);
	}
	
	/**
	 * Execute the program associated to this ship during the given duration.
	 * 
	 * @param duration
	 * 			The time during which the program has to be executed.
	 * @return A list of all values the program associated to this ship has printed out during its execution, if the program has finished executing.
	 * 			Otherwise, null.
	 * 			| if (getProgramExecutor().isProgramFinished())
	 * 			|	then result == getProgramExecutor().getPrintList())
	 * 			| else result == null
	 * @throws IllegalMethodCallException
	 * 			The program executor of this ship is not effective.
	 * 			| getProgramExecutor() == null
	 * @throws TerminatedException
	 * 			This ship is terminated.
	 * 			| isTerminated()
	 */
	public List<Object> executeProgram(double duration) throws HoldException, NullPointerException, IndexOutOfBoundsException,
				BreakException, ReturnException, NoReturnException, IllegalArgumentException, ArithmeticException, IllegalMethodCallException, TerminatedException {
		if (getProgramExecutor() == null)
			throw new IllegalMethodCallException();
		if (isTerminated())
			throw new TerminatedException();
		return getProgramExecutor().executeProgram(duration);
	}
	
	/**
	 * A variable registering the program executor associated to this ship.
	 */
	private ProgramExecutor programExecutor;
}
