package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import be.kuleuven.cs.som.annotate.*;

public class ReadParameterExpression<T> extends UnknownTypeExpression<T> {
	
	public ReadParameterExpression(String name) throws IllegalArgumentException {
		super(name);
		if ( !canHaveAsName(name))
			throw new IllegalArgumentException();
		parameterNumber = Integer.parseInt(name.substring(1));
	}

	@Override
	public <S> ReadParameterExpression<S> convertToType(Class<S> type) {
		return new ReadParameterExpression<S>(getName());
	}
	
	@Override
	public T evaluate(ProgramExecutor executor) {
		return (T)executor.getParameterContainer().getParameterValueWithIndex(getParameterNumber());
	}
	
	@Override
	public boolean canHaveAsName(String name) {
		return name.matches("\\$[1-9][0-9]*");
	}
	
	@Basic
	public int getParameterNumber() {
		return parameterNumber;
	}
	
	public static boolean isValidParamterNumber(int number) {
		return number > 0;
	}
	
	private final int parameterNumber;
}
