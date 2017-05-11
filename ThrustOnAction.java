package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;

public class ThrustOnAction extends Action<Object> {
	
	public ThrustOnAction() {
		super(TIME_TO_EXECUTE, null);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
		executor.getShip().thrustOn();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
