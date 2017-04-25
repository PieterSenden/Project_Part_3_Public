package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing an entity floating in outer space.
 * 
 * @invar  Each entity can have its position as position.
 *       	| canHaveAsPosition(this.getPosition())
 * @invar  Each entity can have its velocity as velocity.
 *       	| canHaveAsVelocity(this.getVelocity())
 * @invar  Each entity can have its density as density.
 *       	| canHaveAsDensity(this.getDensity())
 * @invar  Each entity can have its radius as radius.
 *       	| canHaveAsRadius(this.getRadius())
 * @invar  Each entity can have its initial radius as initial radius.
 * 			| canHaveAsInitialRadius(this.getInitialRadius())  
 * @invar  The speed limit of this entity is a valid speed limit for any entity.
 *     	    | isValidSpeedLimit(this.getSpeedLimit())
 * @invar  Each entity has a proper world.
 * 			| hasProperWorld()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 */

public abstract class Entity {
	
	/**
	 * Initialize this new entity with given position, velocity, radius and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new entity.
	 * @param yComPos
	 * 			The yComponent of the position of this new entity.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new entity.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new entity.
	 * @param radius
	 * 			The radius of this new entity.
	 * @param mass
	 * 			The mass of this new entity.
	 * @effect The position of this new entity is set to the position with given xComponent and yComponent.
	 * 			| setPosition(xComPos, yComPos)
	 * @effect The velocity of this new entity is set to the velocity with given xComponent and yComponent.
	 * 			| setVelocity(xComVel, yComVel)
	 * @post The radius of this new entity is equal to the given radius.
	 * 			| new.getRadius() == radius
	 * @effect If this entity can have the density given by the given mass divided by the volume of this new entity as its density, then 
	 * 			the density of this new entity is set to the density given by the given mass divided by the volume of this new entity.
	 * 		   Else, the density of this new entity is set to getMinimalDensity() 
	 * 			| if canHaveAsDensity(mass / new.getVolume())
	 * 			|	then setDensity(mass / new.getVolume())
	 * 			| else setDensity(getMinimalDensity())
	 * @throws IllegalRadiusException
	 * 			The given radius is not a valid radius for any entity
	 * 			| ! isValidRadius(radius)
	 */
	//TODO : specs in orde brengen.
	public Entity(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double mass, double minimalDensity, double minimalRadius)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		setPosition(xComPos, yComPos);
		setVelocity(xComVel, yComVel);
		
