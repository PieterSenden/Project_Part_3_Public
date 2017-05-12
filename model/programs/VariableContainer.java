package asteroids.model.programs;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import be.kuleuven.cs.som.annotate.Basic;

public class VariableContainer {
	@Basic
	public Variable getVariableWithName(String name) {
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
	
	public void assignVariable(String name, Object value) {
		if (! localVariables.empty()) {
			Map<String,Variable> scope = localVariables.peek();
			if (scope.containsKey(name)){
				
			}
			else {
				
			}
		}
	}
	
	private Map<String,Variable> globalVariables = new HashMap<>();
	
	private Stack<Map<String,Variable>> localVariables = new Stack<>();
}
