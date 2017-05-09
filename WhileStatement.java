package asteroids.model.programs;

import asteroids.model.exceptions.programExceptions.*;
import be.kuleuven.cs.som.annotate.*;

public class WhileStatement extends SingleExpressionStatement<Boolean> implements ComposedStatement {
	
	
	public WhileStatement(Expression<Boolean> condition, Statement bodyStatement) throws IllegalArgumentException {
		super(condition);
		if (! canHaveAsEnclosedStatement(bodyStatement))
			throw new IllegalArgumentException();
		this.bodyStatement = bodyStatement;
	}
	
	@Override
	public void execute() {
		while (evaluateExpression() || isExecutingBody()) {
			setIsExecutingBody(true);
			try {
				getBodyStatement().execute();
			}
			catch (BreakException exc) {
				break;
			}
			setIsExecutingBody(false);
		}
		setIsExecutingBody(false);	
	}
	
	@Basic
	public boolean isExecutingBody() {
		return this.isExecutingBody;
	}
	
	private void setIsExecutingBody(boolean flag) {
		this.isExecutingBody = flag;
	}
	
	private boolean isExecutingBody;
	
	@Override
	public boolean hasAsSubStatement(Statement statement) {
		if (getBodyStatement() == statement)
			return true;
		if (getBodyStatement() instanceof ComposedStatement)
			return ((ComposedStatement)getBodyStatement()).hasAsSubStatement(statement);
		return false;
	}

	@Override
	public boolean hasAsEnclosedStatement(Statement statement) {
		return getBodyStatement() == statement;
	}

	@Override
	public boolean hasProperEnclosedStatements() {
		if (! canHaveAsEnclosedStatement(getBodyStatement()) || getBodyStatement().getEnclosingStatement() != this)
			return false;
		return true;
	}
	
	@Basic
	public Statement getBodyStatement() {
		return this.bodyStatement;
	}
	
	private final Statement bodyStatement;
}