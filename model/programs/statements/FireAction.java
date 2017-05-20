package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.NullExpression;

public class FireAction extends Action<Object> {
	
	public FireAction() {
		super(TIME_TO_EXECUTE, new NullExpression());
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
		executor.getShip().fireBullet();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
