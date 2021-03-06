package asteroids.model.programs.expressions;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing an expression evaluating to the orientation of the ship of a program executor.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class GetDirExpression extends Expression<Double> {
		
	@Override
	public Double evaluate(ProgramExecutor executor) throws IllegalMethodCallException {
		if (executor == null || executor.getShip() == null)
			throw new IllegalMethodCallException();
		return executor.getShip().getOrientation();
	}
	
}
