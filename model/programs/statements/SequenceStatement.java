package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.exceptions.programExceptions.NoReturnException;
import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.ProgramExecutor;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a sequence statement.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar	| hasProperEnclosedStatements()
 *
 */

public class SequenceStatement extends Statement implements ComposedStatement {
	@Raw
	public SequenceStatement(Statement... enclosedStatements) throws IllegalArgumentException {
		if (enclosedStatements == null || enclosedStatements.length == 0)
			throw new IllegalArgumentException();
		for (Statement statement : enclosedStatements)
			if (! canHaveAsEnclosedStatement(statement))
				throw new IllegalArgumentException();
		this.enclosedStatements = enclosedStatements;
		for (Statement statement : enclosedStatements)
			statement.setEnclosingStatement(this);
		// setEnclosingStatement() cannot throw an IllegalMethodCallException since getExecutable() is at this point still null.
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException, IndexOutOfBoundsException,
															BreakException, ReturnException, NoReturnException, IllegalArgumentException {
		if (executor.getCurrentExecutionListLength() <= getDepth())
			executor.setExecutionPositionAt(getDepth(), 1);
		for (int i = executor.getExecutionPositionAt(getDepth()); i <= getNbOfEnclosedStatements(); i++) {
			getEnclosedStatementAt(i).execute(executor);
			stepExecutionPosition(executor);
		}
		executor.removeExecutionPosition();
	}
	
	private void stepExecutionPosition(ProgramExecutor executor) throws IllegalArgumentException {
		executor.setExecutionPositionAt(getDepth(), executor.getExecutionPositionAt(getDepth()) + 1);
	}
	
	@Override
	public boolean hasAsSubStatement(Statement statement) {
		for (Statement enclosed : enclosedStatements) {
			if (enclosed == statement)
				return true;
			else if (enclosed instanceof ComposedStatement &&
						((ComposedStatement)enclosed).hasAsSubStatement(statement))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasAsEnclosedStatement(Statement statement) {
		for (Statement stat : enclosedStatements) {
			if (stat == statement) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canHaveAsEnclosedStatement(Statement statement) {
		if (statement == null || statement == this)
			return false;
		if (statement instanceof ComposedStatement)
			return !((ComposedStatement)statement).hasAsSubStatement(this);
		return true;
	}
	
	@Override
	public boolean hasProperEnclosedStatements() {
		for (int i = 1; i <= getNbOfEnclosedStatements(); i++) {
			if (! canHaveAsEnclosedStatementAt(i, getEnclosedStatementAt(i)) || getEnclosedStatementAt(i).getEnclosingStatement() != this)
				return false;
		}
		return true;
	}
	
	@Basic
	public int getNbOfEnclosedStatements() {
		return enclosedStatements.length;
	}
	
	public boolean canHaveAsEnclosedStatementAt(int index, Statement statement) {
		return (1 <= index) && (index <= getNbOfEnclosedStatements()) && canHaveAsEnclosedStatement(statement);
	}
	
	@Basic
	public Statement getEnclosedStatementAt(int index) throws IndexOutOfBoundsException {
		return enclosedStatements[index - 1];
	}
	
	private final Statement[] enclosedStatements;
}
