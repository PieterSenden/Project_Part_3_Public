package asteroids.model.programs;

import asteroids.model.exceptions.programExceptions.BreakException;

public class BreakStatement extends Statement {
	
	public BreakStatement() {
		super();
	}
	
	@Override
	public void execute() throws BreakException {
		throw new BreakException();
	}

}