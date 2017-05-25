package asteroids.model.programs;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.exceptions.programExceptions.NoReturnException;
import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class representing an executable.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public abstract class Executable {
	
	public Executable(Statement bodyStatement) throws IllegalArgumentException, IllegalMethodCallException {
		if (! isValidBodyStatement(bodyStatement))
			throw new IllegalArgumentException();
		this.bodyStatement = bodyStatement;
		bodyStatement.setDirectExecutable(this);
	}
	
	public abstract Program getProgram();
	
	@Basic
	public Statement getBodyStatement() {
		return this.bodyStatement;
	}
	
	public static boolean isValidBodyStatement(Statement bodyStatement) {
		return bodyStatement != null;
	}
	
	public boolean hasProperBodyStatement() {
		return isValidBodyStatement(getBodyStatement()) && getBodyStatement().getDirectExecutable() == this;
	}
	
	public void executeBodyStatement(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException, IndexOutOfBoundsException,
															BreakException, ReturnException, NoReturnException, IllegalArgumentException, ArithmeticException {
		getBodyStatement().execute(executor);
	}
	
	private final Statement bodyStatement;
}
