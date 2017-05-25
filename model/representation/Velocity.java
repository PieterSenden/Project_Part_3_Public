package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class representing a 2-dimensional velocity vector.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
@Value
public class Velocity extends PhysicalVector {

	/**
	 * Initialize this new velocity with given xComponent and yComponent.
	 *
	 * @param  xComponent
	 *         The xComponent for this new velocity.
	 * @param  yComponent
	 *         The yComponent for this new velocity.
	 * @effect This new velocity is initialized as a new physical vector with the given xComponent and yComponent.
	 * 			| @see implementation
	 */
	public Velocity(double xComponent, double yComponent) throws IllegalComponentException {
		super(xComponent, yComponent);
	}
	
	/**
	 * Calculate the speed associated with this velocity 
	 * 
	 * @return The speed associated with this velocity
	 * 			|  result == Math.sqrt(getxComponent()^2 + getyComponent()^2)
	 */
	public double getSpeed() {
		return Math.hypot(getxComponent(), getyComponent());
	}
	
	
	/**
	 * Return the difference of this velocity vector with the given other physical vector.
	 * 
	 * @param other
	 * 			The second physical vector (after minus sign).
	 * @return 	The difference of this velocity vector and the given other physical vector.
	 * 			| @see implementation
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws IllegalComponentException
	 * 			getxComponent() - other.getxComponent() or getyComponent() - other.getyComponent() is not a valid component for any velocity.
	 * 			| !isValidComponent(getxComponent() - other.getxComponent()) || !isValidComponent(getyComponent() - other.getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given other physical vector is not a velocity.
	 * 			| !(other instanceof Velocity)
	 */
	@Override
	public Velocity vectorMinus(PhysicalVector other) throws NullPointerException, IllegalComponentException,
																					IllegalArgumentException {
		PhysicalVector result = super.vectorMinus(other);
		return new Velocity(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Return the sum of this velocity vector with the given other physical vector.
	 * 
	 * @param other
	 * 			The second physical vector.
	 * @return The sum of this velocity vector and the given other physical vector.
	 * 			| @see implementation
	 * @throws NullPointerException
	 * 			The given other physical vector is not effective.
	 * 			| other == null
	 * @throws IllegalComponentException
	 * 			getxComponent() + other.getxComponent() or getyComponent() + other.getyComponent() is not a valid component for any velocity.
	 * 			| !isValidComponent(getxComponent() + other.getxComponent()) || !isValidComponent(getyComponent() + other.getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given other physical vector is not a velocity.
	 * 			| !(other instanceof Velocity)
	 */
	@Override
	public Velocity vectorPlus(PhysicalVector other) throws NullPointerException, IllegalComponentException,
																					IllegalArgumentException {
		PhysicalVector result = super.vectorPlus(other);
		return new Velocity(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Return the scalar multiple of this physical vector with the given factor.
	 * 
	 * @param factor
	 * 			The scalar factor.
	 * @return The scalar multiple of this velocity vector with the given factor.
	 * 			| @see implemenation
	 * @throws IllegalComponentException
	 * 			getxComponent() * factor or getyComponent() * factor is not a valid component for any velocity.
	 * 			| !isValidComponent(getxComponent() * factor) || !isValidComponent(getyComponent() * factor)
	 * @throws IllegalArgumentException
	 * 			The given factor is not finite.
	 * 			| !Double.isFinite(factor)
	 */
	@Override
	public Velocity scalarMultiple(double factor) throws IllegalComponentException,	IllegalArgumentException {
		PhysicalVector result = super.scalarMultiple(factor);
		return new Velocity(result.getxComponent(), result.getyComponent());
	}
	
	/**
	 * Check whether this velocity is equal to the given object.
	 * 
	 * @return True iff other is an instance of the class Velocity, 
	 * 			and both xComponents are equal and both yComponents are equal. 
	 * 			| @see implementation
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Velocity))
			return false;
		Velocity otherAsVelocity= (Velocity)other;
		return getxComponent() == otherAsVelocity.getxComponent() && getyComponent() == otherAsVelocity.getyComponent();
	}
	
	/**
	 * Return the hash code for this velocity.
	 */
	@Override
	public int hashCode() {
		final int prime = 101;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(getxComponent());
		result = prime * result + (int) (temp ^ (temp >>> 102));
		temp = Double.doubleToLongBits(getyComponent());
		result = prime * result + (int) (temp ^ (temp >>> 102));
		return result;
	}
}
