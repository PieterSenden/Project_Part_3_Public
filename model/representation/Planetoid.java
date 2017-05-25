package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing a circular planetoid dealing with
 * position, velocity, radius, density and mass.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */
public class Planetoid extends MinorPlanet {
	
	/**
	 * Initialize this new planetoid with given position, velocity, radius and total travelled distance.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new planetoid.
	 * @param yComPos
	 * 			The yComponent of the position of this new planetoid.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new planetoid.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new planetoid.
	 * @param radius
	 * 			The radius of this new planetoid.
	 * @param totalTravelledDistance
	 * 			The total travelled distance of this new planetoid.
	 * @effect This new planetoid is initialized as a new minor planet with the given position as its position, the given velocity as its
	 * 			velocity and the given radius as its radius. After that, it is shrunk with an amount of totalTravelledDistance * SHRINK_FACTOR
	 * 			and the given total travelled distance is added to total travelled distance of this planetoid.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius) && shrink(totalTravelledDistance * SHRINK_FACTOR) && 
	 * 			|														addToTotalTravelledDistance(totalTravelledDistance)
	 */
	@Raw 
	public Planetoid(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double totalTravelledDistance)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, MINIMAL_DENSITY);
		addToTotalTravelledDistance(totalTravelledDistance);
		shrink(totalTravelledDistance * SHRINK_FACTOR);
	}
	
	/**
	 * Initialize this new planetoid with given position, velocity and radius.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new planetoid.
	 * @param yComPos
	 * 			The yComponent of the position of this new planetoid.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new planetoid.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new planetoid.
	 * @param radius
	 * 			The radius of this new planetoid.
	 * @effect This new planetoid is initialized with the given position as its position, the given velocity as its velocity,
	 * 			the given radius as its radius and zero as total travelled distance.
	 * 			| this(xComPos, yComPos, xComVel, yComVel, radius, 0)
	 */
	@Raw 
	public Planetoid(double xComPos, double yComPos, double xComVel, double yComVel, double radius)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, 0);
	}
	
	/**
	 * Constant registering the minimal density of any planetoid. 
	 */
	public static final double MINIMAL_DENSITY = 0.917e12;
	
	/**
	 * Terminate this planetoid.
	 * 
	 * @effect	| if (! isTerminated() && getRadius() >= 30 && getWorld() != null)
	 * 			|	then let
	 * 			|		angle = Math.random() * 2 * Math.PI
	 * 			|	in 
	 * 			|		asteroid1 = new Asteroid(getPosition().vectorPlus(new Position(Math.cos(angle), Math.sin(angle)).
	 * 			|			scalarMultiple(getRadius() / 2)), (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(1.5 * getSpeed()),
	 * 			|			getRadius() / 2)
	 * 			|		asteroid2 = new Asteroid(getPosition().vectorMinus(new Position(Math.cos(angle), Math.sin(angle)).
	 * 			|			scalarMultiple(getRadius() / 2)), (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(-1.5 * getSpeed()),
	 * 			|			getRadius() / 2)
	 * 			|		getWorld().addEntity(asteroid1)
	 * 			|		getWorld().addEntity(asteroid2)
	 * @effect	| if (! isTerminated())
	 * 			|	then super.terminate()
	 */
	@Override
	public void terminate() {
		if (! isTerminated()){
			World planetoidWorld = getWorld();
			super.terminate();
			if (getRadius() >= 30 && planetoidWorld != null) {
				double angle = Math.random() * 2 * Math.PI;
				Position newPos1 = getPosition().vectorPlus(new Position(Math.cos(angle), Math.sin(angle)).scalarMultiple(getRadius() / 2));
				Position newPos2 = getPosition().vectorMinus(new Position(Math.cos(angle), Math.sin(angle)).scalarMultiple(getRadius() / 2));
				Velocity newVel1 = (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(1.5 * getSpeed());
				Velocity newVel2 = (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(-1.5 * getSpeed());
				planetoidWorld.addEntity(new Asteroid(newPos1, newVel1, getRadius() / 2));
				planetoidWorld.addEntity(new Asteroid(newPos2, newVel2, getRadius() / 2));
			}
		}
	}
	
	/**
	 * Resolve a collision between this planetoid and another entity.
	 * 
	 * @param  other
	 * 			The entity to resolve a collision with.
	 * @effect	| super.resolveCollision(other)
	 * @effect	| if (other instanceof Ship)
	 * 			|	then other.teleport()
	 * @effect	| if (!(other instanceof Ship) && !(other instanceof Bullet) && !(other instanceof MinorPlanet))
	 * 			|	then other.resolveCollision(this)
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException, NullPointerException {
		super.resolveCollision(other);
		if (other instanceof Ship)
			other.teleport();
		else if (! (other instanceof Bullet) && ! (other instanceof MinorPlanet))
			other.resolveCollision(this);
	}
	
	/**
	 * Let this planetoid shrink with the given amount.
	 * 
	 * @param amount
	 * 			The amount to shrink.
	 * @effect	| if (!canHaveAsRadius(getRadius() - amount)
	 * 			|	then this.terminate()
	 *			| else
	 * 			|	setRadius(getRadius() - amount)
	 * @throws IllegalArgumentException
	 * 			| amount < 0
	 */
	public void shrink(double amount) throws IllegalArgumentException {
		if (amount < 0)
			throw new IllegalArgumentException();
		try {
			setRadius(getRadius() - amount);
		}
		catch (IllegalRadiusException exc) {
			terminate();
		}
	}
	
	/**
	 * Move this planetoid during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the planetoid is moved.
	 * @effect	| super.move(duration)
	 * @effect	| shrink(duration * getSpeed() * SHRINK_FACTOR)
	 * @note	shrink(duration * getSpeed() * SHRINK_FACTOR) will never throw an exception, since getSpeed() >= 0, SHRINK_FACTOR > 0
	 * 			and duration >= 0 since this was already checked by super.move(duration) 
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 * @throws TerminatedException
	 * 			This planetoid is terminated
	 * 			| this.isTerminated()
	 */
	@Override
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException, TerminatedException,
															IllegalPositionException, IllegalStateException {
		super.move(duration);
		shrink(duration * getSpeed() * SHRINK_FACTOR);
	}
	
	public static final double SHRINK_FACTOR = 1e-6;
}
