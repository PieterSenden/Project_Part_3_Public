package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public abstract class Expression<T> {
	
	public abstract T evaluate(ProgramExecutor executor);
}