package asteroids.model.programs;

import asteroids.model.exceptions.IllegalMethodCallException;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar | hasProperEnclosingStatement()
 * @invar | hasProperExecutable()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 *
 */
public abstract class Statement {
	
	public abstract void execute();
	
	public Executable getExecutable() {
		if (getEnclosingStatement() == null)
			return getEric();
		return getEnclosingStatement().getExecutable();
	}
	
	public boolean canHaveAsEric(Executable exe) {
		return (exe == null) || exe.canHaveAsBodyStatement(this);
	}
	
	public boolean hasProperEric() {
		if (getEric() == null)
			return true;
		return getEric().getBodyStatement() == this;
	}
	
	@Basic
	public Executable getEric() {
		return this.eric;
	}
	
	void setEric(Executable executable) throws IllegalArgumentException, IllegalMethodCallException {
		if (! canHaveAsEric(executable))
			throw new IllegalArgumentException();
		if (executable.getBodyStatement() != this || getExecutable() != null || getEnclosingStatement() != null)
			throw new IllegalMethodCallException();
		this.eric = executable;
		
	}
	
	private Executable eric;
	//TODO: betere naam verzinnen
	
	@Basic
	public ComposedStatement getEnclosingStatement() {
		return this.enclosingStatement;
	}
	
	public boolean canHaveAsEnclosingStatement(ComposedStatement enclosingStatement) {
		return (enclosingStatement == null) || enclosingStatement.canHaveAsEnclosedStatement(this);
	}
	
	public boolean hasProperEnclosingStatement() {
		return (getEnclosingStatement() == null) || (getEnclosingStatement().hasAsEnclosedStatement(this) && getEric() == null);
	}
	
	/*
	 * TODO: method call check toevoegen aan documentatie
	 * The enclosing statement of this statement is only set to the given enclosing statement if
	 * the enclosing statement of this statement is still null.
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
}