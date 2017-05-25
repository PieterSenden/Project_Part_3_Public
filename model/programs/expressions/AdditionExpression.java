package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
/**
 * A class representing an addition expression.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */
import asteroids.model.programs.ProgramExecutor;

public class AdditionExpression extends BinaryExpression<Double, Double, Double> {
	
	public AdditionExpression(Expression<? extends Double> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}
	
	public AdditionExpression(Expression<? extends Double> leftSubExpression,
			UnknownTypeExpression<?> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression.convertToType(Double.class));
	}
	
	public AdditionExpression(UnknownTypeExpression<?> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		this(rightSubExpression, leftSubExpression);
	}
	
	public AdditionExpression(UnknownTypeExpression<?> leftSubExpression,
			UnknownTypeExpression<? extends Double> rightSubExpression) {
		super(leftSubExpression.convertToType(Double.class), rightSubExpression.convertToType(Double.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return evaluateLeftSubExpression(executor) + evaluateRightSubExpression(executor);
	}
}
