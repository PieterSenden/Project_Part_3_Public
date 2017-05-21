package asteroids.model.programs.statements;

import asteroids.model.exceptions.programExceptions.*;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;

public class WhileStatement extends SingleExpressionStatement<Boolean> implements ComposedStatement {
	
	
	public WhileStatement(Expression<Boolean> condition, Statement bodyStatement) throws IllegalArgumentException {
		super(condition);
		if (! canHaveAsEnclosedStatement(bodyStatement))
			throw new IllegalArgumentException();
		this.bodyStatement = bodyStatement;
		bodyStatement.setEnclosingStatement(this);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		if (executor.getCurrentExecutionListLength() <= getDepth())
			executor.setExecutionPositionAt(getDepth(), NOT_EXECUTING_BODY);
		while (evaluateExpression(executor) || executor.getExecutionPositionAt(getDepth()) == EXECUTING_BODY) {
			setIsExecutingBody(EXECUTING_BODY, executor);
			try {
				getBodyStatement().execute(executor);
			}
			catch (BreakException exc) {
				break;
			}
			setIsExecutingBody(NOT_EXECUTING_BODY, executor);
		}
		setIsExecutingBody(NOT_EXECUTING_BODY, executor);
	}
	
	private static final int EXECUTING_BODY = 1;
	private static final int NOT_EXECUTING_BODY = 0;
	
	
//	@Basic
//	public boolean isExecutingBody() {
//		return this.isExecutingBody;
//	}
//	
	private void setIsExecutingBody(int state, ProgramExecutor executor) throws IllegalArgumentException {
		if (state != EXECUTING_BODY && state != NOT_EXECUTING_BODY)
			throw new IllegalArgumentException();
		executor.setExecutionPositionAt(getDepth(), state);
	}
//	
//	private boolean isExecutingBody;
	
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