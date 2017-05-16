package asteroids.model.programs;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.*;

public class Program extends Executable {
	
	@Raw
	public Program(Statement bodyStatement, List<Function> functions) throws IllegalArgumentException {
		super(bodyStatement);
		for (Function function : functions) {
			addFunction(function);
		}
	}
	
	@Basic @Override
	public Program getProgram() {
		return this;
	}
	
	
	/**
	 * @param name
	 * 			The name of the function to get.
	 * @return Null if there is no function with the given name.
	 */
	@Basic
	public Function getFunctionWithName(String name) {
		return functions.get(name);
	}
	
	public boolean hasFunctionWithName(String name) {
		return getFunctionWithName(name) != null;
	}
	
	public static boolean isValidFunction(Function function) {
		return function != null;
	}
	
	public boolean hasProperFunctions() {
		for (Function function : functions.values()) { 
			if (! isValidFunction(function) || function.getProgram() != this)
				return false;
		}
		return true;				
	}
	
	private void addFunction(Function function) throws IllegalArgumentException {
		if (! isValidFunction(function))
			throw new IllegalArgumentException();
		functions.put(function.getName(), function);
	}
	
	private Map<String, Function> functions = new HashMap<>();
}