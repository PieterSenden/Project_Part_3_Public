package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class SelfExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null)
			throw new IllegalMethodCallException();
		return executor.getShip();
	}

}
