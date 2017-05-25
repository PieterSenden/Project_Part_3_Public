package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the square root of its subexpression.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class SquareRootExpression extends UnaryExpression<Double, Double> {
	
	public SquareRootExpression(Expression<? extends Double> radicandExpression) {
		super(radicandExpression);
	}
	
	public SquareRootExpression(UnknownTypeExpression<?> radicandExpression) {
		super(radicandExpression.convertToType(Double.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		double radicand = evaluateSubExpression(executor);
		if (radicand < 0)
			throw new ArithmeticException("Square root of negative number.");
		return Math.sqrt(radicand);
	}
	
}
