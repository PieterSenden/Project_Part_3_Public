package asteroids.model.programs;

import java.util.Stack;

import asteroids.model.exceptions.IllegalMethodCallException;
import be.kuleuven.cs.som.annotate.*;

public class ParameterContainer {
	
	@Basic
	public Object getParameterValueWithIndex(int index) throws IllegalMethodCallException, IndexOutOfBoundsException {
		if (parameters.empty())
			throw new IllegalMethodCallException();
		if (index < 1 || parameters.peek().length < index)
			throw new IndexOutOfBoundsException(); 
		return parameters.peek()[index - 1];
	}
	
	public int getNumberOfParametersInCurrentScope() throws IllegalMethodCallException {
		if (parameters.empty())
			throw new IllegalMethodCallException();
		return parameters.peek().length;
	}
	
	/**
	 * Method to create a new scope when a function call is being processed.
	 */
	public void createNewScope(Object[] parameterValues) throws IllegalArgumentException {
		if (parameterValues == null)
			throw new IllegalArgumentException();
		parameters.push(parameterValues);
	}
	
	/**
	 * Method to delete the current scope when the current function call is finished.
	 */
	public void deleteCurrentScope() {
		parameters.pop();
	}
	
	/**
	 * A stack of arrays containing the parameters for the execution of a function.
	 */
	private Stack<Object[]> parameters = new Stack<>();
}