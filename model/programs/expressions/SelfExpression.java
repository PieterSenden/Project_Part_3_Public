package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class SelfExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) throws IllegalArgumentException {
		if (executor == null)
			throw new IllegalArgumentException();
		return executor.getShip();
	}

}
