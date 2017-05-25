package asteroids.model.programs.statements;

import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.programs.ProgramExecutor;

/**
 * A class representing a break statement.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class BreakStatement extends Statement {
	
	@Override
	public void execute(ProgramExecutor executor) throws BreakException {
		throw new BreakException();
	}

}