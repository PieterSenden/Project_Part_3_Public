package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

public class NotExpression extends UnaryExpression<Boolean, Boolean> {

	public NotExpression(Expression<? extends Boolean> subExpression) throws IllegalArgumentException {
		super(subExpression);
	}
	
	public NotExpression(UnknownTypeExpression<?> subExpression) throws IllegalArgumentException {
		super(subExpression.convertToType(Boolean.class));		
	}
	

	@Override
	public Boolean evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return ! evaluateSubExpression(executor);
	}
	
}