		if (! canHaveAsMinimalRadius(minimalRadius))
			throw new IllegalRadiusException();
		this.minimalRadius = minimalRadius;
		if (! canHaveAsInitialRadius(radius))
			throw new IllegalRadiusException();
		this.initialRadius = radius;
		setRadius(radius);
		if (! canHaveAsMinimalDensity(minimalDensity))
			minimalDensity = Double.isFinite(minimalDensity) ? -minimalDensity : 1;
		this.minimalDensity = minimalDensity;
		double density = mass / getVolume(); 
		if (! canHaveAsDensity(density))
			density = getMinimalDensity();
		setDensity(density);
	}
	
	/**
	 * Return a copy of this entity.
	 * 
	 * @return A copy of this entity
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public abstract Entity copy() throws TerminatedException;
	
	/**
	 * Terminate this entity.
	 *
	 * @post This entity is terminated.
	 *       | new.isTerminated()
	 * @effect If this entity is associated to a world, this entity is removed from its world.
	 * 		 | @see implementation
	 */
	public void terminate() {
		if (!isTerminated()) {
			if (getWorld() != null) {
				getWorld().removeEntity(this);
			}
			this.isTerminated = true;
		}
	}
	
	/**
	 * Return a boolean indicating whether or not this entity is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether this entity is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Variable registering an accuracy factor.
	 */
	public static final double ACCURACY_FACTOR = 0.99;
	
	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		if (this.position == null)
			return null;
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for this entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return true iff the given position is effective and this entity can have the xComponent and yComponent of the given position
	 * 			as the xComponent and yComponent of its position.
	 *       | result == (position != null) && canHaveAsPosition(position.getxComponent(), position.getyComponent())
	 */
	public boolean canHaveAsPosition(Position position) {
		if (position == null)
			return false;
		return canHaveAsPosition(position.getxComponent(), position.getyComponent());
	}
	
	/**
	 * Check whether the given components can be components of a valid position for this entity.
	 * 
	 * @param xComponent
	 * 			The xComponent to check.
	 * @param yComponent
	 * 			The yComponent to check.
	 * @return true iff the world of this entity is not effective or (the world of this entity is effective and
	 * 			the world of this entity fully surrounds this entity.
	 * 			| @see implementation
	 */
	public boolean canHaveAsPosition(double xComponent, double yComponent) {
		if (getWorld() == null)
			return true;
		return getWorld().boundariesSurround(this);
	}
	
	
	/**
	 * Move this entity during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @effect The new position of this entity is set to the position that is the result of the position of this entity moved with
	 * 			the velocity of this entity and during the given duration.
	 * 			| @see implementation
	 * @effect If this entity is contained in a world, the position of this entity in its world is updated.
	 * 			| if (getWorld() != null)
	 * 			|	then getWorld().updatePosition(this)
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 * @throws TerminatedException
	 * 			This entity is terminated
	 * 			| this.isTerminated()
	 */
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException, TerminatedException,
															IllegalPositionException {
		if (isTerminated())
			throw new TerminatedException();
		setPosition(getPosition().move(getVelocity(), duration));
		if (getWorld() != null){
			getWorld().updatePosition(this);
		}
	}
	
	
	/**
	 * Set the position of this entity to the position with given components.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the position for this entity.
	 * @param  yComponent
	 *         The new yComponent for the position for this entity.
	 * @effect The position of this entity is set to the position with the given components.
	 * 			| @see implementation
	 */
	@Raw @Model
	protected void setPosition(double xComponent, double yComponent) throws IllegalComponentException, IllegalPositionException {
		setPosition(new Position(xComponent, yComponent));
	}
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param position
	 * 			The new position for this entity.
	 * @post The new position of this entity is equal to the given position.
	 * 		 | new.getPosition().equals(position)
	 * @throws IllegalPositionException
	 * 			This entity cannot have this position as its position.
	 * 		 | ! canHaveAsPosition(position)
	 */
	@Raw @Model
	protected void setPosition(Position position) throws IllegalPositionException {
		if (!canHaveAsPosition(position))
			throw new IllegalPositionException();
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private Position position;
	
	
	/**
	 * Return the velocity of this entity.
	 */
	@Basic @Raw
	public Velocity getVelocity() {
		if (this.velocity == null)
			return null;
		return this.velocity;
	}
	
	/**
	 * Check whether this entity can have the given velocity as its velocity.
	 *  
	 * @param  velocity
	 *         The velocity to check.
	 * @return true iff the given velocity is effective and the associated speed does not exceed
	 *		the speedLimit of this entity.
	 *       | @see implementation
	 */
	public boolean canHaveAsVelocity(Velocity velocity) {
		if (velocity == null)
			return false;
		if (velocity.getSpeed() > getSpeedLimit())
			return false;
		return true;
	}
	
	/**
	 * Set the velocity of this entity to the given velocity.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the velocity for this entity.
	 * @param  yComponent
	 *         The new yComponent for the velocity for this entity.
	 * @post   If this entity can have the velocity with the given xComponent and  given yComponent as its velocity, 
	 * 			then the new velocity of this entity is equal to the velocity with given xComponent and yComponent.
	 *       | if (this.canHaveAsVelocity(new Velocity(xComponent,yComponent))
	 *       | 		then new.getVelocity().equals(new Velocity(xComponent, yComponent))
	 * @post   If this entity cannot have the velocity with the given xComponent and  given yComponent as its velocity, and
	 * 			the given xComponent and yComponent are valid components for any physical vector, then the new velocity of this entity 
	 * 			is equal to a velocity such that the direction corresponds with the velocity with given xComponent and yComponent,
	 * 			but the speed is set to the speedLimit.
	 * 			More concretely, the xComponent of the new velocity of this entity is set to (xComponent * getSpeedLimit() / speed) and the
	 *			yComponent of the new velocity of this entity is set to (yComponent * getSpeedLimit() / speed), where
	 *			speed is the speed corresponding to the velocity with given xComponent and yComponent.
	 *		 | if (PhysicalVector.isValidComponent(xComponent) && PhysicalVector.isValidComponent(yComponent) &&
	 *		 |			 ! this.canHaveAsVelocity(new Velocity(xComponent, yComponent))
	 *		 | 		then (new.getVelocity().getxComponent() == xComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 *		 |			&& (new.getVelocity().getyComponent() == yComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 * @post	If the current velocity of this entity is not effective and the given xComponent or yComponent are valid components
	 * 			for any physical vector, then the new velocity of this entity is equal to a velocity with 0 as its xComponent and yComponent.
	 * 		 | if (getVelocity() == null && (!PhysicalVector.isValidComponent(xComponent) || !PhysicalVector.isValidComponent(yComponent))
	 * 		 |		then new.getVelocity().equals(new Velocity(0, 0)
	 */
	@Raw @Model
	protected void setVelocity(double xComponent, double yComponent) {
			Velocity tempVelocity;
			try {
				tempVelocity = new Velocity(xComponent, yComponent);
			}
			catch(IllegalComponentException exc) {
				if (getVelocity() == null)
					tempVelocity = new Velocity(0, 0);
				else
					tempVelocity = getVelocity();
			}
			if (!canHaveAsVelocity(tempVelocity)) {
				double speed = tempVelocity.getSpeed();
				xComponent = xComponent * getSpeedLimit() / speed;
				yComponent = yComponent * getSpeedLimit() / speed;
				tempVelocity = new Velocity(xComponent, yComponent);
				//No exceptions are thrown here, because if xComponent or yComponent would be an invalid component, an exception would already
				//have been thrown and caught such that canHaveAsVelocity(tempVelocity) is always true.
			}
			this.velocity = tempVelocity;
	}
	
	/**
	 * Variable registering the velocity of this entity.
	 */
	private Velocity velocity;
	
	
	/**
	 * Return the speed limit of this entity.
	 */
	@Basic @Raw @Immutable
	public double getSpeedLimit() {
		return this.speedLimit;
	}
	
	/**
	 * Check whether the given speed limit is a valid speed limit for any entity.
	 *  
	 * @param  speedLimit
	 *         The speed limit to check.
	 * @return True if and only if the given speed limit is strictly positive and not greater than the speed of light.
	 *       | result == (0 < speedLimit) && (speedLimit <= SPEED_OF_LIGHT)
	 */
	public static boolean isValidSpeedLimit(double speedLimit) {
		return (0 < speedLimit) && (speedLimit <= SPEED_OF_LIGHT);
	}
	
	/**
	 * Variable registering the speed limit of this entity.
	 */
	private final double speedLimit = SPEED_OF_LIGHT;
	
	/**
	 * Constant representing the speed of light (i.e. 300000 km/s)
	 */
	public static final double SPEED_OF_LIGHT = 300000;
	
	/**
	 * Return the mass of this entity.
	 * 
	 * @return The volume of this entity multiplied by the density of this entity.
	 * 			| @see implementation
	 */
	@Raw
	public double getMass() {
		return getVolume() * getDensity();
	}
	
	/**
	 * Return the total mass of this entity.
	 * 
	 * @return The total mass of this entity, which is greater than or equal to this entity's mass.
	 * 			| result >= getMass()
	 */
	public double getTotalMass() {
		return getMass();
	}
	
	/**
	 * Set the density of this entity to the given density.
 	 *
	 * @param density
	 * 		The new density for this entity.
	 * @post If this entity can have the given density as its density,
	 * 			the new density of this entity equals the given density.
	 * 		| if (canHaveAsDensity(density))
	 * 		|	then new.getDensity() == density
	 */
	@Basic @Raw
	public void setDensity(double density) {
		if (canHaveAsDensity(density))
			this.density = density;
	}
	
	/**
	 * Return the density of this entity.
	 */
	@Basic @Raw
	public double getDensity() {
		return this.density;
	}
	
	/**
	 * Check whether this entity can have the given density as its density.
	 *  
	 * @param  density
	 *         The density to check.
	 * @return False if the given density is smaller than getMinimalDensity().
	 *       | if (density < getMinimalDensity())
	 *       |	then result == false
	 * @return False if the given density is not finite.
	 * 		 | if (!Double.isFinite(density))
	 * 		 |	then result == false
	 */
	@Raw
	public boolean canHaveAsDensity(double density) {
		return density >= getMinimalDensity() && Double.isFinite(density);
	}
	
	/**
	 * Variable registering the density of this entity.
	 */
	private double density;
	
	/**
	 * Check whether this entity can have the given density as its minimal density.
	 * 
	 * @param density
	 * 			The density to check.
	 * @return True iff the given density is strictly positive.
	 * 			| @see implementation
	 */
	public boolean canHaveAsMinimalDensity(double density) {
		return density > 0;
	}
	
	/**
	 * Return the minimal density of this entity.
	 */
	@Basic @Raw @Immutable
	public double getMinimalDensity() {
		return this.minimalDensity;
	}
	
	/**
	 * Variable registering the minimal density of this ship. 
	 */
	private final double minimalDensity;
	
	/**
	 * Set the radius of this entity to the given radius.
	 * @param radius
	 * 			The new radius of this entity.
	 * @post The new radius of this entity is equal to the given radius.
	 * 			| new.getRadius() == radius
	 * @throws IllegalRadiusException
	 * 			This entity cannot have the given radius as its radius.
	 * 			| ! canHaveAsRadius(radius)
	 */
	protected void setRadius(double radius) throws IllegalRadiusException {
		if (! canHaveAsRadius(radius))
			throw new IllegalRadiusException();
		this.radius = radius;
	}
	
	/**
	 * Return the radius of this entity.
	 */
	@Basic @Raw
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Calculate the sum of the radii of the two given entities.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return The sum of the radii of the two entities, if both are effective.
	 * 			| If ((entity1 != null) && (entity2!= null))
	 * 			|	then result == entity1.getRadius() + entity2.getRadius()
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getSumOfRadii(Entity entity1, Entity entity2) throws NullPointerException {
		return entity1.getRadius() + entity2.getRadius();
	}
	
	/**
	 * Check whether this entity can have the given radius as radius.
	 *  
	 * @param  radius
	 *         The radius to check.
	 * @return false if this entity cannot have the given radius as its initial radius.
	 * 			| if (! canHaveAsInitialRadius(radius))
	 * 			|	then result == false
	 */
	@Raw
	public boolean canHaveAsRadius(double radius) {
		return canHaveAsInitialRadius(radius);
	}

	
	/**
	 * @return The volume of this entity.
	 * 			| @see implementation
	 */
	@Raw
	public double getVolume() {
		return 4.0 / 3 * Math.PI * Math.pow(getRadius(), 3);
	}
	
	/**
	 * Variable registering the radius of this entity.
	 */
	private double radius;
	
	/**
	 * Return the initial radius of this entity.
	 */
	@Basic @Immutable @Raw
	public double getInitialRadius() {
		return this.initialRadius;
	}
	
	/**
	 * Check whether this entity can have the given radius as its initial radius.
	 * @param radius
	 * 			The radius to check.
	 * @return False if the given radius is strictly less than the minimal radius of this entity.
	 * 			| if (radius < getMinimalRadius())
	 * 			|	then result == false
	 * @return False if the radius is not finite
	 * 			| if (!Double.isFinite(radius))
	 * 			|	then result == false
	 */
	public boolean canHaveAsInitialRadius(double radius) {
		return radius >= getMinimalRadius() && Double.isFinite(radius);
	}
	
	/**
	 * Variable registering the initial radius of this entity.
	 */
	private final double initialRadius;
	
	/**
	 * Check whether this entity can have the given radius as its minimal radius.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return True iff the given radius is strictly positive.
	 * 			| @see implementation
	 */
	public boolean canHaveAsMinimalRadius(double radius) {
		return radius > 0;
	}
	
	/**
	 * Return the minimal radius of this entity.
	 */
	@Basic @Raw @Immutable
	public double getMinimalRadius() {
		return this.minimalRadius;
	}
	
	/**
	 * Variable registering the minimal radius of this ship. 
	 */
	private final double minimalRadius;
	
	/**
	 * Calculate the distance between the centres of two entities.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective, the distance between the centres of the two entities.
	 * 			| If ((entity1 != null) && (entity2!= null))
	 * 			|	then result == Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition())
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getDistanceBetweenCentres(Entity entity1, Entity entity2) throws NullPointerException {
		return Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition());
	}
	
	/**
	 * Calculate the distance between two entities.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, the distance between the two entities (i.e. the distance
	 * 				between the two centres minus the sum of their radii).
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == getDistanceBetweenCentres(entity1, entity2) - getSumOfRadii(entity1, entity2)
	 * @return If the two entities are effective and identical, zero.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == 0
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getDistanceBetween(Entity entity1, Entity entity2) throws NullPointerException {
		if ((entity1 != null) && (entity1 == entity2))
			return 0;
		return getDistanceBetweenCentres(entity1, entity2) - getSumOfRadii(entity1, entity2);
	}
	
	/**
	 * Determine whether two entities overlap.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, true iff the distance between their two centres is less than or equal
	 * 			to ACCURACY_FACTOR times the sum of their radii.
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == (Entity.getDistanceBetween(entity1, entity2) <= (ACCURACY_FACTOR - 1) * (entity1.getRadius() +
	 * 			|																								 entity2.getRadius())
	 * @return If the two entities are effective and identical, true.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == true
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 */
	public static boolean overlap(Entity entity1, Entity entity2) throws NullPointerException, TerminatedException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new TerminatedException();
		if (entity1 != null && entity1 == entity2)
			return true;
		return (Entity.getDistanceBetween(entity1, entity2) <= (ACCURACY_FACTOR - 1) * getSumOfRadii(entity1, entity2));
	}
	
