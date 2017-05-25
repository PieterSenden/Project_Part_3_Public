package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

/**
 * A class representing an expression evaluating to the radius of the ship of a program executor.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class GetRadiusExpression extends UnaryExpression<Double, Entity> {
	
	public GetRadiusExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetRadiusExpression(UnknownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return evaluateSubExpression(executor).getRadius();
	}
	
}
