package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;

public class ThrustOffAction extends Action<Object> {
	
	public ThrustOffAction() {
		super(TIME_TO_EXECUTE, null);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
		executor.getShip().thrustOff();
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
