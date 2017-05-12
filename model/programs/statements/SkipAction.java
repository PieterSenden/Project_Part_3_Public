package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;

public class SkipAction extends Action<Object> {
	
	public SkipAction() {
		super(TIME_TO_EXECUTE, null);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
