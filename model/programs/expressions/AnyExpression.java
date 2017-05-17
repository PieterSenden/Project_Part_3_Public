package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class AnyExpression extends Expression<Entity> {

	@Override
	public Entity evaluate(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null || executor.getShip().getWorld() == null)
			throw new IllegalMethodCallException();
		Entity[] entitiesInWorldOfShip = (Entity[])executor.getShip().getWorld().getEntities().toArray();
		return entitiesInWorldOfShip.length == 0 ? null : entitiesInWorldOfShip[(int)(Math.random() * entitiesInWorldOfShip.length)];
	}

}
