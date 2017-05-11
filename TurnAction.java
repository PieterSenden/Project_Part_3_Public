package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;

public class TurnAction extends Action<Double> {
	
	public TurnAction(Expression<Double> angleExpression) {
		super(TIME_TO_EXECUTE, angleExpression);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
		executor.getShip().turn(evaluateExpression());
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
