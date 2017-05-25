package asteroids.model.programs.statements;

import asteroids.model.programs.Executable;

/**
 * An interface representing functionality for a composed statement.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
interface ComposedStatement {
	
	boolean hasAsSubStatement(Statement statement);
	
	boolean hasAsEnclosedStatement(Statement statement);
	
	default boolean canHaveAsEnclosedStatement(Statement statement) {
		if (statement == null || statement == this)
			return false;
		if (statement instanceof ComposedStatement)
			return !((ComposedStatement)statement).hasAsSubStatement((Statement)this);
		return true;
	}
	
	boolean hasProperEnclosedStatements();
	
	Executable getExecutable();
	
	int getDepth();
}