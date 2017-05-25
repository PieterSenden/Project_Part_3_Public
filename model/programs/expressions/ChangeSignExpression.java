package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the opposite of its subexpression.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class ChangeSignExpression extends UnaryExpression<Double, Double> {
	
	public ChangeSignExpression(Expression<? extends Double> subExpression) throws IllegalArgumentException {
		super(subExpression);
	}
	
	public ChangeSignExpression(UnknownTypeExpression<?> subExpression) throws IllegalArgumentException {
		super(subExpression.convertToType(Double.class));		
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		return -evaluateSubExpression(executor); 
	}
}
