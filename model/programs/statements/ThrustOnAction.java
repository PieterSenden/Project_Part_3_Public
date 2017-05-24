package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.NullExpression;

public class ThrustOnAction extends Action<Object> {
	
	public ThrustOnAction() {
		super(TIME_TO_EXECUTE, new NullExpression());
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException {
		super.execute(executor);
		executor.getShip().thrustOn();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
