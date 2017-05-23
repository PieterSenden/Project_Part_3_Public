package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class representing the position of an entity.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 * 
 */
@Value
public class Position extends PhysicalVector {
	
	/**
	 * Initialize this new position with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new position.
	 * @param  yComponent
	 *         The yComponent for this new position.
	 * @effect This new position is initialized as a physical vector with the given xComponent and yComponent.
	 * 		 | @see implementation
	 */
	public Position(double xComponent, double yComponent) throws IllegalComponentException {
		super(xComponent, yComponent);
	}
	
	/**
	 * Calculates the distance between two positions.
	 * 
	 * @param pos1
	 * 			The first position
	 * @param pos2
	 * 			The second position
	 * @return If both positions are effective, the Euclidean distance between them is returned.
	 * 			| result == Math.sqrt( (pos1.getxComponent() - pos2.getxComponent())^2 +
	 *			| 	(pos1.getyComponent() - pos2.getyComponent())^2 )
	 * @throws NullPointerException
	 * 			One of the positions is not effective
	 * 			| (pos1 == null) || (pos2 == null)
	 */
	public static double getDistanceBetween(Position pos1, Position pos2) throws NullPointerException {
		return Math.hypot(pos1.getxComponent() - pos2.getxComponent(),
				pos1.getyComponent() - pos2.getyComponent());
	}
	
	/**
	 * Move this position during a given time duration and with a given velocity.
	 * 
	 * @param velocity
	 * 			The velocity with which the object is moved.
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @return A new position object which is the result of moving this position object
	 * 			with the given velocity during the given duration  
	 * 			| @see implementation 
	 * @throws IllegalArgumentException
	 * 			The given velocity is not effective or given duration is strictly less than 0.
	 * 			| velocity == null || duration < 0
	 */
	public Position move(Velocity velocity, double duration) throws IllegalComponentException, IllegalArgumentException {
		if (velocity == null || duration < 0)
			throw new IllegalArgumentException();
		return new Position(getxComponent() + velocity.getxComponent() * duration,
				getyComponent() + velocity.getyComponent() * duration);
	}
	
	
	/**
	 * Return the difference of this position vector with the given other physical vector.
	 * 
	 * @param other
	 * 			The second physical vector (after minus sign).
	 * @return 	The difference of this position vector and the given other physical vector.
	 * 			| @see implementation
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws IllegalComponentException
	 * 			getxComponent() - other.getxComponent() or getyComponent() - other.getyComponent() is not a valid component for any position.
	 * 			| !isValidComponent(getxComponent() - other.getxComponent()) || !isValidComponent(getyComponent() - other.getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given other physical vector is not a position.
	 * 			| !(other instanceof Position)
	 */
	@Override
	public Position vectorMinus(PhysicalVector other) throws NullPointerException, IllegalComponentException,
																					IllegalArgumentException {
		PhysicalVector result = super.vectorMinus(other);
		return new Position(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Return the sum of this position vector with the given other physical vector.
	 * 
	 * @param other
	 * 			The second position vector.
	 * @return The sum of this position vector an the given other physical vector.
	 * 			| result == (Position)(super.vectorPlus(other))
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws IllegalComponentException
	 * 			getxComponent() + other.getxComponent() or getyComponent() + other.getyComponent() is not a valid component for any position.
	 * 			| !isValidComponent(getxComponent() + other.getxComponent()) || !isValidComponent(getyComponent() + other.getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given other physical vector is not a position.
	 * 			| !(other instanceof Position)
	 */
	@Override
	public Position vectorPlus(PhysicalVector other) throws NullPointerException, IllegalComponentException,
	IllegalArgumentException {
		PhysicalVector result = super.vectorPlus(other);
		return new Position(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Return the scalar multiple of this position vector with the given factor.
	 * 
	 * @param factor
	 * 			The scalar factor.
	 * @return The scalar multiple of this position vector with the given factor.
	 * 			| result == (Position)(super.scalarMultiple(factor))
	 * @throws IllegalComponentException
	 * 			getxComponent() * factor or getyComponent() * factor is not a valid component for any position.
	 * 			| !isValidComponent(getxComponent() * factor) || !isValidComponent(getyComponent() * factor)
	 * @throws IllegalArgumentException
	 * 			The given factor is not finite.
	 * 			| !Double.isFinite(factor)
	 */
	@Override
	public Position scalarMultiple(double factor) throws IllegalComponentException,	IllegalArgumentException {
		PhysicalVector result = super.scalarMultiple(factor);
		return new Position(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Check whether this position is equal to the given object.
	 * 
	 * @return True iff other is an instance of the class Position, 
	 * 			and both xComponents are equal and both yComponents are equal.
	 * 			| @see implementation
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Position))
			return false;
		Position otherAsPosition = (Position)other;
		return getxComponent() == otherAsPosition.getxComponent() && getyComponent() == otherAsPosition.getyComponent();
	}
	
	/**
	 * Return the hash code for this physical vector.
	 */
	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(getxComponent());
		result = prime * result + (int) (temp ^ (temp >>> 38));
		temp = Double.doubleToLongBits(getyComponent());
		result = prime * result + (int) (temp ^ (temp >>> 38));
		return result;
	}
}
