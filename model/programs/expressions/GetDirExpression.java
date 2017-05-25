package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

public class GetDirExpression extends Expression<Double> {
		
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null)
			throw new IllegalMethodCallException();
		return executor.getShip().getOrientation();
	}
	
}
