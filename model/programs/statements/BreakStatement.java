package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.programs.ProgramExecutor;

public class BreakStatement extends Statement {
	
	public BreakStatement() {
		super();
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, NullPointerException, BreakException {
		throw new BreakException();
	}

}