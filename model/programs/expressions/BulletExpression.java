package asteroids.model.programs.expressions;

import java.util.Optional;
import java.util.stream.Stream;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Bullet;

public class BulletExpression extends Expression<Bullet> {
	
	public Stream<Bullet> stream(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null || executor.getShip().getFiredBullets() == null)
			throw new IllegalMethodCallException();
		Stream.Builder<Bullet> builder = Stream.builder();
		for (Bullet bullet: executor.getShip().getFiredBullets())
			builder.accept(bullet);
		return builder.build();
	}
	
	@Override
	public Bullet evaluate(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null)
			throw new IllegalMethodCallException();
		if (executor.getShip().getFiredBullets().isEmpty())
			return null;
		Stream<Bullet> bulletStream = stream(executor);
		Optional<Bullet> result = bulletStream.filter(b -> ! b.isTerminated())
							.reduce( (b1, b2) -> b1);
		return result.isPresent() ? result.get() : null;
		
	}

}
