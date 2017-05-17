package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class NullExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) {
		return null;
	}

}
