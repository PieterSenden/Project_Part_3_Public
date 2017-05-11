package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

public abstract class SingleExpressionStatement<T> extends Statement {
	
	public SingleExpressionStatement(Expression<T> expression) throws IllegalArgumentException {
		if (! canHaveAsExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}
	
	public Expression<T> getExpression() {
		return this.expression;
	}
	
	public boolean canHaveAsExpression(Expression<T> expression) {
		return expression != null;
	}
	
	public T evaluateExpression() {
		return getExpression().evaluate();
	}
	
	
	private final Expression<T> expression;
}