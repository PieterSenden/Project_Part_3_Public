package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

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
		return new FunctionCallExpression<S>(getName());
	}

	@Override
	public T evaluate(ProgramExecutor executor) {
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
