package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.NullExpression;

public class SkipAction extends Action<Object> {
	
	public SkipAction() {
		super(TIME_TO_EXECUTE, new NullExpression());
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException {
		super.execute(executor);
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
