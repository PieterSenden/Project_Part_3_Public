package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

/**
 * A class representing an expression evaluating to null.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class NullExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) {
		return null;
	}

}
