package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the logical value of an equality.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class EqualityExpression extends BinaryExpression<Boolean, Object, Object> {
	
	public EqualityExpression(Expression<? extends Object> leftSubExpression,
			Expression<? extends Object> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}

	@Override
	public Boolean evaluate(ProgramExecutor executor) {
		return evaluateLeftSubExpression(executor) == evaluateRightSubExpression(executor);
	}

}
