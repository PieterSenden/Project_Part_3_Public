package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

/**
 * A class representing an expression evaluating to the y-component of the position of the ship of a program executor.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class GetYExpression extends UnaryExpression<Double, Entity> {
	
	public GetYExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetYExpression(UnknownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getPosition().getyComponent();
	}
}
