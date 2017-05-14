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
	public Variable(Object value, Class<?> type) throws IllegalArgumentException {
		if (! isValidType(type))
			throw new IllegalArgumentException();
		this.type = type;
		if (!canHaveAsValue(value))
			throw new IllegalArgumentException();
		this.value = value;
	}
	
	@Raw
	public Variable(Object value) throws IllegalArgumentException {
		if (value == null)
			throw new IllegalArgumentException("A variable containing null must be constructed with a given type");
		Class<?> variableType = value.getClass();
		boolean supportedTypeFound = false;
		//We look for the most general supported type that the given value belongs to.
		for (Class<?> supportedType : Program.getSupportedTypes()) {
			if (supportedType.isAssignableFrom(variableType)) {
				variableType = supportedType;
				supportedTypeFound = true;
			}
		}
		if (!supportedTypeFound)
			throw new IllegalArgumentException("Variables of this type are not supported.");
		this.type = variableType;
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
	
	public static boolean isValidType(Class<?> type) {
		return Program.hasAsSupportedType(type);
	}
	
	private final Class<?> type;
	
	//TODO: equals en hashcode overriden?
}
