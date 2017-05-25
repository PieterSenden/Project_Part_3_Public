package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.NullExpression;

/**
 * A class representing a thrust-off action.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class ThrustOffAction extends Action<Object> {
	
	public ThrustOffAction() {
		super(TIME_TO_EXECUTE, new NullExpression());
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException {
		super.execute(executor);
		executor.getShip().thrustOff();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
