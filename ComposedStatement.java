package asteroids.model.programs;

public interface ComposedStatement {
	
	boolean hasAsSubStatement(Statement statement);
	
	boolean hasAsEnclosedStatement(Statement statement);
	
	boolean canHaveAsEnclosedStatement(Statement statement);
	
	boolean hasProperEnclosedStatements();
}