//	/**
//	 * Check whether the given entity lies fully within the bounds of this entity.
//	 * 
//	 * @param other
//	 * 			The entity to check.
//	 * @return True iff minimal distance between the centre of the given entity and a point on the boundary of this entity
//	 * 			is greater than or equal to the radius of the given entity times the ACCURACY_FACTOR.
//	 * 		| result == (min{ pos in Position | Position.getDistanceBetween(pos, this.getPosition()) == this.getRadius()
//	 * 		|					: Position.getDistanceBetween(pos, other.getPosition())}) >= other.getRadius() * ACCURACY_FACTOR)
//	 * @throws NullPointerException
//	 * 			The given entity is not effective
//	 * 		| other == null
//	 * @throws TerminatedException
//	 * 			One of the entities is terminated
//	 * 			| (entity1.isTerminated() || entity2.isTerminated())
//	 */
//	public boolean surrounds(Entity other) throws NullPointerException, TerminatedException {
//		if (this.isTerminated() || other.isTerminated())
//			throw new TerminatedException();
//		return (this.getRadius() - getDistanceBetweenCentres(this, other) ) >= other.getRadius() * ACCURACY_FACTOR;
//	}
	
	/**
	 * Check whether this entity can fully surround the given entity.
	 * 
	 * @param other
	 * 			The entity to check.
	 * @return True iff the radius of this entity is greater than or equal to the radius of the given entity.
	 * 		| result == (getRadius() >= other.getRadius())
	 * @throws NullPointerException
	 * 			The given entity is not effective
	 * 		| other == null
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 */
	public boolean canSurround(Entity other) throws NullPointerException, TerminatedException {
		if (this.isTerminated() || other.isTerminated())
			throw new TerminatedException();
		return (getRadius() >= other.getRadius());
	}
	
	
	
	
	/**
	 * Check whether two entities apparently collide.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return false if one of the entities is not effective, is not associated to a world or if they are not associated to the same world.
	 * 			| if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
	 * 			|																					 entity1.getWorld() != entity2.getWorld())
	 * 			|	then result == false.
	 * @return true if both entities are effective and associated to the same world and if the distance between the centres of the entities
	 * 			lies within the range determined by the sum of their radii multiplied with ACCURACY_FACTOR and 2 - ACCURACY_FACTOR respectively,
	 * 			and if both entities are moving towards each other.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 */
	public static boolean apparentlyCollide(Entity entity1, Entity entity2) throws TerminatedException {
		if (entity1 == null || entity2 == null)
			return false;
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new TerminatedException();
		if (entity1.getWorld() == null || entity2.getWorld() == null ||
				entity1.getWorld() != entity2.getWorld())
			return false;
		boolean areCloseToEachOther = (ACCURACY_FACTOR * getSumOfRadii(entity1, entity2) <= getDistanceBetweenCentres(entity1, entity2)) &&
				(getDistanceBetweenCentres(entity1, entity2) <= (2 - ACCURACY_FACTOR) * getSumOfRadii(entity1, entity2));
		boolean movingTowardsEachOther = (entity1.getVelocity().vectorMinus(entity2.getVelocity())).scalarProductWith(
											entity1.getPosition().vectorMinus(entity2.getPosition())) < 0;
		return areCloseToEachOther && movingTowardsEachOther;
	}
	
	/**
	 * Check whether two entities will collide if they are moved during a certain duration.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @param duration
	 * 			The duration during which the entities must be moved.
	 * @return false if one of the entities is not effective, is not associated to a world or if they are not associated to the same world.
	 * 			| if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
	 * 			|																					 entity1.getWorld() != entity2.getWorld())
	 * 			|	then result == false.
	 * @return true if both entities are effective and associated to the same world and if they collide when their positions
	 * 			are set as if they moved during the given duration. It does not matter whether they collide during the
	 * 			process of moving the entities. Only the final positions are important.
	 * 			| if ((entity1 != null) && (entity2!= null) && (entity1 != entity2) && (entity1.getWorld() != null) 
	 * 			|																	&& (entity1.getWorld() == entity2.getWorld()))
	 * 			|	then result == (getSumOfRadii(entity1, entity2) == 
	 * 			|		Position.getDistanceBetween(entity1.getPosition().move(entity1.getVelocity(), duration),
	 * 			|											 entity2.getPosition().move(entity2.getVelocity(), duration)))
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 */
	public static boolean collideAfterMove(Entity entity1, Entity entity2, double duration) throws TerminatedException {
		if (entity1 == null || entity2 == null)
			return false;
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new TerminatedException();
		if (entity1.getWorld() == null || entity2.getWorld() == null || entity1.getWorld() != entity2.getWorld())
			return false;
		Position position1 = entity1.getPosition().move(entity1.getVelocity(), duration);
		Position position2 = entity2.getPosition().move(entity2.getVelocity(), duration);
		double distanceBetweenCentres = Position.getDistanceBetween(position1, position2);
		return getSumOfRadii(entity1, entity2) == distanceBetweenCentres;
	}
	
	/**
	 * Check whether this entity will collide if it is moved during a certain duration.
	 * 
	 * @param duration
	 * 			The duration during which this entity must be moved.
	 * @return false if this entity is not contained in a world.
	 * 			| if (getWorld() == null)
	 * 			|	then result == false
	 * @return true if this entity is contained in a world and if it will collide with the boundary of its world when it is moved during the
	 * 			given duration.
	 * 			| if (getWorld() != null)
	 * 			| then result == ( (getPosition().move(getVelocity(), duration).getyComponent() <= getRadius()) ||
	 * 								(getWorld().getHeight() - getPosition().move(getVelocity(), duration).getyComponent() <= getRadius()) ||
	 * 								(getPosition().move(getVelocity(), duration).getxComponent() <= getRadius()) ||
	 * 								(getWorld().getWidth() - getPosition().move(getVelocity(), duration).getxComponent() <= getRadius()) )
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| isTerminated()
	 */
	public boolean collidesWithBoundaryAfterMove(double duration) throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (getWorld() == null)
			return false;
		Position positionAfterMove = getPosition().move(getVelocity(), duration);
		return (positionAfterMove.getyComponent() <= getRadius()) || (getWorld().getHeight() - positionAfterMove.getyComponent() <=
				getRadius()) || (positionAfterMove.getxComponent() <= getRadius()) || (getWorld().getWidth() -
				positionAfterMove.getxComponent() <= getRadius());
	}
	
	/**
	 * Determine the time after which, if ever, two entities will collide.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If both entities are effective, different and are contained in the same effective world, the result is determined such that
	 * 			the two entities would collide after they would have moved during the given duration, but not earlier.
	 * 			| if ((entity1 != null) && (entity2!= null) && (entity1 != entity2) && (entity1.getWorld() != null) 
	 * 			|																	&& (entity1.getWorld() == entity2.getWorld()))
	 * 			|	then collideAfterMove(entity1, entity2, result) &&
	 * 			|		( for each t in { x in Real Numbers | 0 <= x < result } : !collideAfterMove(entity1, entity2, t)
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			|	(entity1 == null) || (entity2 == null)
	 * @throws IllegalMethodCallException
	 * 			The world of one entity is not effective or both entities are not contained in the same world.
	 * 			| (entity1.getWorld() == null) || (entity1.getWorld() == entity2.getWorld())
	 * @throws OverlapException
	 * 			The entities overlap.
	 * 			| overlap(entity1, entity2)
	 * @note Theoretically, two entities that are contained in the same world, cannot overlap.
	 * 			This exception is included as an additional safety check. 
	 * @throws TerminatedException
	 * 			One of the entities is terminated.
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 * @throws IllegalMethodCallException
	 * 			The world of one entity is not effective or both entities are not contained in the same world.
	 */
	public static double getTimeToCollision(Entity entity1, Entity entity2) throws NullPointerException, IllegalMethodCallException,
																		OverlapException, TerminatedException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new TerminatedException();
		if ((entity1.getWorld() == null) || (entity1.getWorld() != entity2.getWorld()))
			throw new IllegalMethodCallException();
		if (overlap(entity1, entity2))
			throw new OverlapException();
		
		double dx, dy, dvx, dvy, discriminant, sumOfRadii, dvDotdr;
		dx = entity1.getPosition().getxComponent() - entity2.getPosition().getxComponent();
		dy = entity1.getPosition().getyComponent() - entity2.getPosition().getyComponent();
		dvx = entity1.getVelocity().getxComponent() - entity2.getVelocity().getxComponent();
		dvy = entity1.getVelocity().getyComponent() - entity2.getVelocity().getyComponent();
		sumOfRadii = entity1.getRadius() + entity2.getRadius();
		dvDotdr = dvx * dx + dvy * dy;
		
		if (dvDotdr >= 0)
			return Double.POSITIVE_INFINITY;
		
		discriminant = Math.pow(dvDotdr, 2) - Math.pow(Math.hypot(dvx, dvy), 2) *
							(Math.pow(Math.hypot(dx, dy),  2) - Math.pow(sumOfRadii, 2));
		if (discriminant <= 0)
			return Double.POSITIVE_INFINITY;
		double result = - (dvDotdr + Math.sqrt(discriminant)) / Math.pow(Math.hypot(dvx, dvy), 2);
		if (result < 0)
			return 0;
		return result;
	}
	
	/**
	 * Determine the position where, if ever, two entities will collide.
	 * 
	 * @param entity1
	 * 			The first entity.
	 * @param entity2
	 * 			The second entity.
	 * @return null, if the entities will not collide
	 * 			| if (getTimeToCollision(entity1, entity2) == Double.POSITIVE_INFINITY)
	 * 			|	then result == null 
	 * @return If the entities will collide, the result satisfies the following condition:
	 * 			After both entities are moved during the time getTimeToCollision(entity1, entity2), the distance between
	 * 			the result and the position of entity1 equals the radius of entity1 and the distance between
	 * 			the result and the position of entity2 equals the radius of entity2.
	 * 			| if ( Double.isFinite(getTimeToCollision(entity1, entity2)) )
	 * 			| 	then (Position.getDistanceBetween(result, entity1.getPosition().move(getTimeToCollision(entity1, entity2))) == 
	 * 			|		entity1.getRadius() ) 
	 * 			|	&& (Position.getDistanceBetween(result, entity2.getPosition().move(getTimeToCollision(entity1, entity2))) == 
	 * 			|		entity2.getRadius() )
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			| (entity1 == null) || (entity2 == null)
	 * @throws IllegalMethodCallException
	 * 			The world of one entity is not effective or both entities are not contained in the same world.
	 * 			| (entity1.getWorld() == null) || (entity1.getWorld() == entity2.getWorld())
	 * @throws OverlapException
	 * 			The entities overlap.
	 * 			| overlap(entity1, entity2)
	 * @note Theoretically, two entities that are contained in the same world, cannot overlap.
	 * 			This exception is included as an additional safety check. 
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| (entity1.isTerminated() || entity2.isTerminated())
	 * @throws IllegalMethodCallException
	 * 			The world of one entity is not effective or both entities are not contained in the same world.
	 */
	public static Position getCollisionPosition(Entity entity1, Entity entity2) throws NullPointerException, IllegalMethodCallException,
																OverlapException, TerminatedException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new TerminatedException();
		if ((entity1.getWorld() == null) || (entity1.getWorld() != entity2.getWorld()))
			throw new IllegalMethodCallException();
		if (overlap(entity1, entity2))
			throw new OverlapException();
		
		double timeToCollision = getTimeToCollision(entity1, entity2);
		if (timeToCollision == Double.POSITIVE_INFINITY && !apparentlyCollide(entity1, entity2))
			//Due to rounding issues, it is possible that two entities already apparently collide, but the time to their collision is
			// calculated to be POSITIVE_INFINITY instead of zero.
			return null;
		
		Position position1, position2;
		
		if (!apparentlyCollide(entity1, entity2)) {
			position1 = entity1.getPosition().move(entity1.getVelocity(), timeToCollision);
			position2 = entity2.getPosition().move(entity2.getVelocity(), timeToCollision);
		}
		else {
			position1 = entity1.getPosition();
			position2 = entity2.getPosition();
		}
		
		double radius1 = entity1.getRadius();
		double radius2 = entity2.getRadius();
		double sumOfRadii = radius1 + radius2;
		
		return new Position( (position1.getxComponent() * radius2 + position2.getxComponent() * radius1) / sumOfRadii, 
				(position1.getyComponent() * radius2 + position2.getyComponent() * radius1) / sumOfRadii);
		
	}
	
	/**
	 * Check whether this entity apparently collides with a horizontal boundary of its world.
	 * 
	 * @return True iff the world of this entity is effective and
	 * 			the distance between the centre of this entity and a horizontal boundary of its world is less than or equal to
	 * 				(2 - ACCURACY_FACTOR) times the radius of this entity, and
	 * 			this entity is moving towards the closest horizontal boundary.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public boolean apparentlyCollidesWithHorizontalBoundary() throws TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null)
			return false;
		return ( (getPosition().getyComponent() <= getRadius() * (2 - ACCURACY_FACTOR)) && getVelocity().getyComponent() < 0)
				|| (getWorld().getHeight() - getPosition().getyComponent() <= getRadius() * (2 - ACCURACY_FACTOR) &&
																								getVelocity().getyComponent() > 0);
	}
	
	/**
	 * Check whether this entity apparently collides with a vertical boundary of its world.
	 * 
	 * @return True iff the world of this entity is effective and
	 * 			the distance between the centre of this entity and a vertical boundary of its world is less than or equal to
	 * 				(2 - ACCURACY_FACTOR) times the radius of this entity, and
	 * 			this entity is moving towards the closest vertical boundary.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public boolean apparentlyCollidesWithVerticalBoundary() throws TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null)
			return false;
    		return (getPosition().getxComponent() <= getRadius() * (2 - ACCURACY_FACTOR) && getVelocity().getxComponent() < 0)
				|| (getWorld().getWidth() - getPosition().getxComponent() <= getRadius() * (2 - ACCURACY_FACTOR)
																&& getVelocity().getxComponent() > 0);
	}
  
	/**
	 * Check whether this entity apparently collides with the boundary of its world.
	 * 
	 * @return true iff this entity apparently collides with a horizontal or vertical boundary.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public boolean apparentlyCollidesWithBoundary() throws TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null)
			return false;
		return apparentlyCollidesWithHorizontalBoundary() || apparentlyCollidesWithVerticalBoundary();
	}
	
	/**
	 * Determine the time after which, if ever, this entity will collide with the boundary of its world.
	 * 
	 * @return Double.POSITIVE_INFINITY if this entity is not associated to a world.
	 * 			| if (getWorld() == null)
	 * 			|	then result == Double.POSITIVE_INFINITY
	 * @return Double.POSITIVE_INFINITY if this entity is associated to a world and it will never collide with the boundary of its world.
	 * 			| if (getWorld() != null) && (for each t in { x in Real Numbers | O <= x }: ! collidesWithBoundaryAfterMove(t))
	 * 			|	then result == Double.POSITIVE_INFINITY 
	 * @return If this entity is associated to a world and it will collide with the boundary of its world, the result is the smallest
	 *			positive real number for which collidesWithBoundaryAfterMove(result) is true.
	 * 			| if (getWorld() != null)
	 * 			|	then (for each t in { x in Real Number | 0 <= x < result }: ! collidesWithBoundaryAfterMove(t) &&
	 * 			|		 collidesWithBoundaryAfterMove(result))
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public double getTimeToCollisionWithBoundary() throws TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null)
			return Double.POSITIVE_INFINITY;
		double result = Double.POSITIVE_INFINITY;
		if (getVelocity().getxComponent() < 0)
			result = Double.min(result, -(getPosition().getxComponent() - getRadius()) / getVelocity().getxComponent());
		else 
			result = Double.min(result, (getWorld().getWidth() - getPosition().getxComponent() - getRadius())
																										/ getVelocity().getxComponent());
		if (getVelocity().getyComponent() < 0)
			result = Double.min(result, -(getPosition().getyComponent() - getRadius()) / getVelocity().getyComponent());
		else 
			result = Double.min(result, (getWorld().getHeight() - getPosition().getyComponent() - getRadius())
																										/ getVelocity().getyComponent());
		return result;
	}
	
	/**
	 * Determine the position where the given entity will collide with the boundary of its world.
	 * 
	 * @return null, if this entity is not associated with a world or it will not collide with a boundary.
	 * 			| if (getWorld() == null || getTimeToCollisionWithBoundary() == Double.POSITIVE_INFINITY)
	 * 			|	then result == null
	 * @return The position where this entity collides with its world's boundary.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 */
	public Position getCollisionWithBoundaryPosition() throws TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || getTimeToCollisionWithBoundary() == Double.POSITIVE_INFINITY)
			return null;
		return getPosition().move(getVelocity(), getTimeToCollisionWithBoundary());
	}
	
	/**
	 * Resolve a collision between this entity and another entity.
	 * 
	 * @param other
	 * 			The entity to resolve a collision with 
	 * @throws IllegalMethodCallException
	 * 			Either this entity or the other entity is not associated to a world, this entity and the other entity are not associated
	 *			to the same world or this entity and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	public abstract void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException;
	
	/**
	 * Let this entity bounce of the other given entity.
	 * 
	 * @param other
	 * 			The other entity to bounce of.
	 * @effect The velocities of this entity and the given entity are adjusted according to the physical laws regarding conservation
	 * 			of momentum and energy. 
	 * 			| @see implementation
	 * @throws IllegalMethodCallException
	 * 			This entity does not apparently collide with the other entity.
	 * 			| !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			This entity or the other entity is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	@Model
	void bounceOf(Entity other) throws IllegalMethodCallException, TerminatedException {
		if (!Entity.apparentlyCollide(this, other))
			throw new IllegalMethodCallException();
		double dx, dy, dvx, dvy, sumOfRadii, dvDotdr, m1, m2;
		dx = this.getPosition().getxComponent() - other.getPosition().getxComponent();
		dy = this.getPosition().getyComponent() - other.getPosition().getyComponent();
		dvx = this.getVelocity().getxComponent() - other.getVelocity().getxComponent();
		dvy = this.getVelocity().getyComponent() - other.getVelocity().getyComponent();
		sumOfRadii = this.getRadius() + other.getRadius();
		dvDotdr = dvx * dx + dvy * dy;
		m1 = this.getTotalMass();
		m2 = other.getTotalMass();
		
		double J = (2 * m1 * m2 * dvDotdr) / (sumOfRadii * (m1 + m2));
		double Jx = J * dx / sumOfRadii;
		double Jy = J * dy / sumOfRadii;
		
		this.setVelocity(this.getVelocity().getxComponent() - Jx / m1, this.getVelocity().getyComponent() - Jy / m1);
		other.setVelocity(other.getVelocity().getxComponent() + Jx / m2, other.getVelocity().getyComponent() + Jy / m2);
	}
	
	/** 
	 * Make this entity bounce of the boundary of its world.
	 * 
	 * @effect	If this entity apparently collides with a horizontal boundary of its world, then the y component of this entity's
	 * 			velocity is negated.
	 * 			| if (apparentlyCollidesWithHorizontalBoundary())
	 * 			|	then setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent())
	 * @effect	If this entity apparently collides with a vertical boundary of its world, then the x component of this entity's
	 * 			velocity is negated.
	 * 			| if (apparentlyCollidesWithVerticalBoundary())
	 * 			|	then setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent())
	 * @throws	TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			This entity is not associated to a world or this entity does not collide with the boundary of its world.
	 * 			| getWorld() == null || !apparentlyCollidesWithBoundary()
	 */
	public void bounceOfBoundary() throws IllegalMethodCallException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || !apparentlyCollidesWithBoundary())
			throw new IllegalMethodCallException();
		else if (apparentlyCollidesWithHorizontalBoundary())
			setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent());
		else if (apparentlyCollidesWithVerticalBoundary())
			setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent());
	}
	
	/**
	 * Check whether this entity can have the given world as world.
	 * 
	 * @param world
	 * 			The world to check.
	 * @return If this entity is terminated, true iff the given world is null.
	 * 			| @see implementation
	 * @return If this entity is not terminated, true iff the given world is null or
	 * 			the given world is effective and can have this entity as entity.
	 * 			| @see implementation
	 */
	public boolean canHaveAsWorld(World world) {
		if (isTerminated())
			return world == null;
		return (world == null) || world.canHaveAsEntity(this);
	}
	
	/**
	 * Check whether if a collision between this entity and the given other entity occurs, it must be shown.
	 * This method does not check if this entity and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 */
	public abstract boolean mustShowCollisionWith(Entity other);
	
	/**
	 * Check whether this entity has a proper world.
   *
	 * @return If this entity is terminated, true iff the world associated to this entity is null.
	 * 			| if (this.isTerminated())
	 * 			|	then result == (getWorld() == null)
	 * @return If this entity is not terminated, true iff the world associated to this entity is null or contains this entity at this
	 * 			entity's position.
	 * 			| if (!this.isTerminated())
	 * 			|	then result == (getWorld() == null) || (getWorld().getEntityAt(this.getPosition()) == this && 
	 * 			|				(for each position in getWorld().getOccupiedPositions():
	 * 			|					position == this.getPosition() || getWorld().getEntityAt(position) != this))
	 */
	public boolean hasProperWorld() {
		if (getWorld() == null)
			return true;
		if (isTerminated())
			return false;
		if (!canHaveAsWorld(getWorld()))
			return false;
		if (getWorld().getEntityAt(this.getPosition()) != this)
			return false;
		else {
			for(Position position: getWorld().getOccupiedPositions()) {
				if (!position.equals(getPosition()) && getWorld().getEntityAt(position) == this)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the world in which this entity is contained.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Determine whether this entity can be removed from its world.
	 * 
	 * @return false if this entity is not contained in a world.
	 * 			| if (getWorld() == null)
	 * 			|	then result == false.
	 */
	public abstract boolean canBeRemovedFromWorld();
	
	/**
	 * Set the world of this entity to the given world.
	 * 
	 * @param world
	 * 			The new world for this entity.
	 * @post The new world of this entity is equal to the given world.
	 * 			| new.getWorld() == world
	 * @throws IllegalMethodCallException
	 * 			(This entity cannot have the given world as its world) or
	 * 			(The given world is effective but does not yet contain this entity) or
	 * 			(the given world is effective but is terminated) or 
	 * 			(the given world is not effective and the world of this entity is effective and still contains this entity).
	 * 			| (world != null && !world.hasAsEntity(this)) ||
	 * 			|	(world == null && getWorld() != null && getWorld().hasAsEntity(this))
	 * @throws TerminatedException
	 * 			This entity is terminated.
	 * 			| isTerminated()
	 */
	@Model
	void setWorld(World world) throws IllegalMethodCallException, TerminatedException {
		if (! canHaveAsWorld(world))
			throw new IllegalMethodCallException();
		if (isTerminated())
			throw new TerminatedException();
		if (world != null && (!world.hasAsEntity(this) || world.isTerminated()))
			throw new IllegalMethodCallException();
		if (world == null && getWorld() != null && getWorld().hasAsEntity(this))
			throw new IllegalMethodCallException();
		this.world = world;
	}
	
	/**
	 * A variable registering the world in which this entity is contained.
	 */
	private World world;
}