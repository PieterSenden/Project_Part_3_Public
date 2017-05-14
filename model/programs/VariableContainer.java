package asteroids.model.programs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import be.kuleuven.cs.som.annotate.Basic;

public class VariableContainer {
	@Basic
	public Variable getVariableWithName(String name) throws NoSuchElementException {
		if (! hasVariableWithName(name))
			throw new NoSuchElementException();
		if (! localVariables.empty() && localVariables.peek().containsKey(name))
			return localVariables.peek().get(name);
		return globalVariables.get(name);
	}
	
	public boolean hasVariableWithName(String name) {
		if (! localVariables.empty() && localVariables.peek().containsKey(name))
			return true;
		return globalVariables.containsKey(name);
	}
	
	public void assignVariable(String name, Object value) throws IllegalArgumentException {
		Map<String,Variable> scope;
		if (! localVariables.empty())
			scope = localVariables.peek();
		else
			scope = globalVariables;
		
		if (scope.containsKey(name)){
			Variable oldVariable = scope.get(name);
			if (oldVariable.canHaveAsValue(value))
				scope.put(name, new Variable(value, oldVariable.getType()));
				// The new variable object has the same type as the variable object it replaces. In this way, it is enforced that the type
				// of a variable is not changed during the execution of a program/function call.
			else
				throw new IllegalArgumentException("This value cannot be assigned to the variable with the given name.");
		}
		else {
			Variable newVariable = new Variable(value);
			scope.put(name, newVariable);
		}
	}
	
	/**
	 * Map containing the global variables. The keys are the names of the variables, the values are variable objects.
	 */
	private Map<String,Variable> globalVariables = new HashMap<>();
	
	/**
	 * A stack of maps containing the local variables for the execution of a program. The keys and values of the map are the same as described
	 * for global variables. The top element of the stack contains the map for the local variables of the currently executing function call.
	 * If the stack is empty, this means that no function code, but program code is being executed.
	 */
	private Stack<Map<String,Variable>> localVariables = new Stack<>();
}
