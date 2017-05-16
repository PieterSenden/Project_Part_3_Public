package asteroids.model.programs.expressions;

import be.kuleuven.cs.som.annotate.*;

public abstract class UnknownTypeExpression<T> extends Expression<T> {
	
	public UnknownTypeExpression(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	public abstract <S> UnknownTypeExpression<S> convertToType(Class<S> type);
	
	@Basic
	public String getName() {
		return this.name;
	}
	
	public static boolean isValidName(String name) {
		return (name != null) && (name != "");
	}
	
	private final String name;
}