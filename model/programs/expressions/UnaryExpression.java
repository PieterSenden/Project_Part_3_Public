package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

/**
 * 
 * @author Joris Ceulemans & Pieter Senden
 *
 * @param <T> Return type of this.evaluate()
 * @param <S> Return type of evaluateSubExpression()
 */
public abstract class UnaryExpression<T, S> extends ComposedExpression<T> {
	
	
	public UnaryExpression(Expression<? extends S> subExpression) throws IllegalArgumentException {
		if (! canHaveAsSubExpression(subExpression))
			throw new IllegalArgumentException();
		this.subExpression = subExpression;
	}
	
	public S evaluateSubExpression(ProgramExecutor executor) {
		return getSubExpression().evaluate(executor);
	}
	
	public Expression<? extends S> getSubExpression() {
		return this.subExpression;
	}
	
	private final Expression<? extends S> subExpression;
}
