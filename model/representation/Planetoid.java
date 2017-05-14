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
	 * @effect This new planetoid is initialized with the given position as its position, the given velocity as its velocity and
	 * 			the given radius as its radius. After that, it is shrunk with an amount of totalTravelledDistance * SHRINK_FACTOR.
	 * 			| super(xComPos, yComPos, xComVel, yComVel, radius) && shrink(totalTravelledDistance * SHRINK_FACTOR);
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
	 * 			|		asteroid1 = new Asteroid(
	 * @effect	| if (! isTerminated())
	 * 			|	then super.terminate()
	 */
	@Override
	public void terminate() {
		if (! isTerminated()){
			if (getRadius() >= 30 && getWorld() != null) {
				double angle = Math.random() * 2 * Math.PI;
				Position newPos1 = getPosition().vectorPlus(new Position(Math.cos(angle), Math.sin(angle)).scalarMultiple(getRadius() / 2));
				Position newPos2 = getPosition().vectorMinus(new Position(Math.cos(angle), Math.sin(angle)).scalarMultiple(getRadius() / 2));
				Velocity newVel1 = (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(1.5 * getSpeed());
				Velocity newVel2 = (new Velocity(Math.cos(angle), Math.sin(angle))).scalarMultiple(-1.5 * getSpeed());
				getWorld().addEntity(new Asteroid(newPos1, newVel1, getRadius() / 2));
				getWorld().addEntity(new Asteroid(newPos2, newVel2, getRadius() / 2));
			}
			super.terminate();
		}
	}
	
	/**
	 * Return a copy of this planetoid.
	 * 
	 * @return A copy of this planetoid.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 */
	@Override
	public Planetoid copy() throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		return new Planetoid(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius());
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
	 * @throws IllegalMethodCallException
	 * 			Either this planetoid or the other entity is not associated to a world, this planetoid and the other entity are not associated
	 *			to the same world or this planetoid and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 * @throws TerminatedException
	 * 			This planetoid or the other entity is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 * @throws NullPointerException
	 * 			The given other entity is not effective.
	 * 			| other == null
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException, NullPointerException {
		super.resolveCollision(other);
		if (other instanceof Ship)
			other.teleport();
		else
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
