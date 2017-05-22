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
	
//	public boolean canHaveAsValue(Object value) {
//		if (value == null)
//			return Variable.hasAsSupportedReferenceType(getType());
//		return getType().isAssignableFrom(value.getClass());
//	}
	
	private final Object value;
	
	@Basic
	public Class<?> getType() {
		return this.type;
	}
	
//	public static boolean isValidType(Class<?> type) {
//		return Variable.hasAsSupportedType(type);
//	}
	
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
	//TODO nadenken over het nodig zijn van isValidSupportedType (controleren niet null en type al niet in supportedReferenceTypes).
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
	
	//TODO: equals en hashcode overriden?
}
