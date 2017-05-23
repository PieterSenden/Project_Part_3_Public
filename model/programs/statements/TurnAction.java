package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import asteroids.util.internal.InternalUtils;

public class TurnAction extends Action<Double> {
	
	public TurnAction(Expression<Double> angleExpression) {
		super(TIME_TO_EXECUTE, angleExpression);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
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
