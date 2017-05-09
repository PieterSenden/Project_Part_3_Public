package asteroids.model.programs;

public abstract class SingleExpressionStatement<T> extends Statement {
	
	public SingleExpressionStatement(Expression<T> expression) {
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