package asteroids.model.programs;

import be.kuleuven.cs.som.annotate.*;
 /**
  * 
  * @author Joris Ceulemans & Pieter Senden
  * @version 3.0
  * 
  * @invar	| isValidType(getType())
  * @invar	| canHaveAsValue(getValue())
  *
  */
@Value
public class Variable {
	@Raw
	public <T> Variable(T value, Class<T> type) throws IllegalArgumentException {
		if (! isValidType(type))
			throw new IllegalArgumentException();
		this.type = type;
		if (!canHaveAsValue(value))
			throw new IllegalArgumentException();
		this.value = value;
	}
	
	@Basic
	public Object getValue() {
		return this.value;
	}
	
	public boolean canHaveAsValue(Object value) {
		if (value == null)
			return Program.hasAsSupportedReferenceType(getType());
		return getType().isAssignableFrom(value.getClass());
	}
	
	private final Object value;
	
	@Basic
	public Class<?> getType() {
		return this.type;
	}
	
	public boolean isValidType(Class<?> type) {
		return type != null;
	}
	
	private final Class<?> type;
}
