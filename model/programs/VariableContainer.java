package asteroids.model.programs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import asteroids.model.exceptions.IllegalMethodCallException;
import be.kuleuven.cs.som.annotate.Basic;


/**
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar | hasProperProgramExecutor()
 *
 */

public class VariableContainer {
	
	public VariableContainer(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor.getVariableContainer() != null)
			throw new IllegalMethodCallException();
		this.programExecutor = executor;
	}
	
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
	
	public void assignVariable(String name, Object value) throws IllegalArgumentException, IllegalMethodCallException {
		if (getProgramExecutor().getProgram().hasFunctionWithName(name))
			throw new IllegalMethodCallException();
		Map<String,Variable> scope;
		if (! localVariables.empty())
			scope = localVariables.peek();
		else
			scope = globalVariables;
		
		if (scope.containsKey(name)){
			Variable oldVariable = scope.get(name);
			if (Variable.isValidVariable(oldVariable.getType(), value))
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
	 * Method to create a new scope when a function call is being processed.
	 */
	public void createNewScope() {
		Map<String,Variable> newScope = new HashMap<>();
		localVariables.push(newScope);
	}
	
	/**
	 * Method to delete the current scope when the current function call is finished.
	 */
	public void deleteCurrentScope() {
		localVariables.pop();
	}
	
	public void reset() {
		globalVariables = new HashMap<>();
		localVariables = new Stack<>();
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
	
	@Basic
	public ProgramExecutor getProgramExecutor() {
		return this.programExecutor;
	}
	
	public static boolean isValidProgramExecutor(ProgramExecutor executor) {
		return executor != null;
	}
	
	public boolean hasProperProgramExecutor() {
		return isValidProgramExecutor(getProgramExecutor()) && getProgramExecutor().getVariableContainer() == this;
	}
	
	private final ProgramExecutor programExecutor;
}
