package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

/**
 * A class representing an expression evaluating to the ship of a program executor.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class SelfExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) throws IllegalArgumentException {
		if (executor == null)
			throw new IllegalArgumentException();
		return executor.getShip();
	}

}
