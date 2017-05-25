package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing an assignment statement.
 *  
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar	| isValidVariableName(getVariableName())
 */
public class AssignmentStatement extends SingleExpressionStatement<Object> {
	@Raw
	public AssignmentStatement(String variableName, Expression<?> value) throws IllegalArgumentException {
		super(value);
		if (!isValidVariableName(variableName))
			throw new IllegalArgumentException("Illegal variable name.");
		this.variableName = variableName;
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, NullPointerException, IllegalArgumentException {
		executor.getVariableContainer().assignVariable(variableName, evaluateExpression(executor));
	}
	
	@Basic
	public String getVariableName() {
		return variableName;
	}
	
	public static boolean isValidVariableName(String name) {
		return (name != null) && (name != "");
	}
	
	private final String variableName;
}
