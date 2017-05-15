package asteroids.model.programs.expressions;


public abstract class ComposedExpression<T> extends Expression<T> {
	
	public abstract boolean hasAsSubExpression(Expression<?> expression);
	
	public boolean canHaveAsSubExpression(Expression<?> expression) {
		if (expression == null || expression == this)
			return false;
		if (expression instanceof ComposedExpression<?>)
			return !((ComposedExpression<?>)expression).hasAsSubExpression((Expression<T>)this);
		return true;
	}
}
