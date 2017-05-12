package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;

public class FireAction extends Action<Object> {
	
	public FireAction() {
		super(TIME_TO_EXECUTE, null);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
		executor.getShip().fireBullet();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
