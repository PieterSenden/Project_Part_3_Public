package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;

/**
 * A class representing a statement containing a single expression.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar | canHaveAsExpression(getExpression())
 */
public abstract class SingleExpressionStatement<T> extends Statement {
	
	public SingleExpressionStatement(Expression<? extends T> expression) throws IllegalArgumentException {
		if (! canHaveAsExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}
	
	public Expression<? extends T> getExpression() {
		return this.expression;
	}
	
	public boolean canHaveAsExpression(Expression<?> expression) {
		return expression != null;
	}
	
	public T evaluateExpression(ProgramExecutor executor) {
		return getExpression().evaluate(executor);
	}
	
	
	private final Expression<? extends T> expression;
}