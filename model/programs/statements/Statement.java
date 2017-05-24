package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.BreakException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.exceptions.programExceptions.NoReturnException;
import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar | hasProperEnclosingStatement()
 * @invar | hasProperDirectExecutable()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */
public abstract class Statement {
	
	public abstract void execute(ProgramExecutor executor) throws IllegalMethodCallException, HoldException, NullPointerException,
																	BreakException, ReturnException, NoReturnException ;
	
	public Executable getExecutable() {
		if (getEnclosingStatement() == null)
			return getDirectExecutable();
		return getEnclosingStatement().getExecutable();
	}
	
	public Program getProgram() {
		return (getExecutable() == null ? null : getExecutable().getProgram());
	}
	
	@Basic
	public Executable getDirectExecutable() {
		return this.directExecutable;
	}
	
	public boolean canHaveAsDirectExecutable(Executable exe) {
		return (exe == null) || Executable.isValidBodyStatement(this);
	}
	
	public boolean hasProperDirectExecutable() {
		if (getDirectExecutable() == null)
			return true;
		if (!canHaveAsDirectExecutable(getDirectExecutable()))
			return false;
		return getDirectExecutable().getBodyStatement() == this;
	}
	
	
	public void setDirectExecutable(Executable executable) throws IllegalArgumentException, IllegalMethodCallException {
		if (! canHaveAsDirectExecutable(executable))
			throw new IllegalArgumentException();
		if (executable.getBodyStatement() != this || getExecutable() != null || getEnclosingStatement() != null)
			throw new IllegalMethodCallException();
		this.directExecutable = executable;
		
	}
	
	private Executable directExecutable;
	
	@Basic
	public ComposedStatement getEnclosingStatement() {
		return this.enclosingStatement;
	}
	
	public boolean canHaveAsEnclosingStatement(ComposedStatement enclosingStatement) {
		return (enclosingStatement == null) || enclosingStatement.canHaveAsEnclosedStatement(this);
	}
	
	public boolean hasProperEnclosingStatement() {
		return (getEnclosingStatement() == null) || (getEnclosingStatement().hasAsEnclosedStatement(this) && getDirectExecutable() == null);
	}
	
	/*
	 * The enclosing statement of this statement is only set to the given enclosing statement if
	 * the enclosing statement of this statement is still null. In this way, the enclosing statement of this statement cannot change after
	 * it has been assigned a statement that is not null.
	 */
	void setEnclosingStatement(ComposedStatement enclosingStatement) throws IllegalArgumentException, IllegalMethodCallException {
		if (enclosingStatement != null && ! enclosingStatement.hasAsEnclosedStatement(this))
			throw new IllegalArgumentException();
		if (getExecutable() != null)
			throw new IllegalMethodCallException();
		if (getEnclosingStatement() == null && canHaveAsEnclosingStatement(enclosingStatement))
			this.enclosingStatement = enclosingStatement;
	}
	
	private ComposedStatement enclosingStatement;
	
	public int getDepth() {
		if (getEnclosingStatement() == null)
			return 0;
		return 1 + getEnclosingStatement().getDepth();
	}
}