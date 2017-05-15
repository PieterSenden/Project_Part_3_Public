package asteroids.model.programs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import asteroids.model.representation.Entity;
import be.kuleuven.cs.som.annotate.*;

public class Program extends Executable {
	
	@Override
	public Program getProgram() {
		return this;
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
}
