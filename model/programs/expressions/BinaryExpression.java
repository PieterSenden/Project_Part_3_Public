package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

/**
 * 
 * @author Joris Ceulemans & Pieter Senden
 *
 * @param <T> Return type of this.evaluate()
 * @param <L> Return type of evaluateLeftSubExpression()
 * @param <R> Return type of evaluateRightSubExpression()
 */

public abstract class BinaryExpression<T, L, R> extends ComposedExpression<T> {
	
	
	public BinaryExpression(Expression<? extends L> leftSubExpression, Expression<? extends R> rightSubExpression) throws IllegalArgumentException {
		if (! canHaveAsSubExpression(leftSubExpression) || ! canHaveAsSubExpression(rightSubExpression))
			throw new IllegalArgumentException();
		this.leftSubExpression = leftSubExpression;
		this.rightSubExpression = rightSubExpression;
	}
	
	public boolean hasAsSubExpression(Expression<?> expression) {
		if (getLeftSubExpression() == expression || getRightSubExpression() == expression)
			return true;
		if (getLeftSubExpression() instanceof ComposedExpression<?> && ((ComposedExpression<?>)getLeftSubExpression()).hasAsSubExpression(expression))
			return true;
		if (getRightSubExpression() instanceof ComposedExpression<?> && ((ComposedExpression<?>)getRightSubExpression()).hasAsSubExpression(expression))
			return true;
		return false;
	}
	
	public Expression<? extends L> getLeftSubExpression() {
		return this.leftSubExpression;
	}
	
	public L evaluateLeftSubExpression(ProgramExecutor executor) {
		return getLeftSubExpression().evaluate(executor);
	}
	
	private final Expression<? extends L> leftSubExpression;
	
	
	public Expression<? extends R> getRightSubExpression() {
		return this.rightSubExpression;
	}
	
	public R evaluateRightSubExpression(ProgramExecutor executor) {
		return getRightSubExpression().evaluate(executor);
	}
	
	private final Expression<? extends R> rightSubExpression;
	
}
