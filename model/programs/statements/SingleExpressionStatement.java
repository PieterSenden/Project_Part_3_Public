package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

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
	
	public T evaluateExpression() {
		return getExpression().evaluate();
	}
	
	
	private final Expression<? extends T> expression;
}