package asteroids.model.programs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import asteroids.model.representation.Entity;
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
		if (! isValidVariable(type, value))
			throw new IllegalArgumentException();
		this.type = type;
		this.value = value;
	}
	
	@Raw
	public Variable(Object value) throws IllegalArgumentException {
		if (value != null) {
			Class<?> variableType = getMostGeneralSupportedTypeFor(value);
			if (variableType == null)
				throw new IllegalArgumentException("Variables of this type are not supported.");
			this.type = variableType;
			if (! isValidVariable(getType(), value))
				throw new IllegalArgumentException();
			this.value = value;
		}
		else {
			this.value = value;
			this.type = null;
		}
	}
	
	public static Class<?> getMostGeneralSupportedTypeFor(Object value) {
		Class<?> result = value.getClass();
		boolean supportedTypeFound = false;
		for (Class<?> supportedType : Variable.getSupportedTypes()) {
			if (supportedType.isAssignableFrom(result)) {
				result = supportedType;
				supportedTypeFound = true;
			}
		}
		if (supportedTypeFound)
			return result;
		else
			return null;
	}
	
	@Basic
	public Object getValue() {
		return this.value;
	}
	
	private final Object value;
	
	@Basic
	public Class<?> getType() {
		return this.type;
	}
	
	private final Class<?> type;
	
	public static boolean isValidVariable(Class<?> type, Object value) {
		if (type == null)
			return value == null;
		else
			return (value == null) ? hasAsSupportedReferenceType(type) : (hasAsSupportedType(type) && type.isAssignableFrom(value.getClass())); 
	}
	
	@Immutable
	public static boolean hasAsSupportedValueType(Class<?> type) {
		if (type == null)
			return false;
		for (Class<?> supportedType : supportedValueTypes) {
			if (supportedType.isAssignableFrom(type))
				return true;
		}
		return false;
	}
  
	private static final Set<Class<?>> supportedValueTypes = new HashSet<>(Arrays.asList(Double.class, Boolean.class));
	
	@Immutable
	public static boolean hasAsSupportedReferenceType(Class<?> type) {
		if (type == null)
			return false;
		for (Class<?> supportedType : supportedReferenceTypes) {
			if (supportedType.isAssignableFrom(type))
				return true;
		}
		return false;
	}
	
	private static final Set<Class<?>> supportedReferenceTypes = new HashSet<>(Arrays.asList(Entity.class));
	
	@Immutable
	public static boolean hasAsSupportedType(Class<?> type) {
		return (hasAsSupportedValueType(type) || hasAsSupportedReferenceType(type));
	}
	
	public static Set<Class<?>> getSupportedTypes() {
		Set<Class<?>> result = new HashSet<>(supportedValueTypes);
		result.addAll(supportedReferenceTypes);
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Variable))
			return false;
		Variable otherAsVariable = (Variable)other;
		return getType() == otherAsVariable.getType() && getValue() == otherAsVariable.getValue();
	}
	
	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Variable(type: " + getType().getName() + ", value: " + getValue().toString();
	}
}
