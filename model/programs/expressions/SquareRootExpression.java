package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class SquareRootExpression extends UnaryExpression<Double, Double> {
	
	public SquareRootExpression(Expression<? extends Double> radicandExpression) {
		super(radicandExpression);
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		double radicand = evaluateSubExpression(executor);
		if (radicand < 0)
			throw new ArithmeticException("Square root of negative number.");
		return Math.sqrt(radicand);
	}
	
}
