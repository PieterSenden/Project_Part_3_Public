package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.NullExpression;

/**
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class SkipAction extends Action<Object> {
	
	public SkipAction() {
		super(TIME_TO_EXECUTE, new NullExpression());
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		super.execute(executor);
	}
	
	public static final double TIME_TO_EXECUTE = 0.2;
}
