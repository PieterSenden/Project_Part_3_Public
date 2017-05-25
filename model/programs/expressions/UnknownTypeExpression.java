package asteroids.model.programs.expressions;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing an expression of which the type of the evaluation is unknown until runtime.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public abstract class UnknownTypeExpression<T> extends Expression<T> {
	
	public UnknownTypeExpression(String name) throws IllegalArgumentException {
		if (! canHaveAsName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	public abstract <S> UnknownTypeExpression<S> convertToType(Class<S> type);
	
	@Basic
	public String getName() {
		return this.name;
	}
	
	public boolean canHaveAsName(String name) {
		return (name != null) && (name != "");
	}
	
	private final String name;
}