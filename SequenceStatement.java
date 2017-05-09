package asteroids.model.programs;

public class SequenceStatement extends Statement implements ComposedStatement {

	public ComposedStatement(Statement... enclosedStatements) {
		if (enclosedStatements == null || enclosedStatements.length == 0)
			throw new IllegalArgumentException();
		for (Statement statement : enclosedStatements)
			if (! canHaveAsEnclosedStatement(statement))
				throw new IllegalArgumentException();
		this.enclosedStatements = enclosedStatements;
	}
	
	
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
	
	public boolean hasAsEnclosedStatement(Statement statement) {
		for (Statement stat : enclosedStatements) {
			if (stat == statement) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canHaveAsEnclosedStatement(Statement statement) {
		if (statement == null || statement == this)
			return false;
		if (statement instanceof ComposedStatement)
			return !((ComposedStatement)statement).hasAsSubStatement(this);
		return true;
	}
	
	public boolean hasProperEnclosedStatements() {
		for (Statement statement : enclosedStatements) {
			if (! canHaveAsEnclosedStatement(statement) || statement.getEnclosingStatement() != this)
				return false;
		}
		return true;
	}
		
	private final Statement[] enclosedStatements;
}
