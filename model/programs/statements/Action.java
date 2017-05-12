package asteroids.model.programs.statements;

import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;


/**
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar	| isValidTimeToExecute(getTimeToExecute())
 */
public abstract class Action<T> extends SingleExpressionStatement<T> {
	
	@Raw
	public Action(double timeToExecute, Expression<T> expression) {
		super(expression);
		if (isValidTimeToExecute(timeToExecute))
			this.timeToExecute = timeToExecute;
		else
			this.timeToExecute = 0;
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		if (getTimeToExecute() > executor.getRemainingTime())
			throw new HoldException();
		executor.decreaseRemainingTime(getTimeToExecute());
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
