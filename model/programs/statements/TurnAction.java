package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
//import asteroids.util.internal.InternalUtils;

/**
 * A class representing a turn action.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class TurnAction extends Action<Double> {
	
	public TurnAction(Expression<Double> angleExpression) {
		super(TIME_TO_EXECUTE, angleExpression);
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException {
		super.execute(executor);
		try {
//			double turnAngle = evaluateExpression(executor);
//			turnAngle = InternalUtils.toProperAngleDelta(executor.getShip().getOrientation(), turnAngle);
//			executor.getShip().turn(turnAngle);
			executor.getShip().turn(evaluateExpression(executor));
		}
		catch (AssertionError err) {
			//By using toProperAngleDelta, this assertion error should not be thrown. We include this catcher as a safety measure.
			;
		}
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
