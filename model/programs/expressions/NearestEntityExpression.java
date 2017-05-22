package asteroids.model.programs.expressions;

import java.util.Optional;
import java.util.stream.Stream;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.*;
import be.kuleuven.cs.som.annotate.Basic;

public class NearestEntityExpression extends Expression<Entity> {
	
	public NearestEntityExpression(Class<? extends Entity> entityType) throws IllegalArgumentException {
		if (isValidEntityType(entityType))
			throw new IllegalArgumentException();
		this.entityType = entityType;
	}
	
	
	public Stream<Entity> stream(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null || executor.getShip().getWorld() == null)
			throw new IllegalMethodCallException();
		Stream.Builder<Entity> builder = Stream.builder();
		for (Entity entity : executor.getShip().getWorld().getEntities())
			builder.accept(entity);
		return builder.build();
	}
	
	@Override
	public Entity evaluate(ProgramExecutor executor) {
		Stream<Entity> entityStream = stream(executor);
		Ship currentShip = executor.getShip();
		Optional<Entity> result = entityStream.filter(e -> getEntityType().isAssignableFrom(e.getClass()))
					.filter(e -> e != currentShip)
					.reduce( (e1, e2) -> (Entity.getDistanceBetween(e1, currentShip) <= Entity.getDistanceBetween(e2, currentShip)) ? e1 : e2);
		return result.isPresent() ? result.get() : null;
	}
	
	@Basic
	public Class<? extends Entity> getEntityType() {
		return this.entityType;
	}
	
	public static boolean isValidEntityType(Class<? extends Entity> entityType) {
		return (entityType == null);
	}
	
	
	private final Class<? extends Entity> entityType; 
}
