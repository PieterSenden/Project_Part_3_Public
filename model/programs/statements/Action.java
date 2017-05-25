package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.*;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class representing an action.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar	| isValidTimeToExecute(getTimeToExecute())
 */
public abstract class Action<T> extends SingleExpressionStatement<T> {
	
	@Raw
	public Action(double timeToExecute, Expression<? extends T> expression) {
		super(expression);
		if (isValidTimeToExecute(timeToExecute))
			this.timeToExecute = timeToExecute;
		else
			this.timeToExecute = 0;
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException {
		if (!(getExecutable() instanceof Program))
			throw new IllegalMethodCallException();
		if (getTimeToExecute() > executor.getRemainingExecutionTime())
			throw new HoldException();
		executor.decreaseRemainingTime(getTimeToExecute());
		// decreaseRemainingTime() cannot throw an IllegalArgumentException: it only throws such an exception if
		// executor.getRemainingExecutionTime() - getTimeToExecute() is less than zero; but this is already checked by the previous if-statement.
	}
	
	@Basic @Raw
	public double getTimeToExecute() {
		return timeToExecute;
	}
	
	public static boolean isValidTimeToExecute(double timeToExecute) {
		return timeToExecute >= 0;
	}
	
	private final double timeToExecute;
}
