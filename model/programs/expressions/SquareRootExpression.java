package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

public class SquareRootExpression extends UnaryExpression<Double, Double> {
	
	public SquareRootExpression(Expression<? extends Double> radicandExpression) {
		super(radicandExpression);
	}
	
	public SquareRootExpression(UnknownTypeExpression<?> radicandExpression) {
		super(radicandExpression.convertToType(Double.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		double radicand = evaluateSubExpression(executor);
		if (radicand < 0)
			throw new ArithmeticException("Square root of negative number.");
		return Math.sqrt(radicand);
	}
	
}
