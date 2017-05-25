package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetVyExpression extends UnaryExpression<Double, Entity> {
	
	public GetVyExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetVyExpression(UnknownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return evaluateSubExpression(executor).getVelocity().getyComponent();
	}
}