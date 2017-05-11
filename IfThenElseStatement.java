package asteroids.model.programs.statements;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;

public class IfThenElseStatement extends SingleExpressionStatement<Boolean> implements ComposedStatement {
	
	public IfThenElseStatement(Expression<Boolean> condition, Statement ifStatement, Statement elseStatement) throws IllegalArgumentException {
		super(condition);
		if (! canHaveAsIfStatement(ifStatement) || ! canHaveAsElseStatement(elseStatement))
			throw new IllegalArgumentException();
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		if (executor.getExecutionStackDepth() < getDepth())
			executor.setExecutionPositionAt(getDepth(), CONDITION);
		if (evaluateExpression() || executor.getExecutionPositionAt(getDepth()) == IF) {
			setExecutionPosition(IF, executor);
			getIfStatement().execute(executor);
			setExecutionPosition(CONDITION, executor);
		}
		else if (getElseStatement() != null || executor.getExecutionPositionAt(getDepth()) == ELSE) {
			setExecutionPosition(ELSE, executor);			
			getElseStatement().execute(executor);
			setExecutionPosition(CONDITION, executor);
		}
		executor.removeExecutionPosition();
	}
	
	private static final int CONDITION = 0;
	private static final int IF = 1;
	private static final int ELSE = 2;
	
//	public ExecutionPosition getExecutionPosition() {
//		return this.executionPosition;
//	}
//	
	private void setExecutionPosition(int executionPosition, ProgramExecutor executor) throws IllegalArgumentException {
		if (executionPosition != CONDITION || executionPosition != IF || executionPosition != CONDITION)
			throw new IllegalArgumentException();
		executor.setExecutionPositionAt(getDepth(), executionPosition);
	}
//	
//	@Value
//	enum ExecutionPosition {
//		CONDITION, IF, ELSE
//	}
//	private ExecutionPosition executionPosition = ExecutionPosition.CONDITION;
	
	
	@Override
	public boolean hasAsSubStatement(Statement statement) {
		if (hasAsEnclosedStatement(statement))
			return true;
		if (getElseStatement() instanceof ComposedStatement && ((ComposedStatement)getElseStatement()).hasAsSubStatement(statement))
			return true;
		if (getIfStatement() instanceof ComposedStatement && ((ComposedStatement)getIfStatement()).hasAsSubStatement(statement))
			return true;
		return false;
	}

	@Override
	public boolean hasAsEnclosedStatement(Statement statement) {
		return (getIfStatement() == statement) || (getElseStatement() == statement);
	}

	@Override
	public boolean hasProperEnclosedStatements() {
		if (! canHaveAsIfStatement(ifStatement) || ifStatement.getEnclosingStatement() != this)
			return false;
		if (! canHaveAsElseStatement(elseStatement) || elseStatement.getEnclosingStatement() != this)
			return false;
		return true;
	}
	
	@Override
	public boolean canHaveAsEnclosedStatement(Statement statement) {
		return canHaveAsIfStatement(statement) || canHaveAsElseStatement(statement);
	}
	
	public boolean canHaveAsIfStatement(Statement statement) {
		return ComposedStatement.super.canHaveAsEnclosedStatement(statement);
	}
	
	public boolean canHaveAsElseStatement(Statement statement) {
		return (statement == null) || ComposedStatement.super.canHaveAsEnclosedStatement(statement);
	}
	
	@Basic
	public Statement getIfStatement() {
		return this.ifStatement;
	}
	
	@Basic
	public Statement getElseStatement() {
		return this.elseStatement;
	}
	
	private final Statement ifStatement, elseStatement;	
}
