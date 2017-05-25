package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the value of a certain variable.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class ReadVariableExpression<T> extends UnknownTypeExpression<T> {

	public ReadVariableExpression(String name) throws IllegalArgumentException {
		super(name);
	}

	@Override
	public <S> ReadVariableExpression<S> convertToType(Class<S> type) {
		return new ReadVariableExpression<S>(getName());
	}

	@Override
	public T evaluate(ProgramExecutor executor) {
		return (T)executor.getVariableContainer().getVariableWithName(getName()).getValue();
		// Unavoidable unchecked type cast: if the value returned by this expression is used, it is at that moment checked by the Java Virtual Machine
		// if the dynamic type is compatible with the method using the value returned by this expression.
	}

}
