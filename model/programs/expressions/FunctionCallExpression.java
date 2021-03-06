package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the return value of a function.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class FunctionCallExpression<T> extends UnknownTypeExpression<T> {

	public FunctionCallExpression(String name, Expression<?>... actualArgumentExpressions) throws IllegalArgumentException {
		super(name);
		for (Expression<?> expression : actualArgumentExpressions)
			if (!isValidActualArgumentExpression(expression))
				throw new IllegalArgumentException();
		this.actualArgumentExpressions = actualArgumentExpressions;
		
	}

	@Override
	public <S> FunctionCallExpression<S> convertToType(Class<S> type) {
		return new FunctionCallExpression<S>(getName(), actualArgumentExpressions);
	}

	@Override
	public T evaluate(ProgramExecutor executor) throws IllegalMethodCallException, IndexOutOfBoundsException, ArithmeticException {
		Object[] values = new Object[actualArgumentExpressions.length];
		for (int i = 0; i < actualArgumentExpressions.length; i++) {
			values[i] = actualArgumentExpressions[i].evaluate(executor);
		}
		return (T)executor.getProgram().getFunctionWithName(getName()).evaluate(executor, values);
	}
	
	public static boolean isValidActualArgumentExpression(Expression<?> argumentExpression) {
		return argumentExpression != null;
	}
	
	private final Expression<?>[] actualArgumentExpressions;
	
}
