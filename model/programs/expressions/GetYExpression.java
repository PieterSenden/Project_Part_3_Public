package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetYExpression extends UnaryExpression<Double, Entity> {
	
	public GetYExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetYExpression(UnknownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return evaluateSubExpression(executor).getPosition().getyComponent();
	}
}
