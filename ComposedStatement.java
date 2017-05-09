package asteroids.model.programs;

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
}