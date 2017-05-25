package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the logical value of a less-than comparison.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class LessThanExpression extends BinaryExpression<Boolean, Double, Double> {

	public LessThanExpression(Expression<? extends Double> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}
	
	public LessThanExpression(Expression<? extends Double> leftSubExpression,
			UnknownTypeExpression<?> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression.convertToType(Double.class));
	}
	
	public LessThanExpression(UnknownTypeExpression<?> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		this(rightSubExpression, leftSubExpression);
	}
	
	public LessThanExpression(UnknownTypeExpression<?> leftSubExpression,
			UnknownTypeExpression<? extends Double> rightSubExpression) {
		super(leftSubExpression.convertToType(Double.class), rightSubExpression.convertToType(Double.class));
	}

	@Override
	public Boolean evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return evaluateLeftSubExpression(executor) < evaluateRightSubExpression(executor);
	}

}
